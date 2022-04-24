package cn.jja8.knapsackToGo4.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class KnapsackToGo4  extends JavaPlugin {
    public static KnapsackToGo4 knapsackToGo4;

    public KnapsackToGo4() {
        knapsackToGo4 = this;
    }

    @Override
    public void onEnable() {
        ConfigBukkit.load();
    }
}
