package cn.jja8.knapsackToGo4.bukkit.work;

import cn.jja8.knapsackToGo4.all.work.Task;
import cn.jja8.knapsackToGo4.all.work.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class BukkitTaskManager implements TaskManager {
    private final Plugin plugin;
    public BukkitTaskManager(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void runSynchronization(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin,runnable);
    }

    @Override
    public void runAsynchronous(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin,runnable);
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
