package cn.jja8.knapsackToGo4.bukkit.basic.dataCase.file;

import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCaseLock;

import java.io.*;

public class FileDataCaseLock implements PlayerDataCaseLock {
    File playerDataFile;
    FileLock fileLock;
    public FileDataCaseLock(File playerDataFile, FileLock fileLock) {
        this.playerDataFile = playerDataFile;
        this.fileLock = fileLock;
    }

    @Override
    public void saveData(byte[] bytes) {
        try {
            playerDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(playerDataFile);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public byte[] loadData() {
        if (playerDataFile.exists()){
            FileInputStream inputStream = null;
            ByteArrayOutputStream byteArrayOutputStream = null;
            byte[] data = null;
            try {
                inputStream = new FileInputStream(playerDataFile);
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bs = new byte[128];
                int size;
                while ((size=inputStream.read(bs))!=-1){
                    byteArrayOutputStream.write(bs,0,size);
                }
                inputStream.close();
                data=byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    byteArrayOutputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
            return data;
        }else {
            return null;
        }
    }

    @Override
    public void unlock() {
        fileLock.unLock();
    }
}
