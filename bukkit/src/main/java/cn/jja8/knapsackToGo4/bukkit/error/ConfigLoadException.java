package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Exception;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class ConfigLoadException extends KnapsackToGo4Exception {
    public ConfigLoadException(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public ConfigLoadException(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
