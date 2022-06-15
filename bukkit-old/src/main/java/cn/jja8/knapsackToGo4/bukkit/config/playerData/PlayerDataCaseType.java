package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.file.FileDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.mysql.MysqlDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.sqlite.SqliteDataCase;

public enum PlayerDataCaseType {
    File,
    Sqlite,
    Mysql;


    public PlayerDataCase getFileDataCase() {
        switch (this){
            default:return null;
            case File:return FileDataCase.get();
            case Sqlite:return SqliteDataCase.get();
            case Mysql:return MysqlDataCase.get();
        }
    }
}
