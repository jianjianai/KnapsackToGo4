package cn.jja8.knapsackToGo4.sponge.error;

import cn.jja8.knapsackToGo4.all.work.error.KnapsackToGo4Error;
import cn.jja8.knapsackToGo4.sponge.work.SpongeLogger;

public class SpoongKnapsackToGo4Error extends KnapsackToGo4Error {
    public SpoongKnapsackToGo4Error(String message) {
        super(SpongeLogger.get(),message);
    }

    public SpoongKnapsackToGo4Error(Throwable cause, String message) {
        super(SpongeLogger.get(),cause, message);
    }
}
