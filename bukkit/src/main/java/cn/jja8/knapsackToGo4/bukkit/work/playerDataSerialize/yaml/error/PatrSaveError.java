package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class PatrSaveError extends KnapsackToGo4Error {
    public PatrSaveError(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public PatrSaveError(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
