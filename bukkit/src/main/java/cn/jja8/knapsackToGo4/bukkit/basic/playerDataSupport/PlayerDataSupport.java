package cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport;
import org.bukkit.entity.Player;

/**
 * 用于在同步的数据库中管理玩家数据
 * */
public interface PlayerDataSupport {
    /**
     * 获得这个玩家的锁
     * @return null 此玩家已经被锁
     * @param serverName 上锁服务器名称
     * */
    PlayerDataLock getPlayerDataLock(Player player,String serverName);

    /**
     * 获取占有玩家锁的服务器名称
     * @return null 没有服务器占有锁
     * */
    String getPlayerDataLockServerName(Player player);
}
