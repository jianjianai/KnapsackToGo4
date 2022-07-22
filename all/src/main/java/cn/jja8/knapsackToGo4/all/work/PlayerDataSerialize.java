package cn.jja8.knapsackToGo4.all.work;

/**
 * 玩家数据的序列化
 * */
public interface PlayerDataSerialize {
    /**
     * 将玩家的数据序列化
     * 如果调用此方法的的实现抛出异常会自动重试
     * */
    byte[] save(Go4Player go4Player) throws Throwable;
    /**
     * 将玩家的数据从序列化加载
     * 如果调用此方法的的实现抛出异常会自动重试
     * */
    void load(Go4Player go4Player, byte[] bytes) throws Throwable;
}
