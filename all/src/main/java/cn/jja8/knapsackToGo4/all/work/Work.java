package cn.jja8.knapsackToGo4.all.work;

import cn.jja8.knapsackToGo4.all.work.error.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

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
    final Logger logger;

    /**
     * 构造使其开始工作，全程应该只使用一个对象。
     * */
    public Work(PlayerDataCase playerDataCase, PlayerDataSerialize playerDataSerialize, TaskManager taskManager, SetUp setUp, Logger logger) {
        this.playerDataCase = playerDataCase;
        this.playerDataSerialize = playerDataSerialize;
        this.taskManager = taskManager;
        this.setUp = setUp;
        this.logger = logger;
        //自动保存
        taskManager.runCircularTask(1000 * setUp.AutoSave, () -> {
            playerLockMap.forEach((go4Player, playerDataCaseLock) -> {
                taskManager.runSynchronization(() -> {
                    //主线程获取玩家数据
                    byte[] data = serialize(go4Player);
                    //异步存储玩家数据
                    taskManager.runAsynchronous(() -> save(playerDataCaseLock,data));
                });
            });
        });
    }

    /**
     * 序列化玩家的数据
     * */
    private byte[] serialize(Go4Player go4Player) throws DataSerializeError{
        try {
            return playerDataSerialize.save(go4Player);
        }catch (Exception|Error e){
            throw new DataSerializeError(logger,e,"玩家"+ go4Player.getName()+"数据序列化错误！");
        }
    }
    /**
     * 保存玩家数据
     * */
    private void save(PlayerDataCaseLock playerDataCaseLock,byte[] data){
        for (int i = 0; i < 10; i++) {
            try {
                playerDataCaseLock.saveData(data);
                return;
            } catch (Exception | Error e) {
                new DataSaveError(logger, e, "玩家数据序保存错误！"+(i==0?"":"重试第"+i+"次")).printStackTrace();
                try {Thread.sleep(1000);} catch (InterruptedException ignored) {}
            }
        }
        throw new DataSaveError(logger, "玩家数据序保存错误！重试10次全部失败。");
    }


    //玩家和锁map
    private Map<Go4Player, PlayerDataCaseLock> playerLockMap = new HashMap<>();
    //玩家和加载任务map
    private Map<Go4Player, Task> playerLoadRunMap = new HashMap<>();
    //玩家加载完成后运行队列
    private Map<Go4Player, Queue<Runnable>> playerLoadFinishedToRunMap = new HashMap<>();

    /**
     * 如果玩家没有加载完成，就加载完成后执行。如果已经加载完成就立即执行。如果玩家还没加载完成数据就退出服务器了就不会执行。
     * */
    public void playerLoadFinishedToRun(Go4Player go4Player, Runnable runnable){
        if (playerLoadRunMap.get(go4Player)==null){
            runnable.run();
        }else {
            Queue<Runnable> queue = playerLoadFinishedToRunMap.get(go4Player);
            if (queue==null){
                queue = new ArrayDeque<>();
                playerLoadFinishedToRunMap.put(go4Player,queue);
            }
            queue.add(runnable);
        }
    }

    /**
     * 关闭是调用此方法
     * */
    public void close() {
        new HashMap<>(playerLockMap).forEach((player, lockWork) -> {
            save(lockWork,serialize(player));
            try {
                lockWork.unlock();
            }catch (Exception|Error e){
                new DataUnLockError(logger,e,"在为"+player.getName()+"解锁时发生异常！").printStackTrace();
            }
        });
    }

    /**
     * 在玩家推出时调用此方法，使他开始工作
     * */
    public void playerQuit(Go4Player go4Player){//玩家离开服务器
        playerLoadFinishedToRunMap.remove(go4Player);
        //取消掉玩家数据加载任务
        Task rw = playerLoadRunMap.remove(go4Player);
        if (rw!=null){
            rw.cancel();
        }
        //如果有成功获得锁就保存数据，并解锁。
        PlayerDataCaseLock lock = playerLockMap.remove(go4Player);
        if (lock!=null){
            byte[] data = null;
            try {//防止保存数据异常影响到解锁
                data = serialize(go4Player);
            }catch (DataSerializeError e){
                e.printStackTrace();
            }
            //在异步保存数据并解锁
            byte[] finalData = data;

            taskManager.runAsynchronous(() -> {
                if (finalData !=null){
                    save(lock,finalData);
                }

                for (int i = 0; i < 10; i++) {
                    try {
                        lock.unlock();
                        return;
                    } catch (Exception | Error e) {
                        new DataSaveError(logger, e, "在为"+ go4Player.getName()+"解锁时发生异常！"+(i==0?"":"重试第"+i+"次")).printStackTrace();
                        try {Thread.sleep(1000);} catch (InterruptedException ignored) {}
                    }
                }
                throw new DataSaveError(logger, "在为"+ go4Player.getName()+"解锁时发生异常！重试10次全部失败。");
            });
        }
    }
    /**
     * 在玩家加入时调用此方法，使他开始工作
     * */
    public void playerJoin(Go4Player go4Player){//玩家进入服务器
        class PlayerJoin implements Runnable{
            Task task;
            int time = 0;
            PlayerDataCaseLock lock = null;
            void setTask(Task task){
                this.task = task;
            }
            @Override
            public void run() {
                //这个sendMessage在1.18又不是过时的
                go4Player.loadingMessage(setUp.lang.isLoading.replaceAll("<数>", String.valueOf(time)));
                time++;

                if (lock==null){
                    try {
                        lock = playerDataCase.getPlayerDataLock(go4Player);
                    }catch (Exception|Error e){
                        new DataGetLockError(logger,e,"在获得玩家"+ go4Player.getName()+"的锁时发生异常！").printStackTrace();
                    }
                }else {
                    try {
                        byte[] data = lock.loadData();//如果抛出异常则会下次再来
                        task.cancel();//得到数据后取消任务

                        //异步获得锁和数据之后在主线程加载到玩家上
                        PlayerDataCaseLock finalLock = lock;
                        taskManager.runSynchronization(() -> {
                            try {//防止接口出异常影响正常运行
                                playerDataSerialize.load(go4Player,data);
                            }catch (Exception|Error e){
                                new DataUnSerializeError(logger,e,"玩家"+ go4Player.getName()+"数据反序列化时发生错误。").printStackTrace();
                            }
                            //玩家数据加载完成
                            playerLoadRunMap.remove(go4Player);
                            playerLockMap.put(go4Player, finalLock);//完成这一条才算真的完成
                            //加载完成任务
                            Queue<Runnable> queue = playerLoadFinishedToRunMap.remove(go4Player);
                            if (queue!=null){
                                while (true){
                                    Runnable runnable = queue.poll();
                                    if (runnable==null){
                                        break;
                                    }
                                    try {//防止出异常影响下一条任务运行。
                                        runnable.run();
                                    }catch (Exception|Error e){
                                        new LoadFinishedToRunError(logger,e,"玩家数据加载完成任务执行时发生异常！").printStackTrace();
                                    }

                                }
                            }
                            //这个sendMessage在1.18又不是过时的
                            go4Player.loadingMessage(setUp.lang.loadingFinish);
                        });
                    }catch (Exception|Error e){
                        new DataLoadError(logger,e,"玩家"+ go4Player.getName()+"数据加载时发生错误。").printStackTrace();
                    }
                }
            }

        }
        PlayerJoin playerJoin = new PlayerJoin();
        Task task = taskManager.runCircularTask(setUp.LockDetectionInterval,playerJoin);
        playerLoadRunMap.put(go4Player,task);
        playerJoin.setTask(task);

    }



    /**
     * 返回玩家是否加载完成，在玩家执行任何操作时使用此方法判断玩家是否加载完成，如果没有加载完成应该阻止玩家进行任何操作。
     * */
    public boolean isLoaded(Go4Player go4Player){
        return playerLockMap.containsKey(go4Player);
    }


}
