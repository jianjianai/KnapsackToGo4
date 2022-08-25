package cn.jja8.knapsackToGo4.sponge;

import cn.jja8.knapsackToGo4.all.work.SetUp;
import cn.jja8.knapsackToGo4.sponge.error.ConfigLoadError;
import cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file.YamlConfig;
import cn.jja8.knapsackToGo4.sponge.work.SpongeLogger;
import cn.jja8.knapsackToGo4.sponge.work.SpongeTaskManager;
import cn.jja8.knapsackToGo4.sponge.work.SpongeWork;
import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Engine;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.*;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Plugin("KnapsackToGo4")
public class KnapsackToGo4 {
    @Inject
    public Logger logger;
    @Inject
    public PluginContainer pluginContainer;
    @Inject
    @ConfigDir(sharedRoot = false)
    public Path configPath;

    static KnapsackToGo4 knapsackToGo4;

    private SpongeWork work;

    public File configFile;


    @Inject
    public KnapsackToGo4() {
        knapsackToGo4 = this;
    }

    public static KnapsackToGo4 getKnapsackToGo4() {
        return knapsackToGo4;
    }

    @Listener
    public void gameLoaded(LoadedGameEvent event){

    }

    @Listener
    public void refreshGame(RefreshGameEvent event){

    }

    @Listener
    public void serverStart(StartingEngineEvent<Engine> event){
        configFile = configPath.toFile();
        try {
            PlayerData.load();
        } catch (Error|Exception e) {
            logger.error("插件加载时发生错误，已禁用！");
            e.printStackTrace();
            logger.error("插件无法启用，请参考上方报错排查问题。若需反馈请将上方报错完整提交。");
            logger.error("-------------------------------------------------------");
            logger.error("当前服务端版本："+Sponge.server().toString());
            logger.error("-------------------------------------------------------");
            logger.error("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            logger.error("为了保证数据安全，将在30秒后关闭服务器。");
            try {Thread.sleep(30000);} catch (InterruptedException ignored) {}
            Sponge.server().shutdown();
            return;
        }

        SetUp setUp;
        try {
            setUp = YamlConfig.loadFromFile(new File(configFile,"KnapsackToGo4SetUp.yml"),new SetUp());
        } catch (Error| IOException e) {
            logger.error("插件加载时发生错误，已禁用！");
            new ConfigLoadError(e,"KnapsackToGo4SetUp.yaml配置文件加载出错").printStackTrace();
            logger.error("插件无法启用，请参考上方报错排查问题。若需反馈请将上方报错完整提交。");
            logger.error("-------------------------------------------------------");
            logger.error("当前服务端版本："+Sponge.server().toString());
            logger.error("-------------------------------------------------------");
            logger.error("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");
            logger.error("为了保证数据安全，将在30秒后关闭服务器。");
            try {Thread.sleep(30000);} catch (InterruptedException ignored) {}
            Sponge.server().shutdown();
            return;
        }

        logger.info("-------------------------------------------------------");
        logger.info("当前服务端版本："+Sponge.server().toString());
        logger.info("-------------------------------------------------------");
        logger.info("若有疑问，您可以前往 “PlugClub/插件实验室 - 820131534” 交流。");

        work = new SpongeWork(PlayerData.playerDataCase,PlayerData.playerDataSerialize,SpongeTaskManager.get(),setUp,SpongeLogger.get());
        Sponge.eventManager().registerListeners(pluginContainer,work);

    }


    @Listener
    public void serverStop(StoppedGameEvent event){
        if (work!=null){
            work.close();
        }
    }
}
