package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataUnlockException extends KnapsackToGo4Exception{

    public DataUnlockException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
