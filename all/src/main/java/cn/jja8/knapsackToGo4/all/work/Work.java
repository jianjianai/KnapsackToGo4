package cn.jja8.knapsackToGo4.all.work;

import cn.jja8.knapsackToGo4.all.work.error.*;

import java.util.*;
import java.util.function.BiConsumer;

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
        if (setUp.AutoSave>0){
            logger.info("自动保存功能启动，时间间隔："+setUp.AutoSave+"秒。");
            //自动保存
            taskManager.runCircularTask(1000 * setUp.AutoSave, () -> saveAllPlayerData(new SavePlayerDataRet() {
                int numberOfAllPlayer = 0;
                int i = 0;
                long time = 0;
                @Override
                public void numberOfAllPlayer(int i) {
                    if (numberOfAllPlayer==0){
                        numberOfAllPlayer = i;
                    }
                    logger.info("自动保存开始，共"+i+"个玩家的数据。");
                }

                @Override
                public void finish(Go4Player player) {
                    i++;
                    if (time<System.currentTimeMillis()){
                        logger.info("自动保存:"+i+"/"+numberOfAllPlayer);
                        time = System.currentTimeMillis()+1000;
                    }
                    if (i==numberOfAllPlayer){
                        logger.info("自动保存:所有玩家数据全部保存完成。");
                    }
                }


                final Map<Go4Player,Integer> errmap = new HashMap<>();//记录玩家数据保存错误
                @Override
                public void error(Go4Player player, Throwable throwable) {
                    Integer errtime = errmap.get(player);
                    if (errtime==null){
                        errmap.put(player,1);
                        errtime = 1;
                    }else {
                        errmap.put(player,++errtime);
                    }
                    if (errtime>3){
                        logger.severe("自动保存:玩家"+player+"的数据保存失败，已经重试了3次了，保存任务取消！下方是详细错误原因。");
                        throwable.printStackTrace();
                    }else {
                        logger.severe("自动保存:玩家"+player+"的数据保存失败，将在10秒后自动重试！下方是详细错误原因。");
                        throwable.printStackTrace();
                        taskManager.runAsynchronous(() -> {
                            try {
                                savePlayerData(player,this);
                            } catch (NoPlayerLockException e) {
                                e.printStackTrace();
                            }
                        },10000);
                    }
                }
            }));
        }else {
            logger.info("自动保存功能关闭。只会在玩家退出服务器时和服务器关闭时保存玩家数据，如果服务器崩溃会造成数据丢失！");
        }
    }

//内部方法和对象--------------------------------------------------------------------------------------------------------------------

    //玩家和锁
    private final Map<Go4Player,PlayerLock> playerDataCaseLockMap = new HashMap<>();

    public class PlayerLock{
        private final Go4Player go4Player;
        private final PlayerDataCaseLock playerDataCaseLock;
        public PlayerLock(Go4Player go4Player, PlayerDataCaseLock playerDataCaseLock) {
            this.go4Player = go4Player;
            this.playerDataCaseLock = playerDataCaseLock;
            playerDataCaseLockMap.put(go4Player,this);
        }

        /**
         * 解锁,会柱塞，可在异步调用
         * 只要异常就代表没有解锁成功
         * */
        public synchronized void unlock() throws DataUnlockException{
            try {
                playerDataCaseLock.unlock();
                playerDataCaseLockMap.remove(go4Player);
            } catch (Throwable e) {
                throw new DataUnlockException(logger,"解锁数据错误！");
            }
        }


        /**
         * 更新玩家数据,会柱塞，可在异步调用
         * */
        private synchronized void update(byte[] data) throws DataUpdateException {
            try {
                playerDataCaseLock.saveData(data);
            }catch (Throwable e){
                throw new DataUpdateException(logger,e,"数据更新错误！");
            }

        }

        /**
         * 查询玩家数据,会柱塞，可在异步调用
         * */
        private synchronized byte[] select() throws DataSelectException{
            try {
                return playerDataCaseLock.loadData();
            } catch (Throwable e) {
                throw new DataSelectException(logger,e,"数据查询错误！");
            }
        }
    }

    /**
     * 序列化玩家的数据，需要在主线程调用
     * */
    private byte[] serialize(Go4Player go4Player) throws DataSerializeException {
        try {
            return playerDataSerialize.save(go4Player);
        }catch (Throwable e){
            throw new DataSerializeException(logger,e,"玩家"+ go4Player.getName()+"数据序列化错误！");
        }
    }

    /**
     * 反序列化玩家数据到玩家对象，需要在主线程调用
     * */
    private void deserialize(Go4Player go4Player,byte[] data) throws DataDeserializeException{
        try {
            playerDataSerialize.load(go4Player,data);
        } catch (Throwable e) {
            throw new DataDeserializeException(logger,e,"玩家"+ go4Player.getName()+"数据序反列化错误！");
        }
    }

    /**
     * 尝试获得锁,会柱塞，可在异步调用
     * null代表没有获取到锁
     * */
    private PlayerLock lock(Go4Player go4Player){
        PlayerLock playerLock = playerDataCaseLockMap.get(go4Player);
        if (playerLock!=null){
            return playerLock;
        }
        try {
            PlayerDataCaseLock playerDataCaseLock = playerDataCase.getPlayerDataLock(go4Player);
            if (playerDataCaseLock==null){
                return null;
            }
            return new PlayerLock(go4Player,playerDataCaseLock);
        } catch (Throwable e) {
            new DataLockException(logger,"获取玩家"+go4Player.getName()+"的锁时发生异常！").printStackTrace();
            return null;
        }
    }



    /**
     * 保存某个玩家的数据,此方法内部用于玩家退出时保存
     * 此方法会创建多个异步任务和多个同步任务，方法执行完成后玩家的数据实际还没有开始保存，而是已经把任务创建完成了。
     * @param ret 用于接收返回参数，可以是null
     * */
    private void savePlayerData(Go4Player go4Player, PlayerLock playerLock, UpdatePlayerDataRet ret){
        if (ret==null){
            ret=UpdatePlayerDataRet.NULL;
        }

        try {ret.numberOfAllPlayer(1);}catch (Error|Exception throwable){throwable.printStackTrace();}//传保存数量

        UpdatePlayerDataRet finalRet = ret;
        taskManager.runSynchronization(() -> {//同步序列化
            try {
                byte[] data = serialize(go4Player);


                //异步保存
                taskManager.runAsynchronous(() -> {
                    try {
                        if (playerLock!=null) {
                            playerLock.update(data);
                        }
                    }catch (Throwable e){
                        finalRet.error(go4Player,playerLock,e);
                        return;
                    }
                    finalRet.finish(go4Player,playerLock);
                });



            }catch (DataSerializeException e){
                finalRet.error(go4Player,playerLock,e);
            }
        });
    }

    /**
     * 加载某个玩家的数据，此方法内部用于玩家进入时加载
     * 此方法会创建多个异步任务和多个同步任务，方法执行完成后玩家的数据实际还没有开始加载，而是已经把任务创建完成了。
     * @param ret 用于接收返回参数，可以是null
     * */
    private void loadPlayerData(Go4Player go4Player, PlayerLock playerLock, UpdatePlayerDataRet ret){
        if (ret==null){
            ret=UpdatePlayerDataRet.NULL;
        }
        //异步加载
        UpdatePlayerDataRet finalRet = ret;
        taskManager.runAsynchronous(() -> {
            try {
                byte[] bytes = playerLock.select();
                if (bytes==null){
                    finalRet.finish(go4Player,playerLock);
                    return;
                }
                //同步反序列化
                taskManager.runSynchronization(() -> {
                    try {
                        deserialize(go4Player,bytes);
                        finalRet.finish(go4Player,playerLock);
                    } catch (DataDeserializeException e) {
                        finalRet.error(go4Player,playerLock,e);
                    }
                });
            } catch (DataSelectException e) {
                finalRet.error(go4Player,playerLock,e);
            }
        });
    }


    /**
     * 用于接收保存玩家数据方法的返回值的接口
     * */
    private interface UpdatePlayerDataRet {
        UpdatePlayerDataRet NULL = new UpdatePlayerDataRet() {
            @Override
            public void finish(Go4Player player, PlayerLock playerLock) {}
            @Override
            public void error(Go4Player player, PlayerLock playerLock, Throwable throwable) {throwable.printStackTrace();}
        };
        /**
         * 返回所有会保存的玩家数量
         * 此方法总是在第一个调用
         * */
        default void numberOfAllPlayer(int i){}
        /**
         * 当这个玩家数据保存完成时返回保存玩数据的玩家
         * */
        void finish(Go4Player player,PlayerLock playerLock);
        /**
         * 当这个玩家数据保存错误时返回异常
         * */
        void error(Go4Player player,PlayerLock playerLock,Throwable throwable);
    }

//下面是接口，就是可以用也可以不用------------------------------------------------------------------------------------------------

    /**
     * 保存所有玩家的数据，此方法内部用于自动保存。
     * 此方法会创建多个异步任务和多个同步任务，方法执行完成后玩家的数据实际还没有开始保存，而是已经把任务创建完成了。
     * @param ret 用于接收返回参数，可以是null
     * */
    public void saveAllPlayerData(SavePlayerDataRet ret){
        if (ret==null){
            ret = SavePlayerDataRet.NULL;
        }
        try {
            ret.numberOfAllPlayer(playerStatusMap.size());
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        SavePlayerDataRet finalRet = ret;
        playerDataCaseLockMap.forEach((go4Player, playerLock) -> savePlayerData(go4Player,playerLock, new UpdatePlayerDataRet() {
            @Override
            public void finish(Go4Player player,PlayerLock playerLock) {
                finalRet.finish(player);
            }
            @Override
            public void error(Go4Player player,PlayerLock playerLock, Throwable throwable) {
                finalRet.error(player,throwable);
            }
        }));
    }

    /**
     * 保存某个玩家的数据,此方法内部用于玩家退出时保存
     * 此方法会创建多个异步任务和多个同步任务，方法执行完成后玩家的数据实际还没有开始保存，而是已经把任务创建完成了。
     * @param ret 用于接收返回参数，可以是null
     * */
    public void savePlayerData(Go4Player go4Player, SavePlayerDataRet ret) throws NoPlayerLockException {
        PlayerLock playerLock = playerDataCaseLockMap.get(go4Player);
        if (playerLock==null){
            throw new NoPlayerLockException(logger,go4Player.getName()+"玩家没有锁，无法保存数据！");
        }
        if (ret==null){
            ret = SavePlayerDataRet.NULL;
        }
        SavePlayerDataRet finalRet = ret;
        savePlayerData(go4Player, playerLock, new UpdatePlayerDataRet() {
            @Override
            public void numberOfAllPlayer(int i) {
                finalRet.numberOfAllPlayer(i);
            }
            @Override
            public void finish(Go4Player player, PlayerLock playerLock) {
                finalRet.finish(player);
            }
            @Override
            public void error(Go4Player player, PlayerLock playerLock, Throwable throwable) {
                finalRet.error(player,throwable);
            }
        });
    }



    /**
     * 用于接收保存玩家数据方法的返回值的接口
     * */
    public interface SavePlayerDataRet {
        SavePlayerDataRet NULL = new SavePlayerDataRet() {
            @Override
            public void finish(Go4Player player) {}
            @Override
            public void error(Go4Player player, Throwable throwable) {throwable.printStackTrace();}
        };
        /**
         * 返回所有会保存的玩家数量
         * 此方法总是在第一个调用
         * */
        default void numberOfAllPlayer(int i){}
        /**
         * 当这个玩家数据保存完成时返回保存玩数据的玩家
         * */
        void finish(Go4Player player);
        /**
         * 当这个玩家数据保存错误时返回异常
         * */
        void error(Go4Player player,Throwable throwable);
    }


//运行内部对象--------------------------------------------------------------------------------------------------------------

    //玩家和信息
    private final Map<Go4Player, PlayerStatus> playerStatusMap = new HashMap<>();

    /**
     * 玩家状态类，用来保证线程安全
     * 直接的方法传来的同一个player可能是不同对象，经过map找到PlayerStatus后变成同一对象。之后的方法就可以 synchronized(go4Player)了
     * */
    private class PlayerStatus{
        PlayerLock playerLock;
        Go4Player go4Player;
        Task task = null;
        long time = 0;
        boolean Loaded = false;//玩家已经加载完成
        boolean playerQuit = false; //表示玩家已经退出
        boolean loading = false;//表示正在加载
        public PlayerStatus(Go4Player go4Player) {
            this.go4Player = go4Player;
        }

        private void playerJoin(){
            task = taskManager.runCircularTask(setUp.LockDetectionInterval, () -> {
                go4Player.loadingMessage(setUp.lang.isLoading.replaceAll("<数>", String.valueOf(time)));
                time++;

                    playerLock =  lock(go4Player);
                    if (playerLock!=null){
                        task.cancel();
                    }else {
                        return;
                    }
                    synchronized (PlayerStatus.this){
                        loading =true;
                        if (playerQuit){
                            unlock();
                        }else {
                            loadPlayerData(go4Player, playerLock, new UpdatePlayerDataRet() {
                                @Override
                                public void finish(Go4Player player, PlayerLock playerLock) {
                                    Loaded = true;
                                    go4Player.loadingMessage(setUp.lang.loadingFinish);
                                }

                                @Override
                                public void error(Go4Player player, PlayerLock playerLock, Throwable throwable) {
                                    go4Player.loadingMessage(setUp.lang.isLoadingError.replaceAll("<数>", String.valueOf(time)));
                                    time++;
                                    logger.severe("进入时加载:玩家"+player+"的数据加载失败，将在1秒后自动重试！下方是详细错误原因。");
                                    throwable.printStackTrace();
                                    taskManager.runAsynchronous(() -> loadPlayerData(player,playerLock,this),1000);
                                }
                            });
                        }
                    }
            });
        }

        private void playerQuit(){
            taskManager.runAsynchronous(new Runnable() {
                @Override
                public void run() {
                    if (task!=null){
                        task.cancel();
                    }
                    synchronized (PlayerStatus.this){
                        playerQuit = true;
                        if (!loading){
                            return;
                        }
                        if (!Loaded){//等待加载完成了保存
                            taskManager.runAsynchronous(this,50);
                            return;
                        }
                        savePlayerData(go4Player,playerLock, new UpdatePlayerDataRet() {
                            @Override
                            public void finish(Go4Player player,PlayerLock playerLock) {
                                unlock();
                            }
                            @Override
                            public void error(Go4Player player,PlayerLock playerLock, Throwable throwable) {
                                logger.severe("退出时保存:玩家"+player+"的数据保存失败，将在1秒后自动重试！下方是详细错误原因。");
                                throwable.printStackTrace();
                                taskManager.runAsynchronous(() -> savePlayerData(player,playerLock,this),1000);
                            }
                        });
                    }
                }
            });
        }

        private void unlock(){
            taskManager.runAsynchronous(() -> {
                if (playerLock!=null){
                    for (int i = 0; i < 10; i++) {
                        try {
                            playerLock.unlock();
                            return;
                        } catch (Exception | Error e) {
                            new DataSaveError(logger, e, "在为"+ go4Player.getName()+"解锁时发生异常！"+(i==0?"":"重试第"+i+"次")).printStackTrace();
                            try {Thread.sleep(1000);} catch (InterruptedException ignored) {}
                        }
                    }
                    throw new DataSaveError(logger, "在为"+ go4Player.getName()+"解锁时发生异常！重试10次全部失败。");
                }
            });
        }

    }



//下面是必须在特定时间调用的特定方法---------------------------------------------------------------------------------------------


    /**
     * 在玩家执行任何操作时使用此方法判断玩家是否加载完成，如果没有加载完成应该阻止玩家进行任何操作。
     * 返回玩家是否加载完成。
     * */
    public boolean isLoaded(Go4Player go4Player){
        PlayerStatus playerStatus = playerStatusMap.get(go4Player);
        if (playerStatus==null){
            return false;
        }
        return playerStatus.Loaded&&(!playerStatus.playerQuit);//玩家加载完成，并且没有退出
    }


    /**
     * 关闭是调用此方法
     * */
    public void close() {
        playerStatusMap.forEach((go4Player, playerStatus) -> playerStatus.playerQuit());
    }

    /**
     * 在玩家推出时调用此方法，使他开始工作
     * */
    public void playerQuit(Go4Player go4Player){//玩家离开服务器
        PlayerStatus playerStatus = playerStatusMap.remove(go4Player);
        if (playerStatus!=null){
            playerStatus.playerQuit();
        }
    }
    /**
     * 在玩家加入时调用此方法，使他开始工作
     * */
    public void playerJoin(Go4Player go4Player){//玩家进入服务器
        PlayerStatus playerStatus =  playerStatusMap.get(go4Player);
        if (playerStatus==null){
            playerStatus = new PlayerStatus(go4Player);
            playerStatusMap.put(go4Player,playerStatus);
        }
        playerStatus.playerJoin();
    }

}
