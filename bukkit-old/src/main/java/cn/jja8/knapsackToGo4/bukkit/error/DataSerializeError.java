package cn.jja8.knapsackToGo4.bukkit.error;

public class DataSerializeError extends KnapsackToGo4Error{
    public DataSerializeError(String message) {
        super(message);
    }

    public DataSerializeError(Throwable cause, String message) {
        super(cause, message);
    }
}
