package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.FileDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.FileDataCaseSetUp;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.MysqlDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.MysqlDataCaseSetUp;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.SqliteDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.SqliteDataCaseSetUp;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.error.DataCaseLoadError;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitTaskManager;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;

import java.io.File;

public enum PlayerDataCaseType {
    File,
    Sqlite,
    Mysql;


    public PlayerDataCase getFileDataCase() throws DataCaseLoadError {
        try{
            switch (this){
                default:return null;
                case File:
                    return new FileDataCase(YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"FileDataCaseSetUp.yml"),new FileDataCaseSetUp()));
                case Sqlite:
                    return new SqliteDataCase(YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"SqliteDataCaseSetUp.yml"),new SqliteDataCaseSetUp()),new BukkitTaskManager(KnapsackToGo4.knapsackToGo4),KnapsackToGo4.knapsackToGo4.getLogger());
                case Mysql:
                    return new MysqlDataCase(YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"MysqlDataCaseSetUp.yml"),new MysqlDataCaseSetUp()),new BukkitTaskManager(KnapsackToGo4.knapsackToGo4),KnapsackToGo4.knapsackToGo4.getLogger());
            }
        }catch (Error|Exception e){
            throw new DataCaseLoadError("数据容器加载出错！");
        }

    }
}
