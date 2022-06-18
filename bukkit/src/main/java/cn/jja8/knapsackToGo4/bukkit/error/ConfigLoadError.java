package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class ConfigLoadError extends KnapsackToGo4Error {
    public ConfigLoadError(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public ConfigLoadError(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
