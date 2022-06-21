package cn.jja8.knapsackToGo4.all.work.error;

import cn.jja8.knapsackToGo4.all.work.Logger;

public class DataSaveError extends KnapsackToGo4Error{
    public DataSaveError(Logger logger, String message) {
        super(logger, message);
    }

    public DataSaveError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
