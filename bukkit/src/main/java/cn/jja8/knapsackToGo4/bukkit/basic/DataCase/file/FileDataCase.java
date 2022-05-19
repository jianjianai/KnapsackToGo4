package cn.jja8.knapsackToGo4.bukkit.basic.DataCase.file;

import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCase;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCaseLock;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class FileDataCase implements PlayerDataCase {
    File dataFile;
    public FileDataCase() throws IOException {
        FileDataCaseSetUp fileDataCaseSetUp = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"FileDataCaseSetUp.yml"),new FileDataCaseSetUp());
        this.dataFile = new File(fileDataCaseSetUp.file);
        dataFile.mkdirs();
    }

    @Override
    public PlayerDataCaseLock getPlayerDataLock(Player player, String serverName) {
        File playerDataFile = new File(dataFile, player.getUniqueId() +".dat");
        File playerLockFile = new File(dataFile, player.getUniqueId() +".lock");
        FileLock fileLock = FileLock.getFileLock(playerLockFile,serverName);
        if (fileLock==null){
            return null;
        }
        return new FileDataCaseLock(playerDataFile,fileLock);
    }

    @Override
    public String getPlayerDataLockServerName(Player player) {
        File playerLockFile = new File(dataFile, player.getUniqueId() +".lock");
        return FileLock.getLockServerName(playerLockFile);
    }
}
