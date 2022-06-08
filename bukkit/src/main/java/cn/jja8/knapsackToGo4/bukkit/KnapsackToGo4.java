package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.bukkit.work.PlayerDataManager;
import cn.jja8.patronSaint_2022_3_2_1244.bukkit.bStats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
        } catch (Error|Exception e) {
            e.printStackTrace();
            getLogger().severe("插件无法启用，请参考上方报错排查问题。若需反馈请将上方报错完整提交。");
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("当前服务端版本："+v);
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            return;
        }
        playerDataManager = new PlayerDataManager();
        getLogger().info("-------------------------------------------------------");
        getLogger().info("当前服务端版本："+v);
        getLogger().info("-------------------------------------------------------");
        getLogger().info("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");





        //解决热重载玩家数据不加载的问题
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerDataManager.PlayerJoinEvent(new PlayerJoinEvent(onlinePlayer,"欢迎"));
        }
        new Metrics(this, 15256);
    }

    @Override
    public void onDisable() {
        if (playerDataManager!=null) playerDataManager.close();
        PlayerData.close();
    }
}
