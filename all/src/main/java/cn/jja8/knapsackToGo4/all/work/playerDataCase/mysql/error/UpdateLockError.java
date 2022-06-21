package cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;

import cn.jja8.knapsackToGo4.all.work.Logger;

/**
 * 数据库连接失败异常
 * */
public class UpdateLockError extends KnapsackToGo4Error {


    public UpdateLockError(Logger logger, String message) {
        super(logger, message);
    }

    public UpdateLockError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
