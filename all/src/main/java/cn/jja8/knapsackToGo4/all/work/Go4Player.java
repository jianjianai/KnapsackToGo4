package cn.jja8.knapsackToGo4.all.work;

import java.util.UUID;

/**
 *
 * 代表一个玩家
 * 应该实现equals和hashCode
 * */
public interface Go4Player {
    /**
     * 用于给玩家反馈加载信息
     * */
    void loadingMessage(String message);
    /**
     * 获取玩家名称
     * */
    String getName();
    /**
     * 获取玩家UUID
     * */
    UUID getUUID();
}


