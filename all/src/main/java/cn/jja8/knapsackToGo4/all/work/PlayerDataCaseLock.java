package cn.jja8.knapsackToGo4.all.work;

public interface PlayerDataCaseLock {
    /**
     * 保存某玩家数据到公共的数据库中
     *
     * 如果调用此方法的的实现抛出异常会自动重试
     * */
    void saveData(byte[] bytes) throws Throwable;
    /**
     * 从公共的数据库中加载某玩家的数据
     *
     * 如果调用此方法的的实现抛出异常会自动重试
     * */
    byte[] loadData() throws Throwable;
    /**
     * 解锁，并且释放资源
     *
     * 如果调用此方法的的实现抛出异常会自动重试
     * */
    void unlock() throws Throwable;
}
