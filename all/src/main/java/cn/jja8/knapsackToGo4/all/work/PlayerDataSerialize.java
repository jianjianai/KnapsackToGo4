package cn.jja8.knapsackToGo4.all.work;

/**
 * 玩家数据的序列化
 * */
public interface PlayerDataSerialize {
    /**
     * 将玩家的数据序列化
     * */
    byte[] save(Go4Player go4Player);
    /**
     * 将玩家的数据从序列化加载
     * */
    void load(Go4Player go4Player, byte[] bytes);
}
