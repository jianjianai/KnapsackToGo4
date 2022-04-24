import cn.jja8.knapsackToGo4.all.veryUtil.FileLock;

import java.io.File;

public class lock {
    public static void main(String[] args) throws InterruptedException {
        File file = new File("C:/myWorld/asd.lock");
        System.out.println("--------------------------------------------------------");
        System.out.println(FileLock.getLockServerName(file));
        System.out.println("--------------------------------------------------------");


        FileLock fileLock = FileLock.getFileLock(file ,"6666");

        new Thread(() -> {
            FileLock fileLock1 = FileLock.getFileLock(file,"qwe");
            if (fileLock1==null){
                System.out.println("--------------------------------------------------------");
                System.out.println(FileLock.getLockServerName(file));
                System.out.println("--------------------------------------------------------");
            }
        }).start();

        Thread.sleep(1000);


        fileLock.unLock();

        System.out.println("--------------------------------------------------------");
        System.out.println(FileLock.getLockServerName(file));
        System.out.println("--------------------------------------------------------");
    }
}
