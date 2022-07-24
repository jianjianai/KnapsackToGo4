package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataDeserializeException extends KnapsackToGo4Exception{

    public DataDeserializeException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
