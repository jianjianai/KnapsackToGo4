package cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql;

import cn.jja8.knapsackToGo4.all.work.*;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error.DatabaseConnectionException;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error.UpdateLockError;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;
import java.util.logging.Logger;

public class MysqlDataCase implements PlayerDataCase {
    static public final String NULL = "NULL";
    static private final long MoreTime = 5000;//每次持有锁的时候多申请的时间。防止自己更新太慢被其他服务器抢

    public final MysqlDataCaseSetUp mysqlDataCaseSetUp;
    private long lockExpirationTime = 0; //锁到期时间
    public final String lockUUID = UUID.randomUUID().toString();
    private Task TaskTimer;
    private Logger logger;

    public MysqlDataCase(MysqlDataCaseSetUp mysqlDataCaseSetUp, TaskManager taskManager, Logger logger) throws DatabaseConnectionException, SQLException {
        this.mysqlDataCaseSetUp = mysqlDataCaseSetUp;
        this.logger = logger;
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists PlayerData(PlayerUUID varchar(36) not null primary key, LockUUID varchar(36), Data LONGBLOB)");
        statement.execute("create table if not exists LockServer(LockUUID varchar(36) not null primary key,ServerName VARCHAR(128) not null,LockToTile bigint)");
        statement.close();
        connection.close();
        updateLock();

        //更新锁
        TaskTimer = taskManager.runCircularTask(500, () -> {
            if (lockExpirationTime>System.currentTimeMillis()){
                return;
            }
            updateLock();
        });
    }

    public Connection getConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(mysqlDataCaseSetUp.dataBaseURL, mysqlDataCaseSetUp.userName, mysqlDataCaseSetUp.PassWord);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(logger,e,"数据库连接失败，请检查dataBaseURL，userName，PassWord是否正确。");
        }
    }

    void updateLock(){
        lockExpirationTime = System.currentTimeMillis()+ mysqlDataCaseSetUp.holdLockTime;
        try (
                Connection connection = getConnection();
                PreparedStatement sel = connection.prepareStatement("select ServerName from LockServer where LockUUID=?");
        ){
            sel.setString(1,lockUUID);
            ResultSet resultSet = sel.executeQuery();
            if (resultSet.next()) {
                try (PreparedStatement update = connection.prepareStatement("update LockServer set LockToTile=? where LockUUID=?")){
                    update.setLong(1, lockExpirationTime +MoreTime);
                    update.setString(2,lockUUID);
                    update.executeUpdate();
                }
            }else {
                try (PreparedStatement into =connection.prepareStatement("insert into LockServer(LockUUID,ServerName, LockToTile) VALUES (?,?,?)");){
                    into.setString(1,lockUUID);
                    into.setString(2, mysqlDataCaseSetUp.serverName);
                    into.setLong(3, lockExpirationTime +MoreTime);
                    into.executeUpdate();
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new UpdateLockError(logger,e,"更新锁失败，可能造成数据丢失。请尽快关闭服务器！");
        }
    }

    @Override
    public void close() {
        TaskTimer.cancel();
        try (
                Connection connection = getConnection();
                PreparedStatement del = connection.prepareStatement("delete from LockServer where LockUUID=?")
        ) {
            del.setString(1,lockUUID);
            del.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new UpdateLockError(logger,e, "解锁失败，可能造成数据丢失。");
        }
    }

    @Override
    public PlayerDataCaseLock getPlayerDataLock(Go4Player player) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select LockUUID from PlayerData where PlayerUUID=?;");
        ) {
            String LockServerUUID = null;
            //查询上锁的服务器
            preparedStatement.setString(1,player.getUUID().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    LockServerUUID = resultSet.getString(1);
                }else {
                    try (PreparedStatement ins = connection.prepareStatement("insert into PlayerData(PlayerUUID,LockUUID) values (?,?)")){
                        ins.setString(1,player.getUUID().toString());
                        ins.setString(2,NULL);
                        ins.executeUpdate();
                    }
                }
            }
            //检查服务器是否还持有锁
            if (LockServerUUID!=null){
                try (PreparedStatement select = connection.prepareStatement("select LockToTile from LockServer where LockUUID=?;");){
                    select.setString(1,LockServerUUID);
                    try (ResultSet resultSet = select.executeQuery()){
                        if (resultSet.next()){
                            long lockTile = resultSet.getLong(1);//获取锁到期时间
                            if (lockTile>=System.currentTimeMillis()){
                                return null; //服务器持有锁
                            }
                        }
                    }
                }
            }
            //给玩家上自己的锁
            try (PreparedStatement update = connection.prepareStatement("update PlayerData set LockUUID=? where LockUUID=? and PlayerUUID=?")){
                update.setString(1,lockUUID);
                update.setString(2,LockServerUUID==null?NULL:LockServerUUID);
                update.setString(3,player.getUUID().toString());
                if (update.executeUpdate()>=1) {
                    return new MysqlDataCaseLock(this,player.getUUID().toString(),lockUUID);//成功拿到锁
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPlayerDataLockServerName(Go4Player player) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select LockUUID from PlayerData where PlayerUUID=?;");
        ) {
            String LockServerUUID = null;
            //查询上锁的服务器
            preparedStatement.setString(1,player.getUUID().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    LockServerUUID = resultSet.getString(1);
                }else {
                    try (PreparedStatement ins = connection.prepareStatement("insert into PlayerData(PlayerUUID) values (?)")){
                        ins.setString(1,player.getUUID().toString());
                        ins.executeUpdate();
                    }
                }
            }
            //检查服务器是否还持有锁
            if (LockServerUUID!=null){
                try (PreparedStatement select = connection.prepareStatement("select LockToTile,ServerName from LockServer where LockUUID=?;");){
                    select.setString(1,LockServerUUID);
                    try (ResultSet resultSet = select.executeQuery()){
                        if (resultSet.next()){
                            long lockTile = resultSet.getLong(1);//获取锁到期时间
                            if (lockTile>=System.currentTimeMillis()){
                                return resultSet.getString(2); //服务器持有锁
                            }
                        }
                    }
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
