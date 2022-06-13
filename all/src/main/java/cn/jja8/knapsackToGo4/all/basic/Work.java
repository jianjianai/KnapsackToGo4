package cn.jja8.knapsackToGo4.all.basic;

import java.util.HashMap;
import java.util.Map;

/**
 * 同步的工作主要逻辑。
 * 应该在合适的时候调用对于的方法。使同步正常工作。
 * 应该实现Player，PlayerDataCase，PlayerDataCaseLock，PlayerDataSerialize，TaskManager
 */

public class Work {
    final PlayerDataCase playerDataCase;
    final PlayerDataSerialize playerDataSerialize;
    final TaskManager taskManager;
    final SetUp setUp;

    /**
     * 构造使其开始工作，全程应该只使用一个对象。
     * */
    public Work(PlayerDataCase playerDataCase, PlayerDataSerialize playerDataSerialize, TaskManager taskManager, SetUp setUp) {
        this.playerDataCase = playerDataCase;
        this.playerDataSerialize = playerDataSerialize;
        this.taskManager = taskManager;
        this.setUp = setUp;
    }

    Map<Player,PlayerDataCaseLock> loadFinishPlayerLockMap = new HashMap<>();
    Map<Player,Task> loadingTaskMap = new HashMap<>();

    /**
     * 当玩家加入时调用此方法
     **/
    public void playerJoin(Player player){

    }

    /**
     * 当玩家推出时调用此方法
     * */
    public void playerQuit(Player player){
        Task task = loadingTaskMap.remove(player);
        if (task!=null){
            task.cancel();
        }
        PlayerDataCaseLock playerDataCaseLock = loadFinishPlayerLockMap.remove(player);
        if (playerDataCaseLock==null){
            return;
        }
        playerDataSerialize.save(player  )
        //保持数据
    }

    /**
     * 玩家是否还在加载中，如果在加载中应该阻止玩家的任何行为，防止例如刷物品这类事情。
     * */
    public boolean isLoading(Player player){
        return !loadFinishPlayerLockMap.containsKey(player);
    }
}
