package cn.jja8.knapsackToGo4.bukkit.basic.dataCase.mysql.error;

import cn.jja8.knapsackToGo4.bukkit.error.KnapsackToGo4Exception;

/**
 * 数据库连接失败异常
 * */
public class DatabaseConnectionException extends KnapsackToGo4Exception {
    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(Throwable cause, String message) {
        super(cause, message);
    }
}
