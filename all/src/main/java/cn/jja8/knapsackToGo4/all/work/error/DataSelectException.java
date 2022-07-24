package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataSelectException extends KnapsackToGo4Exception{

    public DataSelectException(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
