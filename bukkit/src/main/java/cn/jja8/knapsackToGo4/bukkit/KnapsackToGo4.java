package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.all.work.SetUp;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadError;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitGo4Player;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitTaskManager;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitWork;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import cn.jja8.patronSaint_2022_3_2_1244.bukkit.bStats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class KnapsackToGo4 extends JavaPlugin{
    public static KnapsackToGo4 knapsackToGo4;
    BukkitWork work;

    @Override
    public void onEnable() {
        knapsackToGo4 = this;
        String v = "unknown";
        try {
            v = Bukkit.getServer().getClass().getName().split("\\.")[3];
        }catch (Error|Exception ignored){}

        try {
            PlayerData.load();
        } catch (Error|Exception e) {
            getLogger().severe("插件加载时发生错误，已禁用！");
            e.printStackTrace();
            getLogger().severe("插件无法启用，请参考上方报错排查问题。若需反馈请将上方报错完整提交。");
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("当前服务端版本："+v);
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            return;

        }

        SetUp setUp;
        try {
            setUp = YamlConfig.loadFromFile(new File(getDataFolder(),"KnapsackToGo4SetUp.yml"),new SetUp());
        } catch (Error|IOException e) {
            getLogger().severe("插件加载时发生错误，已禁用！");
            new ConfigLoadError(e,"KnapsackToGo4SetUp.yaml配置文件加载出错").printStackTrace();
            getLogger().severe("插件无法启用，请参考上方报错排查问题。若需反馈请将上方报错完整提交。");
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("当前服务端版本："+v);
            getLogger().severe("-------------------------------------------------------");
            getLogger().severe("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            return;
        }

        work = new BukkitWork(PlayerData.playerDataCase,PlayerData.playerDataSerialize,new BukkitTaskManager(this),setUp,getLogger());
        Bukkit.getPluginManager().registerEvents(work,this);

        getLogger().info("-------------------------------------------------------");
        getLogger().info("当前服务端版本："+v);
        getLogger().info("-------------------------------------------------------");
        getLogger().info("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");


        //解决热重载玩家数据不加载的问题
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            work.playerJoin(new BukkitGo4Player(onlinePlayer));
        }

        new Metrics(this, 15256);
    }

    @Override
    public void onDisable() {
        work.close();
    }
}
