package cn.jja8.knapsackToGo4.bukkit.error;


public class ConfigLoadException extends BukkitKnapsackToGo4Error {

    public ConfigLoadException(String message) {
        super(message);
    }

    public ConfigLoadException(Throwable cause, String message) {
        super(cause, message);
    }
}
