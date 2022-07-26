package cn.jja8.knapsackToGo4.bukkit.work;


import cn.jja8.knapsackToGo4.all.work.Logger;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;

public class BukkitLogger implements Logger {
    private static final BukkitLogger bukkitLogger = new BukkitLogger();

    public static BukkitLogger get() {
        return bukkitLogger;
    }

    /**
     * 这是一个单例
     * */
    private BukkitLogger() {}

    @Override
    public void severe(String s) {
        KnapsackToGo4.INSTANCE.getLogger().severe(s);
    }

    @Override
    public void info(String s) {
        KnapsackToGo4.INSTANCE.getLogger().info(s);
    }
}
