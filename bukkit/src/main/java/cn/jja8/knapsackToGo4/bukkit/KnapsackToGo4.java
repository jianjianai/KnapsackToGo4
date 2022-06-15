package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.all.work.Work;
import org.bukkit.plugin.java.JavaPlugin;

public class KnapsackToGo4 extends JavaPlugin {
    public static KnapsackToGo4 knapsackToGo4;
    Work work;

    @Override
    public void onEnable() {
        knapsackToGo4 = this;
        work = new Work();
    }

    @Override
    public void onDisable() {

    }
}
