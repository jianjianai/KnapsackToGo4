package cn.jja8.knapsackToGo4.all.work.error;


import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataLoadError extends KnapsackToGo4Error{
    public DataLoadError(Logger logger, String message) {
        super(logger, message);
    }

    public DataLoadError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
