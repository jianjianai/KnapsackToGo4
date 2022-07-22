package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataUpdateException extends KnapsackToGo4Exception{
    public DataUpdateException(Logger logger, String message) {
        super(logger, message);
    }

    public DataUpdateException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
