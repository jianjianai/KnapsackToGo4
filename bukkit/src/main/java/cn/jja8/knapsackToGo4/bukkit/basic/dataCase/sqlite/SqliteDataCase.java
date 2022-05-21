package cn.jja8.knapsackToGo4.bukkit.basic.dataCase.sqlite;

import cn.jja8.knapsackToGo4.bukkit.ConfigBukkit;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCaseLock;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.sqlite.error.DatabaseConnectionException;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.sqlite.error.UpdateLockError;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadError;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class SqliteDataCase implements PlayerDataCase {
    static public final String NULL = "NULL";
    static private final long MoreTime = 5000;//每次持有锁的时候多申请的时间。防止自己更新太慢被其他服务器抢。

    static private SqliteDataCase fileDataCase = null;
    public static PlayerDataCase get() {
        if (fileDataCase==null){
            try {
                fileDataCase = new SqliteDataCase();
            }catch (IOException e){
                throw new ConfigLoadError(e,"配置文件SqliteDataCaseSetUp.yml加载错误");
            } catch (DatabaseConnectionException | SQLException e) {
                throw new ConfigLoadError(e,"初始化数据库连接失败！");
            }
        }
        return fileDataCase;
    }

    public final SqliteDataCaseSetUp sqliteDataCaseSetUp;
    private long lockExpirationTime = 0; //锁到期时间
    public final String lockUUID = UUID.randomUUID().toString();
    private BukkitTask TaskTimer;

    private SqliteDataCase() throws IOException, DatabaseConnectionException, SQLException {
        sqliteDataCaseSetUp = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"SqliteDataCaseSetUp.yml"),new SqliteDataCaseSetUp());
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.execute("create table if not exists PlayerData(PlayerUUID varchar(36) not null constraint PlayerData_pk primary key, LockUUID varchar(36), Data blob)");
        statement.execute("create table if not exists LockServer(LockUUID varchar(36) not null constraint LockServer_pk primary key,ServerName varchar not null,LockToTile datetime)");
        statement.execute("create unique index if not exists PlayerData_PlayerUUID_uindex on PlayerData (PlayerUUID)");
        statement.execute("create unique index if not exists LockServer_ServerName_uindex on LockServer(LockUUID)");
        statement.close();
        connection.close();
        updateLock();

        //更新锁
        TaskTimer = Bukkit.getScheduler().runTaskTimerAsynchronously(KnapsackToGo4.knapsackToGo4, () -> {
            if (lockExpirationTime>System.currentTimeMillis()){
                return;
            }
            updateLock();
        }, 1, 1);
    }

    public Connection getConnection() throws DatabaseConnectionException {
        try {
            return DriverManager.getConnection(sqliteDataCaseSetUp.dataBaseURL, sqliteDataCaseSetUp.userName, sqliteDataCaseSetUp.PassWord);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e,"数据库连接失败，请检查dataBaseURL，userName，PassWord是否正确。");
        }
    }

    void updateLock(){
        lockExpirationTime = System.currentTimeMillis()+ sqliteDataCaseSetUp.holdLockTime;
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
                    into.setString(2, ConfigBukkit.ServerConfig.serverName);
                    into.setLong(3, lockExpirationTime +MoreTime);
                    into.executeUpdate();
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new UpdateLockError(e,"更新锁失败，可能造成数据丢失。请尽快关闭服务器！");
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
            throw new UpdateLockError(e, "解锁失败，可能造成数据丢失。");
        }
    }

    @Override
    public PlayerDataCaseLock getPlayerDataLock(Player player) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select LockUUID from PlayerData where PlayerUUID=?;");
        ) {
            String LockServerUUID = null;
            //查询上锁的服务器
            preparedStatement.setString(1,player.getUniqueId().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    LockServerUUID = resultSet.getString(1);
                }else {
                    try (PreparedStatement ins = connection.prepareStatement("insert into PlayerData(PlayerUUID,LockUUID) values (?,?)")){
                        ins.setString(1,player.getUniqueId().toString());
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
                update.setString(3,player.getUniqueId().toString());
                if (update.executeUpdate()>=1) {
                    return new SqliteDataCaseLock(this,player.getUniqueId().toString(),lockUUID);//成功拿到锁
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPlayerDataLockServerName(Player player) {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select LockUUID from PlayerData where PlayerUUID=?;");
        ) {
            String LockServerUUID = null;
            //查询上锁的服务器
            preparedStatement.setString(1,player.getUniqueId().toString());
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()) {
                    LockServerUUID = resultSet.getString(1);
                }else {
                    try (PreparedStatement ins = connection.prepareStatement("insert into PlayerData(PlayerUUID) values (?)")){
                        ins.setString(1,player.getUniqueId().toString());
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
