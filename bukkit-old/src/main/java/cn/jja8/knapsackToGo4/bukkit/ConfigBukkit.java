package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.bukkit.config.Lang;
import cn.jja8.knapsackToGo4.bukkit.config.PlayerDataConfig;
import cn.jja8.knapsackToGo4.bukkit.config.ServerConfig;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadError;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;

import java.io.File;
import java.io.IOException;

public class ConfigBukkit {
    public static Lang lang = new Lang();
    public static PlayerDataConfig playerDataConfig = new PlayerDataConfig();
    public static ServerConfig ServerConfig = new ServerConfig();
    static void load(){
        try {
            lang = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"lang.yml"),lang);
        } catch (IOException e) {
            new ConfigLoadError(e,"加载配置文件lang.yml出错!").printStackTrace();
        }
        try {
            playerDataConfig = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"playerDataConfig.yml"),playerDataConfig);
        } catch (IOException e) {
            new ConfigLoadError(e,"加载配置文件playerDataConfig.yml出错!").printStackTrace();
        }
        try {
            ServerConfig = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"ServerConfig.yml"),ServerConfig);
        } catch (IOException e) {
            new ConfigLoadError(e,"加载配置文件ServerConfig.yml出错!").printStackTrace();
        }
    }
}
