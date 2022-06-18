package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class NoOptionsError extends KnapsackToGo4Error {
    public NoOptionsError(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public NoOptionsError(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
