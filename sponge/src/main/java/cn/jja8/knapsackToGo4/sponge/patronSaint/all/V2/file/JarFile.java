package cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JarFile {
    /**
     * 从jar包中解压文件
     * @param jarClass jar包中的任意类
     * @param jarUrl jar包中的文件路径  例如 “/文件夹/123.jpg”
     * @param overwrite 是否覆盖文件
     * @param toFile 解压到的文件。
     * */
    public static void unzipFile(File toFile, Class jarClass, String jarUrl, boolean overwrite){
        if (!overwrite){
            if (toFile.exists()){
                return;
            }
        }
        URL url1 = jarClass.getClassLoader().getResource(jarUrl);
        File parentFile = toFile.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        try {
            toFile.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        try (InputStream inputStream = url1.openStream()){
            try (FileOutputStream fileOutputStream = new FileOutputStream(toFile)){
                for (int i;-1!=(i = inputStream.read());){
                    fileOutputStream.write(i);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
