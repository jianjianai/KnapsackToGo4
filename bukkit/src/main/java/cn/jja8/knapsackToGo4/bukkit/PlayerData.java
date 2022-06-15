package cn.jja8.knapsackToGo4.bukkit;


import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.PlayerDataSerialize;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;

import java.io.File;
import java.io.IOException;

/**
 * 一个中介类，为了兼容多版本
 * 可在使用别的插件在load阶段给playerDataSupport赋值，用于兼容更多版本。
 * */
public class PlayerData {
    public static PlayerDataCase playerDataCase = null;
    public static PlayerDataSerialize playerDataSerialize = null;

    static void load() throws ConfigLoadException {
        if (playerDataCase==null){
            String playerDataCase = null;
            try {
                playerDataCase = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"PlayerDataCase.yml"),new cn.jja8.knapsackToGo4.bukkit.config.playerData.PlayerDataCase()).playerDataCaseType;
                PlayerDataCaseType playerDataCaseType = PlayerDataCaseType.valueOf(playerDataCase);
                PlayerData.playerDataCase = playerDataCaseType.getFileDataCase();
            } catch (IOException e) {
                throw new ConfigLoadException(e,"加载配置文件PlayerDataCase.yml出错");
            } catch (IllegalArgumentException e){
                throw new NoOptions(e,"加载配置文件PlayerDataCase.yml中"+playerDataCase+"值不存在！");
            }
        }else {
            KnapsackToGo4.knapsackToGo4.getLogger().info("已加载数据容器扩展。["+playerDataCase.getClass().getName()+"]");
        }
        if (playerDataSerialize==null){
            String playerDataSerialize = null;
            try {
                playerDataSerialize = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"PlayerDataSerialize.yml"),new cn.jja8.knapsackToGo4.bukkit.config.playerData.PlayerDataSerialize()).playerDataSerializeType;
                PlayerDataSerializeType playerDataSerializeType = PlayerDataSerializeType.valueOf(playerDataSerialize);
                PlayerData.playerDataSerialize = playerDataSerializeType.getYamlDataSerialize();
            } catch (IOException e) {
                throw new ConfigLoadException(e,"加载配置文件PlayerDataSerialize.yml出错");
            } catch (IllegalArgumentException e){
                throw new NoOptions(e,"加载配置文件PlayerDataSerialize.yml中"+playerDataSerialize+"值不存在！");
            }
        }else {
            KnapsackToGo4.knapsackToGo4.getLogger().info("已加载数序列化方法扩展。["+playerDataSerialize.getClass().getName()+"]");
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
