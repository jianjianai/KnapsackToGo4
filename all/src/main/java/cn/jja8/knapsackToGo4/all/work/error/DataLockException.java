package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataLockException extends KnapsackToGo4Exception{

    public DataLockException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
