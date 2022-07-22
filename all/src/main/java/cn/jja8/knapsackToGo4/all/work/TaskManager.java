package cn.jja8.knapsackToGo4.all.work;
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
     * 在主线程上执行任务
     * @param time 多久之后，单位毫秒
     * */
    void runSynchronization(Runnable runnable,long time);
    /**
     * 在异步执行任务
     * @param time 多久之后，单位毫秒
     * */
    void runAsynchronous(Runnable runnable,long time);
    /**
     * 运行循环任务,通常是异步的
     * @param time （单位毫秒）多久循环一次。
     * */
    Task runCircularTask(long time,Runnable runnable);
}
