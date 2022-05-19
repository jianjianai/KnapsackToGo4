package cn.jja8.knapsackToGo4.bukkit.work;


import cn.jja8.knapsackToGo4.bukkit.ConfigBukkit;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerData;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataCaseLock;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;


/**
 * 主要负责管理玩家的数据加载和保存
 * 包括玩家背包，玩家下线时的位置。
 * ”玩家数据“字段负责玩家数据的存储，如果需要使用其他保存方式可以在load阶段对他赋值。
 * */
public class PlayerDataManager implements Listener {
    //玩家和锁map
    Map<Player, PlayerDataCaseLock> playerLockMap = new HashMap<>();
    //玩家和加载任务map
    Map<Player, BukkitRunnable> playerLoadRunMap = new HashMap<>();
    //玩家加载完成后运行队列
    Map<Player, Queue<Runnable>> playerLoadFinishedToRunMap = new HashMap<>();
    public PlayerDataManager() {
        //事件监听器
        KnapsackToGo4.knapsackToGo4.getServer().getPluginManager().registerEvents(this, KnapsackToGo4.knapsackToGo4);
        //自动保存
        KnapsackToGo4.knapsackToGo4.getServer().getScheduler().runTaskTimerAsynchronously(
                KnapsackToGo4.knapsackToGo4,
                () -> playerLockMap.forEach((player, playerDataCaseLock) -> playerDataCaseLock.saveData(PlayerData.playerDataSerialize.save(player))),
                20L * ConfigBukkit.playerDataConfig.自动保存时间,
                20L * ConfigBukkit.playerDataConfig.自动保存时间
        );
    }
    /**
     * 如果玩家没有加载完成，就加载完成后执行。如果已经加载完成就立即执行。如果玩家还没加载完成数据就退出服务器了就不会执行。
     * */
    public void playerLoadFinishedToRun(Player player, Runnable runnable){
        if (playerLoadRunMap.get(player)==null){
            runnable.run();
        }else {
            Queue<Runnable> queue = playerLoadFinishedToRunMap.get(player);
            if (queue==null){
                queue = new ArrayDeque<>();
                playerLoadFinishedToRunMap.put(player,queue);
            }
            queue.add(runnable);
        }
    }

    @EventHandler
    public void 玩家离开服务器(PlayerQuitEvent event){
        playerLoadFinishedToRunMap.remove(event.getPlayer());
        //取消掉玩家数据加载任务
        BukkitRunnable rw = playerLoadRunMap.remove(event.getPlayer());
        if (rw!=null){
            if (!rw.isCancelled()){
                rw.cancel();
            }
        }
        //如果有成功获得锁就保存数据，并解锁。
        PlayerDataCaseLock lock = playerLockMap.remove(event.getPlayer());
        if (lock!=null){
            lock.saveData(PlayerData.playerDataSerialize.save(event.getPlayer()));
            lock.unlock();
        }
    }
    @EventHandler
    public void 玩家进入服务器(PlayerJoinEvent event){
        if (ConfigBukkit.playerDataConfig.玩家数据加载前保持背包为空){
            event.getPlayer().getInventory().clear();
        }
        BukkitRunnable 加载 = new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                PlayerDataCaseLock lock = PlayerData.playerDataCase.getPlayerDataLock(event.getPlayer(),ConfigBukkit.ServerConfig.服务器名称);
                if (lock==null) {
                    event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ConfigBukkit.lang.玩家数据加载_等待信息.replaceAll("<数>", String.valueOf(time))));
                    time++;
                    return;
                }
                playerLockMap.put(event.getPlayer(), lock);
                this.cancel();
                playerLoadRunMap.remove(event.getPlayer());
                PlayerData.playerDataSerialize.load(event.getPlayer(),lock.loadData());
                //加载完成任务
                Queue<Runnable> queue = playerLoadFinishedToRunMap.get(event.getPlayer());
                if (queue!=null){
                    while (true){
                        Runnable runnable = queue.poll();
                        if (runnable==null){
                            break;
                        }
                        runnable.run();
                    }
                }
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,null, new TextComponent(ConfigBukkit.lang.玩家数据加载_完成));
            }
        };
        playerLoadRunMap.put(event.getPlayer(),加载);
        加载.runTaskTimer(KnapsackToGo4.knapsackToGo4, 1, ConfigBukkit.playerDataConfig.玩家数据解锁检测间隙);
    }




    @EventHandler
    public void 玩家丢东西(PlayerDropItemEvent event){
        if (!playerLockMap.containsKey(event.getPlayer())){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void 物品拾取事件(EntityPickupItemEvent event){
        LivingEntity entity = event.getEntity();
        if (!(entity instanceof Player)){
            return;
        }
        if (!playerLockMap.containsKey(entity)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void 对象或空气进行交互(PlayerInteractEvent event){
        if (!playerLockMap.containsKey(event.getPlayer())){
            event.setCancelled(true);
        }
    }

    public void close() {
        new HashMap<>(playerLockMap).forEach((player, lockWork) -> {
            lockWork.saveData(PlayerData.playerDataSerialize.save(player));
            lockWork.unlock();
        });
    }
}
