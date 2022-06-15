package cn.jja8.knapsackToGo4.bukkit.error;

public class DataSaveError extends KnapsackToGo4Error{
    public DataSaveError(String message) {
        super(message);
    }

    public DataSaveError(Throwable cause, String message) {
        super(cause, message);
    }
}
