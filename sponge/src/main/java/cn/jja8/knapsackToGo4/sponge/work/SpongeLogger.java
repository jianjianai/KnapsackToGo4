package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.sponge.KnapsackToGo4;

public class SpongeLogger implements Logger {
    private static final SpongeLogger spongeLogger = new SpongeLogger();

    public static SpongeLogger get() {
        return spongeLogger;
    }

    /**
     * 这是一个单例
     * */
    private SpongeLogger(){}
    @Override
    public void severe(String s) {
        KnapsackToGo4.logger.error(s);
    }

    @Override
    public void info(String s) {
        KnapsackToGo4.logger.info(s);
    }
}
