package cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.error;

import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;

/**
 * 数据库连接失败异常
 * */
public class UpdateDataError extends KnapsackToGo4Error {


    public UpdateDataError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
