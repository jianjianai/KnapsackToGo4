package cn.jja8.knapsackToGo4.bukkit.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Exception;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class DataCaseLoadError extends KnapsackToGo4Exception {
    public DataCaseLoadError(String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),message);
    }

    public DataCaseLoadError(Throwable cause, String message) {
        super(KnapsackToGo4.knapsackToGo4.getLogger(),cause, message);
    }
}
