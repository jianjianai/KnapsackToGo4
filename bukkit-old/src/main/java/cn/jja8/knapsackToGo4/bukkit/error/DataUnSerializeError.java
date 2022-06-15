package cn.jja8.knapsackToGo4.bukkit.error;

public class DataUnSerializeError extends KnapsackToGo4Error{
    public DataUnSerializeError(String message) {
        super(message);
    }

    public DataUnSerializeError(Throwable cause, String message) {
        super(cause, message);
    }
}
