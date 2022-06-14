package cn.jja8.knapsackToGo4.all.work.error;

import java.util.logging.Logger;

public class DataUnLockError extends KnapsackToGo4Error{
    public DataUnLockError(Logger logger, String message) {
        super(logger, message);
    }

    public DataUnLockError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
