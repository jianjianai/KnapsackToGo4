package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.file.FileDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.dataCase.jdbc.JdbcDataCase;

public enum PlayerDataCaseType {
    File(FileDataCase.get()),
    JDBC(JdbcDataCase.get())
    ;

    private final PlayerDataCase fileDataCase;
    PlayerDataCaseType(PlayerDataCase fileDataCase) {
        this.fileDataCase = fileDataCase;
    }
    public PlayerDataCase getFileDataCase() {
        return fileDataCase;
    }
}
