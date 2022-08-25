package cn.jja8.knapsackToGo4.sponge;

import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.sponge.config.playerData.PlayerDataCaseString;
import cn.jja8.knapsackToGo4.sponge.config.playerData.PlayerDataCaseType;
import cn.jja8.knapsackToGo4.sponge.error.ConfigLoadException;
import cn.jja8.knapsackToGo4.sponge.error.DataCaseLoadError;
import cn.jja8.knapsackToGo4.sponge.error.NoOptionsError;
import cn.jja8.knapsackToGo4.sponge.error.NoPlayerDataSerialize;
import cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file.YamlConfig;
import cn.jja8.knapsackToGo4.sponge.work.SpongeLogger;

import java.io.File;
import java.io.IOException;

public class PlayerData {
    public static PlayerDataCase playerDataCase;

    public static PlayerDataSerialize playerDataSerialize;


    static void load() throws ConfigLoadException, DataCaseLoadError {
        if (playerDataCase==null){
            String playerDataCase = null;
            try {
                playerDataCase = YamlConfig.loadFromFile(new File(KnapsackToGo4.getKnapsackToGo4().configFile,"PlayerDataCase.yml"),new PlayerDataCaseString()).playerDataCaseType;
                PlayerDataCaseType playerDataCaseType = PlayerDataCaseType.valueOf(playerDataCase);
                PlayerData.playerDataCase = playerDataCaseType.getDataCase();
            } catch (IOException e) {
                throw new ConfigLoadException(e,"加载配置文件PlayerDataCase.yml出错");
            } catch (IllegalArgumentException e){
                throw new NoOptionsError(e,"加载配置文件PlayerDataCase.yml中"+playerDataCase+"值不存在！");
            }
        }else {
            SpongeLogger.get().info("已加载数据容器扩展。["+playerDataCase.getClass().getName()+"]");
        }
        if (playerDataSerialize==null){
            throw new NoPlayerDataSerialize("sponge版本的KTG4中不内置任何的Serialize(序列化)方式，请安装序列化扩展！");
        }else {
            SpongeLogger.get().info("已加载数序列化方法扩展。["+playerDataSerialize.getClass().getName()+"]");
        }
    }
}
