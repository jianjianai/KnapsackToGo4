package cn.jja8.knapsackToGo4.bukkit.basic.dataCase.jdbc;

import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCaseLock;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.jdbc.error.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcDataCaseLock implements PlayerDataCaseLock {
    JdbcDataCase jdbcDataCase;
    String playerUUid;
    String lockUUID;
    public JdbcDataCaseLock(JdbcDataCase jdbcDataCase, String playerUUid, String lockUUID) {
        this.jdbcDataCase = jdbcDataCase;
        this.playerUUid = playerUUid;
        this.lockUUID = lockUUID;
    }

    @Override
    public void saveData(byte[] bytes) {
        try (
                Connection connection = jdbcDataCase.getConnection();
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
                Connection connection = jdbcDataCase.getConnection();
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
                Connection connection = jdbcDataCase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("update PlayerData set LockUUID=? where PlayerUUID=?;")
        ){
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,playerUUid);
            preparedStatement.executeUpdate();
        } catch (DatabaseConnectionException | SQLException e) {
            e.printStackTrace();
        }
    }
}
