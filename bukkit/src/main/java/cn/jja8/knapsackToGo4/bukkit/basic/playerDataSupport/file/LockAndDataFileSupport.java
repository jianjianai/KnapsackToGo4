package cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file;

import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.PlayerDataLock;
import cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.PlayerDataSupport;
import cn.jja8.myWorld.all.veryUtil.FileLock;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public abstract class LockAndDataFileSupport implements PlayerDataSupport {
    File dataFile;
    public LockAndDataFileSupport() throws IOException {
        DataFileLocation dataFileLocation = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"DataFileLocation.yml"),new DataFileLocation());
        this.dataFile = new File(dataFileLocation.file);
        dataFile.mkdirs();
    }
    @Override
    public PlayerDataLock getPlayerDataLock(Player player, String serverName) {
        File playerDataFile = new File(dataFile, player.getUniqueId() +".dat");
        File playerLockFile = new File(dataFile, player.getUniqueId() +".lock");
        FileLock fileLock = FileLock.getFileLock(playerLockFile,serverName);
        if (fileLock!=null){
            LockAndDataFile lockAndDataFile = getLockAndDataFile(playerDataFile,player);
            lockAndDataFile.fileLock = fileLock;
            return lockAndDataFile;
        }
        return null;
    }

    @Override
    public String getPlayerDataLockServerName(Player player) {
        File playerLockFile = new File(dataFile, player.getUniqueId() +".lock");
        return FileLock.getLockServerName(playerLockFile);
    }


    abstract LockAndDataFile getLockAndDataFile(File playerDataFile, Player player);

}
