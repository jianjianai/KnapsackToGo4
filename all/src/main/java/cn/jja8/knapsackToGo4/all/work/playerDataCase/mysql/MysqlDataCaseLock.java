package cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql;

import cn.jja8.knapsackToGo4.all.work.PlayerDataCaseLock;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error.DatabaseConnectionException;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error.LoadDataError;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error.UpdateDataError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDataCaseLock implements PlayerDataCaseLock {
    MysqlDataCase mysqlDataCase;
    String playerUUid;
    String lockUUID;
    public MysqlDataCaseLock(MysqlDataCase mysqlDataCase, String playerUUid, String lockUUID) {
        this.mysqlDataCase = mysqlDataCase;
        this.playerUUid = playerUUid;
        this.lockUUID = lockUUID;
    }

    @Override
    public void saveData(byte[] bytes) {
        try (
                Connection connection = mysqlDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update PlayerData set Data=? where PlayerUUID=?;")
        ){
            preparedStatement.setBytes(1,bytes);
            preparedStatement.setString(2,playerUUid);
            preparedStatement.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new UpdateDataError(mysqlDataCase.getLogger(),e,"数据库更新出错！");
        }
    }

    @Override
    public byte[] loadData() {
        try (
                Connection connection = mysqlDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select Data from PlayerData where PlayerUUID=?")
        ){
            preparedStatement.setString(1,playerUUid);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getBytes(1);
                }else {
                    return null;
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            throw new LoadDataError(mysqlDataCase.getLogger(),e,"数据库查询出错！");
        }
    }

    @Override
    public void unlock() {
        try (
                Connection connection = mysqlDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update PlayerData set LockUUID=? where PlayerUUID=?;")
        ){
            preparedStatement.setString(1, MysqlDataCase.NULL);
            preparedStatement.setString(2,playerUUid);
            preparedStatement.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            throw new UpdateDataError(mysqlDataCase.getLogger(),e,"数据库更新出错！");
        }
    }
}
