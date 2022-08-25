package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.*;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;

public class SpongeWork extends Work {
    /**
     * 构造使其开始工作，全程应该只使用一个对象。
     */
    public SpongeWork(PlayerDataCase playerDataCase, PlayerDataSerialize playerDataSerialize, TaskManager taskManager, SetUp setUp, Logger logger) {
        super(playerDataCase, playerDataSerialize, taskManager, setUp, logger);
    }

    @Listener
    public void playerJoin(ServerSideConnectionEvent.Join event){
        playerJoin(new SpongeG4Player(event.player()));
    }

    @Listener
    public void playerQuit(ServerSideConnectionEvent.Disconnect event){
        playerQuit(new SpongeG4Player(event.player()));
    }

    @Listener
    public void InteractEntityEvent(InteractEntityEvent event){//实体交互事件。针对实体的所有交互的基本事件。
        Entity entity = event.entity();
        if (!(event instanceof Player)){
            return;
        }
        if (!isLoaded(new SpongeG4Player((Player)entity))) {
            event.setCancelled(true);
        }
    }
}
