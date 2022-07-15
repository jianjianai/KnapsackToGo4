package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.Task;
import cn.jja8.knapsackToGo4.all.work.TaskManager;
import cn.jja8.knapsackToGo4.sponge.KnapsackToGo4;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.ScheduledTaskFuture;

import java.util.concurrent.TimeUnit;

public class SpongeTaskManager implements TaskManager {
    public final SpongeTaskManager spongeTaskManager = new SpongeTaskManager();
    private SpongeTaskManager(){}

    @Override
    public void runSynchronization(Runnable runnable) {
        Sponge.asyncScheduler().executor(KnapsackToGo4.pluginContainer).execute(runnable);
    }

    @Override
    public void runAsynchronous(Runnable runnable) {
        Sponge.server().scheduler().executor(KnapsackToGo4.pluginContainer).execute(runnable);
    }

    @Override
    public Task runCircularTask(long time, Runnable runnable) {
        ScheduledTaskFuture<?> scheduledTaskFuture = Sponge.asyncScheduler().executor(KnapsackToGo4.pluginContainer).scheduleAtFixedRate(runnable,time,time, TimeUnit.MILLISECONDS);
        return new SpongeTask(scheduledTaskFuture);
    }

    static class SpongeTask implements Task{
        final ScheduledTaskFuture<?> scheduledTaskFuture;
        public SpongeTask(ScheduledTaskFuture<?> scheduledTaskFuture) {
            this.scheduledTaskFuture = scheduledTaskFuture;
        }
        @Override
        public void cancel() {
            scheduledTaskFuture.cancel(false);
        }
    }
}
