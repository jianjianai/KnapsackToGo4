package cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite;

import cn.jja8.knapsackToGo4.all.work.PlayerDataCaseLock;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.error.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteDataCaseLock implements PlayerDataCaseLock {
    SqliteDataCase sqliteDataCase;
    String playerUUid;
    String lockUUID;
    public SqliteDataCaseLock(SqliteDataCase sqliteDataCase, String playerUUid, String lockUUID) {
        this.sqliteDataCase = sqliteDataCase;
        this.playerUUid = playerUUid;
        this.lockUUID = lockUUID;
    }

    @Override
    public void saveData(byte[] bytes) {
        try (
                Connection connection = sqliteDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update PlayerData set Data=? where PlayerUUID=?;")
        ){
            preparedStatement.setBytes(1,bytes);
            preparedStatement.setString(2,playerUUid);
            preparedStatement.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public byte[] loadData() {
        try (
                Connection connection = sqliteDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("select Data from PlayerData where PlayerUUID=?")
        ){
            preparedStatement.setString(1,playerUUid);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getBytes(1);
                }
            }
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void unlock() {
        try (
                Connection connection = sqliteDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update PlayerData set LockUUID=? where PlayerUUID=?;")
        ){
            preparedStatement.setString(1, SqliteDataCase.NULL);
            preparedStatement.setString(2,playerUUid);
            preparedStatement.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
    }
}
