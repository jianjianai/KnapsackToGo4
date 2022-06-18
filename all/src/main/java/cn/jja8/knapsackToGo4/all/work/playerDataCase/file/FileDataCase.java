package cn.jja8.knapsackToGo4.all.work.playerDataCase.file;

import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;
import cn.jja8.knapsackToGo4.all.work.Go4Player;
import cn.jja8.knapsackToGo4.all.work.PlayerDataCase;
import cn.jja8.knapsackToGo4.all.work.PlayerDataCaseLock;

import java.io.File;

public class FileDataCase implements PlayerDataCase {
    File dataFile;
    String serverName;
    public FileDataCase(FileDataCaseSetUp fileDataCaseSetUp) {
        this.dataFile = new File(fileDataCaseSetUp.file);
        this.serverName = fileDataCaseSetUp.serverName;
        dataFile.mkdirs();

    }
    @Override
    public PlayerDataCaseLock getPlayerDataLock(Go4Player player) {
        File playerDataFile = new File(dataFile, player.getUUID() +".dat");
        File playerLockFile = new File(dataFile, player.getUUID() +".lock");
        FileLock fileLock = FileLock.getFileLock(playerLockFile, this.serverName);
        if (fileLock==null){
            return null;
        }
        return new FileDataCaseLock(playerDataFile,fileLock);
    }

    @Override
    public String getPlayerDataLockServerName(Go4Player player) {
        File playerLockFile = new File(dataFile, player.getUUID() +".lock");
        return FileLock.getLockServerName(playerLockFile);
    }
}
