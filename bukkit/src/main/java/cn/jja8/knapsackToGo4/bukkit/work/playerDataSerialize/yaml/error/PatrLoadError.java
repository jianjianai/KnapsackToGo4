package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class PatrLoadError extends KnapsackToGo4Error {
    public PatrLoadError(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public PatrLoadError(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
