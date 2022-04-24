package cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport;

public interface PlayerDataLock {
    /**
     * 保存某玩家数据到公共的数据库中
     * */
    void saveData() ;
    /**
     * 从公共的数据库中加载某玩家的数据
     * */
    void loadData();
    /**
     * 解锁，并且释放资源
     * */
    void unlock();
}
