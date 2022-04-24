package cn.jja8.knapsackToGo4.all.veryUtil;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;

public class FileLock {
    FileOutputStream fileOutputStream;
    FileChannel channel;
    java.nio.channels.FileLock fileLock;
    File file,file1;

    public FileLock(FileOutputStream fileOutputStream, FileChannel channel, java.nio.channels.FileLock fileLock, File file,File file1) {
        this.fileOutputStream = fileOutputStream;
        this.channel = channel;
        this.fileLock = fileLock;
        this.file = file;
        this.file1 = file1;
    }

    public void unLock(){
        try {
            if (fileLock!=null){
                fileLock.close();
            }
        }catch (IOException  ioException1){
            ioException1.printStackTrace();
        }
        try {
            if (channel!=null){
                channel.close();
            }
        }catch (IOException  ioException1){
            ioException1.printStackTrace();
        }
        try {
            if (fileOutputStream!=null){
                fileOutputStream.close();
            }
        }catch (IOException  ioException1){
            ioException1.printStackTrace();
        }
        file.delete();
        file1.delete();
    }

    /**
     * 给指定文件上锁
     * @return null 文件已经被锁
     * **/
    public static FileLock getFileLock(File file,String LockServerName){
        File pr = file.getParentFile();
        pr.mkdirs();
        try {
            file.createNewFile();
        }catch (IOException ioException){
            ioException.printStackTrace();
            return null;
        }
        File file1 = new File(pr,file.getName()+".inf");
        FileOutputStream fileOutputStream = null;
        FileChannel ch = null;
        java.nio.channels.FileLock lock = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            ch = fileOutputStream.getChannel();
            lock = ch.tryLock();
        } catch (OverlappingFileLockException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lock!=null){
            try (FileOutputStream fileOutputStream1 = new FileOutputStream(file1);){
                fileOutputStream1.write(LockServerName.getBytes(StandardCharsets.UTF_8));
                return new FileLock(fileOutputStream,ch,lock,file,file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭
        if (ch!=null) {
            try {
                ch.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream!=null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * 获得上锁服务器名称
     * @return null 没被上锁
     * */
    public static String getLockServerName(File file){
        File pr = file.getParentFile();
        File file1 = new File(pr,file.getName()+".inf");
        if (!file1.isFile()){
            return null;
        }
        if (!file.isFile()){
            return null;
        }
        java.nio.channels.FileLock lock = null;
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                FileChannel ch = fileOutputStream.getChannel();
        ){
            lock = ch.lock();
        } catch (OverlappingFileLockException ignored) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lock != null) {
            return null;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file1);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ){
            int i;
            while ((i=fileInputStream.read())!=-1){
                byteArrayOutputStream.write(i);
            }
            return new String(byteArrayOutputStream.toByteArray(),StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
