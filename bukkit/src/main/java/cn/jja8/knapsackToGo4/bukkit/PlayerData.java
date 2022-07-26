package cn.jja8.knapsackToGo4.bukkit;


import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.bukkit.config.playerData.PlayerDataCaseType;
import cn.jja8.knapsackToGo4.bukkit.config.playerData.PlayerDataSettings;
import cn.jja8.knapsackToGo4.bukkit.config.playerData.PlayerDataSerializeType;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadException;
import cn.jja8.knapsackToGo4.bukkit.error.DataCaseLoadError;
import cn.jja8.knapsackToGo4.bukkit.error.DataCloseError;
import cn.jja8.knapsackToGo4.bukkit.error.NoOptionsError;
import cn.jja8.patronSaint.all.V2.file.YamlConfig;

import java.io.File;
import java.io.IOException;

/**
 * 一个中介类，为了兼容多版本
 * 可在使用别的插件在load阶段给playerDataSerialize赋值，用于兼容更多版本。
 * */
public class PlayerData {
    public static PlayerDataCase playerDataCase = null;
    public static PlayerDataSerialize playerDataSerialize = null;

    static void load() throws ConfigLoadException, DataCaseLoadError {
        try {
            if (playerDataCase==null){
                String playerDataCase = null;
                try {
                    playerDataCase = YamlConfig.loadFromFile(new File(KnapsackToGo4.INSTANCE.getDataFolder(),"PlayerDataStorage.yml"), new PlayerDataSettings()).playerDataCaseType;
                    PlayerDataCaseType playerDataCaseType = PlayerDataCaseType.valueOf(playerDataCase);
                    PlayerData.playerDataCase = playerDataCaseType.getDataCase();
                    KnapsackToGo4.INSTANCE.getLogger().info("已加载内置数据容器。["+playerDataCase+"]");
                } catch (IllegalArgumentException e){
                    throw new NoOptionsError(e,"加载配置文件PlayerDataStorage.yml中"+playerDataCase+"值不存在！");
                }
            } else {
                KnapsackToGo4.INSTANCE.getLogger().info("已加载数据容器扩展。["+playerDataCase.getClass().getName()+"]");
            }
            if (playerDataSerialize==null){
                String playerDataSerialize = null;
                try {
                    playerDataSerialize = YamlConfig.loadFromFile(new File(KnapsackToGo4.INSTANCE.getDataFolder(),"PlayerDataStorage.yml"), new PlayerDataSettings()).playerDataSerializeType;
                    PlayerDataSerializeType playerDataSerializeType = PlayerDataSerializeType.valueOf(playerDataSerialize);
                    PlayerData.playerDataSerialize = playerDataSerializeType.getYamlDataSerialize();
                    KnapsackToGo4.INSTANCE.getLogger().info("已加载内置数序列化方法。["+playerDataSerialize+"]");
                } catch (IllegalArgumentException e){
                    throw new NoOptionsError(e,"加载配置文件PlayerDataStorage.yml中"+playerDataSerialize+"值不存在！");
                }
            }else {
                KnapsackToGo4.INSTANCE.getLogger().info("已加载数序列化方法扩展。["+playerDataSerialize.getClass().getName()+"]");
            }
        } catch (IOException e) {
            throw new ConfigLoadException(e, "加载配置文件PlayerDataSerialize.yml出错");
        }
    }

    static void close(){
        try {
            if (playerDataCase!=null)playerDataCase.close();
        }catch (Error|Exception e){
            new DataCloseError(e,"数据容器"+PlayerData.playerDataCase.getClass().getName()+"在关闭时发生异常！").printStackTrace();
        }

    }
}
