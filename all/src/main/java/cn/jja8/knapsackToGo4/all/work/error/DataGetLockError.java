package cn.jja8.knapsackToGo4.all.work.error;


import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataGetLockError extends KnapsackToGo4Error{
    public DataGetLockError(Logger logger, String message) {
        super(logger, message);
    }

    public DataGetLockError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
