package cn.jja8.knapsackToGo4.sponge.config.playerData;

import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.FileDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.FileDataCaseSetUp;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.MysqlDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.mysql.MysqlDataCaseSetUp;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.SqliteDataCase;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.SqliteDataCaseSetUp;
import cn.jja8.knapsackToGo4.sponge.KnapsackToGo4;
import cn.jja8.knapsackToGo4.sponge.error.DataCaseLoadError;
import cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file.YamlConfig;
import cn.jja8.knapsackToGo4.sponge.work.SpongeLogger;
import cn.jja8.knapsackToGo4.sponge.work.SpongeTaskManager;

import java.io.File;

public enum PlayerDataCaseType {
    File,
    Sqlite,
    Mysql;


    public PlayerDataCase getDataCase() throws DataCaseLoadError {
        try{
            switch (this){
                default:return null;
                case File:
                    return new FileDataCase(
                            YamlConfig.loadFromFile(new File(KnapsackToGo4.getKnapsackToGo4().configFile,"FileDataCaseSetUp.yml"), new FileDataCaseSetUp()),
                            SpongeLogger.get()
                    );
                case Sqlite:
                    return new SqliteDataCase(
                            YamlConfig.loadFromFile(new File(KnapsackToGo4.getKnapsackToGo4().configFile,"SqliteDataCaseSetUp.yml"), new SqliteDataCaseSetUp()),
                            SpongeTaskManager.get(),
                            SpongeLogger.get()
                    );
                case Mysql:
                    return new MysqlDataCase(
                            YamlConfig.loadFromFile(new File(KnapsackToGo4.getKnapsackToGo4().configFile,"MysqlDataCaseSetUp.yml"), new MysqlDataCaseSetUp()),
                            SpongeTaskManager.get(),
                            SpongeLogger.get()
                    );
            }
        }catch (Error|Exception e){
            throw new DataCaseLoadError(e,"数据容器加载出错！");
        }

    }
}
