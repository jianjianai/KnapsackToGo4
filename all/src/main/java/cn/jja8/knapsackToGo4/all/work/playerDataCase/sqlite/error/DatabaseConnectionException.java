package cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Exception;

import java.util.logging.Logger;

/**
 * 数据库连接失败异常
 * */
public class DatabaseConnectionException extends KnapsackToGo4Exception {

    public DatabaseConnectionException(Logger logger, String message) {
        super(logger, message);
    }

    public DatabaseConnectionException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
