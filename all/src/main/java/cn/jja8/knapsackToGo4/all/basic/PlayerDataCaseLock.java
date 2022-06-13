package cn.jja8.knapsackToGo4.all.basic;

public interface PlayerDataCaseLock {
    /**
     * 保存某玩家数据到公共的数据库中
     * */
    void saveData(byte[] bytes) ;
    /**
     * 从公共的数据库中加载某玩家的数据
     * */
    byte[] loadData();
    /**
     * 解锁，并且释放资源
     * */
    void unlock();
}
