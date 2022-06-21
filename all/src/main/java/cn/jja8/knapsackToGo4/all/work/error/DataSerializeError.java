package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataSerializeError extends KnapsackToGo4Error{
    public DataSerializeError(Logger logger, String message) {
        super(logger, message);
    }

    public DataSerializeError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
