package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.bukkit.basic.DataCase.file.FileDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCase;

public enum PlayerDataCaseType {
    File(FileDataCase.get());

    private final PlayerDataCase fileDataCase;
    PlayerDataCaseType(PlayerDataCase fileDataCase) {
        this.fileDataCase = fileDataCase;
    }
    public PlayerDataCase getFileDataCase() {
        return fileDataCase;
    }
}
