package cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error;

import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;

/**
 * 数据库连接失败异常
 * */
public class LoadDataError extends KnapsackToGo4Error {


    public LoadDataError(Logger logger, String message) {
        super(logger, message);
    }

    public LoadDataError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
