package cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file;

import cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.PlayerDataLock;
import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;

import java.io.*;


public abstract class LockAndDataFile implements PlayerDataLock {
    FileLock fileLock;
    private final File playerDataFile;

    public LockAndDataFile(File playerDataFile) {
        this.playerDataFile = playerDataFile;
    }

    @Override
    public void unlock() {
        fileLock.unLock();
    }

    @Override
    public void saveData() {
        try {
            playerDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(playerDataFile)){
            saveToOutputStream(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void loadData() {
        if (playerDataFile.exists()){
            try (FileInputStream inputStream = new FileInputStream(playerDataFile)){
                loadFromInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将玩家的数据保存到给定流中，实现的类可不用关闭流
     * */
    public abstract void saveToOutputStream(OutputStream outputStream);

    /**
     * 从给定流中加载玩家数据，实现的类可不用关闭流
     * */
    public abstract void loadFromInputStream(InputStream inputStream);
}
