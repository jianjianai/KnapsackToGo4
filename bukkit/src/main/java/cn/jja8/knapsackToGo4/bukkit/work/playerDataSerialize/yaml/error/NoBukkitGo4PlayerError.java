package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error;

import cn.jja8.knapsackToGo4.bukkit.error.BukkitKnapsackToGo4Error;

public class NoBukkitGo4PlayerError extends BukkitKnapsackToGo4Error {

    public NoBukkitGo4PlayerError(String message) {
        super(message);
    }

    public NoBukkitGo4PlayerError(Throwable cause, String message) {
        super(cause, message);
    }
}
