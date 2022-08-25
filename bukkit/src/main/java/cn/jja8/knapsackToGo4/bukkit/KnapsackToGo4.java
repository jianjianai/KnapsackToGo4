package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.all.work.SetUp;
import cn.jja8.knapsackToGo4.all.work.Work;
import cn.jja8.knapsackToGo4.bukkit.command.CommandManager;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadError;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitGo4Player;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitLogger;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitTaskManager;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitWork;
import cn.jja8.patronSaint.all.V2.file.YamlConfig;
import cn.jja8.patronSaint.bukkit.v2.bStats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * 可在load阶段给work赋值，兼容更多版本
 * */
public class KnapsackToGo4 extends JavaPlugin{
    public static KnapsackToGo4 knapsackToGo4;
    public static BukkitWork work;
    public static SetUp setUp;

    public static String v = "unknown";

    public KnapsackToGo4() {
        knapsackToGo4 = this;
    }

    @Override
    public void onLoad() {
        try {
            v = Bukkit.getServer().getClass().getName().split("\\.")[3];
        }catch (Error|Exception ignored){}

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
            getLogger().severe("为了保证数据安全，将在30秒后关闭服务器。");
            try {Thread.sleep(30000);} catch (InterruptedException ignored) {}
            Bukkit.shutdown();
            setEnabled(false);
        }
    }

    @Override
    public void onEnable() {
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
            getLogger().severe("为了保证数据安全，将在30秒后关闭服务器。");
            try {Thread.sleep(30000);} catch (InterruptedException ignored) {}
            Bukkit.shutdown();
            return;
        }



        if (work==null){
            work = new BukkitWork(PlayerData.playerDataCase,PlayerData.playerDataSerialize,new BukkitTaskManager(this),setUp,BukkitLogger.get());
            Bukkit.getPluginManager().registerEvents(work,this);
        }else {
            KnapsackToGo4.knapsackToGo4.getLogger().info("已加工作扩展。["+work.getClass().getName()+"]");
        }


        getLogger().info("-------------------------------------------------------");
        getLogger().info("当前服务端版本："+v);
        getLogger().info("-------------------------------------------------------");
        getLogger().info("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");


        //解决热重载玩家数据不加载的问题
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            work.playerJoin(new BukkitGo4Player(onlinePlayer));
        }


        CommandManager.load();

        new Metrics(this, 15256);
    }

    @Override
    public void onDisable() {
        if (work!=null){
            work.close();
        }
    }
}
