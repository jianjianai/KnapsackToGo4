package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.error;

import cn.jja8.knapsackToGo4.bukkit.error.KnapsackToGo4Error;

public class PatrLoadError extends KnapsackToGo4Error {
    public PatrLoadError(String message) {
        super(message);
    }

    public PatrLoadError(Throwable cause, String message) {
        super(cause, message);
    }
}
