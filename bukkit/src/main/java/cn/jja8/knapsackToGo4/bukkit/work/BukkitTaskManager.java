package cn.jja8.knapsackToGo4.bukkit.work;

import cn.jja8.knapsackToGo4.all.work.Task;
import cn.jja8.knapsackToGo4.all.work.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BukkitTaskManager implements TaskManager {
    private final Plugin plugin;
    //主线程执行队列，用于缓慢主线程执行
    BlockingQueue<Runnable> synchronizedRuns = new LinkedBlockingQueue<>();
    public BukkitTaskManager(Plugin plugin) {
        this.plugin = plugin;

        //每次只执行25毫秒，避免服务器卡顿
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long stopTile = System.currentTimeMillis()+25;
            while (stopTile>System.currentTimeMillis()){
                Runnable runnable = synchronizedRuns.poll();
                if (runnable!=null){
                    try {
                        runnable.run();
                    }catch (Throwable throwable){
                        throwable.printStackTrace();
                    }
                }else {
                    break;
                }
            }
        },1,1);
    }


    @Override
    public void runSynchronization(Runnable runnable) {
        if (!synchronizedRuns.offer(runnable)) {
            //如果队列满了就直接执行，但是队列是不可能满的awa
            Bukkit.getScheduler().runTask(plugin,runnable);
        }
    }

    @Override
    public void runAsynchronous(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,runnable);
    }

    @Override
    public void runSynchronization(Runnable runnable, long time) {
        long task = time/50;
        Bukkit.getScheduler().runTaskLater(plugin,runnable,task);
    }

    @Override
    public void runAsynchronous(Runnable runnable, long time) {
        long task = time/50;
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin,runnable,task);
    }

    @Override
    public Task runCircularTask(long time, Runnable runnable) {
        long task = time/50;
        if (task<1){
            task = 1;
        }
        return new BukkitTask(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin,runnable,task,task));
    }

    static class BukkitTask implements Task{
        private final org.bukkit.scheduler.BukkitTask bukkitTask;
        BukkitTask(org.bukkit.scheduler.BukkitTask bukkitTask) {
            this.bukkitTask = bukkitTask;
        }
        @Override
        public void cancel() {
            bukkitTask.cancel();
        }
    }
}
