package cn.jja8.knapsackToGo4.bukkit;

import cn.jja8.knapsackToGo4.bukkit.config.Lang;
import cn.jja8.knapsackToGo4.bukkit.config.PlayerDataConfig;
import cn.jja8.knapsackToGo4.bukkit.config.ServerConfig;
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
            e.printStackTrace();
        }
        try {
            playerDataConfig = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"playerDataConfig.yml"),playerDataConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ServerConfig = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"ServerConfig.yml"),ServerConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
