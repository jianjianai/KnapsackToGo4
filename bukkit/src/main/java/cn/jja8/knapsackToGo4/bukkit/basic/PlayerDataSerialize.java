package cn.jja8.knapsackToGo4.bukkit.basic;

import org.bukkit.entity.Player;

/**
 * 玩家数据的序列化
 * */
public interface PlayerDataSerialize {
    /**
     * 将玩家的数据序列化
     * */
    byte[] save(Player player);
    /**
     * 将玩家的数据从序列化加载
     * */
    void load(Player player,byte[] bytes);
}
