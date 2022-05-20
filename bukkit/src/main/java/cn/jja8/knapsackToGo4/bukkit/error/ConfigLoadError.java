package cn.jja8.knapsackToGo4.bukkit.error;

public class ConfigLoadError extends KnapsackToGo4Error{
    public ConfigLoadError(String message) {
        super(message);
    }

    public ConfigLoadError(Throwable cause, String message) {
        super(cause, message);
    }
}
