package cn.jja8.knapsackToGo4.sponge.patronSaint.all.V2.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReadFile {
    /**
     *
     * @param file 要读取的文件
     * @return null 如果文件无法读取
     */
    public static String readFormFile(File file) throws IOException {
        long fileLen = file.length();
        if (fileLen>Integer.MAX_VALUE){
            throw new IOException("文件太大无法读取");
        }
        byte[] bytes = new byte[(int)fileLen];
        FileInputStream fileInputStream = new FileInputStream(file);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) fileInputStream.read();
        }
        fileInputStream.close();
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void WriteToFile(String s, File file) throws IOException {
        File pa = file.getParentFile();
        if (pa!=null){
            pa.mkdirs();
        }
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
    }
}
