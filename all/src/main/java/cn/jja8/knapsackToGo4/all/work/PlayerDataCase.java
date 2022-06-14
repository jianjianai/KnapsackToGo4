package cn.jja8.knapsackToGo4.all.work;

/**
 * 用于在同步的数据库中管理玩家数据
 * */
public interface PlayerDataCase {
    /**
     * 获得这个玩家的锁
     * @return null 此玩家已经被锁
     * */
    PlayerDataCaseLock getPlayerDataLock(Go4Player go4Player);

    /**
     * 获取占有玩家锁的服务器名称
     * @return null 没有服务器占有锁
     * */
    String getPlayerDataLockServerName(Go4Player go4Player);

    /**
     * 关闭，一般在服务器关闭时
     * */
    default void close(){};
}
