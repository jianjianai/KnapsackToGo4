package cn.jja8.knapsackToGo4.all.work.playerDataCase.file.error;

import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;

/**
 * 数据库连接失败异常
 * */
public class WriteDataError extends KnapsackToGo4Error {


    public WriteDataError(Logger logger, String message) {
        super(logger, message);
    }

    public WriteDataError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
