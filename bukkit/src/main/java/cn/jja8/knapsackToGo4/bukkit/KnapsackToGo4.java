package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.bukkit.basic.PlayerData;
import cn.jja8.knapsackToGo4.bukkit.player.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public class KnapsackToGo4  extends JavaPlugin {
    public static KnapsackToGo4 knapsackToGo4;
    public PlayerDataManager playerDataManager;

    public KnapsackToGo4() {
        knapsackToGo4 = this;
    }

    @Override
    public void onEnable() {
        ConfigBukkit.load();
        String v = Bukkit.getServer().getClass().getName().split("\\.")[3];
        try {
            PlayerData.load();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            for (int i = 0; i < 3; i++) {
                getLogger().severe("插件无法启用，可能暂时还不兼容当前服务端，您可以从网上下载兼容扩展来使插件兼容当前服务端。");
            }
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("兼容版本：v1_16_R3");
            getLogger().severe("当前服务端版本："+v);
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("如果您的服务器在兼容列表中，请自行排除上方报错！若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            return;
        }
        playerDataManager = new PlayerDataManager();
        getLogger().info("-------------------------------------------------------");
        getLogger().info("当前服务端版本："+v);
        getLogger().info("-------------------------------------------------------");
        getLogger().info("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
        getLogger().warning("当前非正式版本，若有bug您可以前往 “PlugClub/插件实验室 - 820131534” 交流和反馈。");
    }
}
