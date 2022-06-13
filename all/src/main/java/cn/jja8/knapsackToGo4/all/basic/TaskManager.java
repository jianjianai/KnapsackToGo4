package cn.jja8.knapsackToGo4.all.basic;
/**
 * 一个任务接口
 * */
public interface TaskManager {
    /**
     * 在主线程上执行任务
     * */
    void runSynchronization(Runnable runnable);
    /**
     * 在异步执行任务
     * */
    void runAsynchronous(Runnable runnable);
    /**
     * 运行循环任务
     * @param time （单位毫秒）多久循环一次。
     * */
    Task runCircularTask(long time,Runnable runnable);
}
