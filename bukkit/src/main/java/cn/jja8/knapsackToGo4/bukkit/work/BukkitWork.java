package cn.jja8.knapsackToGo4.bukkit.work;

import cn.jja8.knapsackToGo4.all.work.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class BukkitWork extends Work implements Listener {
    /**
     * 构造使其开始工作，全程应该只使用一个对象。
     */
    public BukkitWork(PlayerDataCase playerDataCase, PlayerDataSerialize playerDataSerialize, TaskManager taskManager, SetUp setUp, BukkitLogger logger) {
        super(playerDataCase, playerDataSerialize, taskManager, setUp,logger);
    }

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent event){//玩家离开服务器
        playerQuit(new BukkitGo4Player(event.getPlayer()));
    }
    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){//玩家进入服务器
        playerJoin(new BukkitGo4Player(event.getPlayer()));
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event){//玩家丢东西
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void EntityPickupItemEvent(EntityPickupItemEvent event){//物品拾取事件
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        if (isLoaded(new BukkitGo4Player((Player) event.getEntity()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event){//对象或空气进行交互
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void EntityDamageByBlockEvent(EntityDamageByBlockEvent event){//实体受到方块伤害
        if (!(event.getEntity() instanceof Player)){
            return;
        }
        if (isLoaded(new BukkitGo4Player((Player) event.getEntity()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event){//点击物品栏
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }
        if (isLoaded(new BukkitGo4Player((Player) event.getWhoClicked()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event){//聊天
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event){//命令
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event){//实体攻击实体
        if (event.getEntity() instanceof Player){
            if (isLoaded(new BukkitGo4Player((Player) event.getEntity()))) {
                return;
            }
            event.setCancelled(true);
        }else if (event.getDamager() instanceof Player){
            if (isLoaded(new BukkitGo4Player((Player) event.getDamager()))) {
                return;
            }
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void PlayerSwapHandItemsEvent(PlayerSwapHandItemsEvent event){//玩家切换副手
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }
    @EventHandler
    public void WanJiaYiDong(PlayerMoveEvent event){//玩家移动时
        if (isLoaded(new BukkitGo4Player(event.getPlayer()))) {
            return;
        }
        event.setCancelled(true);
    }


}
