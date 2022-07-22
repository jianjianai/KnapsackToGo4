package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitLogger;

public class BukkitKnapsackToGo4Error extends KnapsackToGo4Error {
    public BukkitKnapsackToGo4Error(String message) {
        super(BukkitLogger.get(),message);
    }

    public BukkitKnapsackToGo4Error(Throwable cause, String message) {
        super(BukkitLogger.get(),cause, message);
    }
}
