package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.*;
import org.spongepowered.api.event.Listener;

public class SpongeWork extends Work {
    /**
     * 构造使其开始工作，全程应该只使用一个对象。
     */
    public SpongeWork(PlayerDataCase playerDataCase, PlayerDataSerialize playerDataSerialize, TaskManager taskManager, SetUp setUp, Logger logger) {
        super(playerDataCase, playerDataSerialize, taskManager, setUp, logger);
    }

    @Listener
    public void playerJoin(){

    }
}
