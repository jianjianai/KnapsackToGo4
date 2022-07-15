package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.sponge.KnapsackToGo4;

public class SpongeLogger implements Logger {
    public static final SpongeLogger spongeLogger = new SpongeLogger();

    private SpongeLogger(){}
    @Override
    public void severe(String s) {
        KnapsackToGo4.logger.error(s);
    }
}
