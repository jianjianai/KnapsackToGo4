package cn.jja8.knapsackToGo4.bukkit.error;

public class DataLoadError extends KnapsackToGo4Error{
    public DataLoadError(String message) {
        super(message);
    }

    public DataLoadError(Throwable cause, String message) {
        super(cause, message);
    }
}
