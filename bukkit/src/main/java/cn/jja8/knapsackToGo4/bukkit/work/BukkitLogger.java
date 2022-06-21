package cn.jja8.knapsackToGo4.bukkit.work;


import cn.jja8.knapsackToGo4.all.work.Logger;

public class BukkitLogger implements Logger {
    private final java.util.logging.Logger logger;

    public BukkitLogger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public void severe(String s) {
        logger.severe(s);
    }
}
