package cn.jja8.knapsackToGo4.bukkit.error;

public class ConfigLoadException extends KnapsackToGo4Exception {
    public ConfigLoadException(String message) {
        super(message);
    }

    public ConfigLoadException(Throwable cause, String message) {
        super(cause, message);
    }
}
