package cn.jja8.knapsackToGo4.all.work.error;

import java.util.logging.Logger;

public class LoadFinishedToRunError extends KnapsackToGo4Error{
    public LoadFinishedToRunError(Logger logger, String message) {
        super(logger, message);
    }

    public LoadFinishedToRunError(Logger logger, Throwable cause, String message) {
        super(logger, cause, message);
    }
}
