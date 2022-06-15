package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.error;

import cn.jja8.knapsackToGo4.bukkit.error.KnapsackToGo4Error;

public class PatrSaveError extends KnapsackToGo4Error {
    public PatrSaveError(String message) {
        super(message);
    }

    public PatrSaveError(Throwable cause, String message) {
        super(cause, message);
    }
}
