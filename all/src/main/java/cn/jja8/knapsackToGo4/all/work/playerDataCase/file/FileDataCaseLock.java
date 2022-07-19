package cn.jja8.knapsackToGo4.all.work.playerDataCase.file;

import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;
import cn.jja8.knapsackToGo4.all.work.PlayerDataCaseLock;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.file.error.WriteDataError;
import cn.jja8.knapsackToGo4.all.work.playerDataCase.sqlite.error.LoadDataError;

import java.io.*;

public class FileDataCaseLock implements PlayerDataCaseLock {
    FileDataCase fileDataCase;
    File playerDataFile;
    FileLock fileLock;
    public FileDataCaseLock(FileDataCase fileDataCase, File playerDataFile, FileLock fileLock) {
        this.fileDataCase = fileDataCase;
        this.playerDataFile = playerDataFile;
        this.fileLock = fileLock;
    }

    @Override
    public void saveData(byte[] bytes) {
        try {
            playerDataFile.createNewFile();
        } catch (IOException e) {
            throw new WriteDataError(fileDataCase.getLogger(),e,"创建文件失败！");
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(playerDataFile);
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            throw new WriteDataError(fileDataCase.getLogger(),e,"意想不到的错误！");
        } catch (IOException e) {
            try {
                fileOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new WriteDataError(fileDataCase.getLogger(),e,"文件写入错误！");
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
                throw new LoadDataError(fileDataCase.getLogger(),e,"意想不到的错误！");
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
                throw new LoadDataError(fileDataCase.getLogger(),e,"文件读取错误！");
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
