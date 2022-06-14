package cn.jja8.knapsackToGo4.all.work.error;

import java.util.logging.Logger;

public class DataUnSerializeError extends KnapsackToGo4Error{
    public DataUnSerializeError(Logger logger, String message) {
        super(logger, message);
    }

    public DataUnSerializeError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
