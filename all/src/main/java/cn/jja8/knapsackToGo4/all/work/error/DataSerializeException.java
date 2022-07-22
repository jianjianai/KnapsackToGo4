package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataSerializeException extends KnapsackToGo4Exception{
    public DataSerializeException(Logger logger, String message) {
        super(logger, message);
    }

    public DataSerializeException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
