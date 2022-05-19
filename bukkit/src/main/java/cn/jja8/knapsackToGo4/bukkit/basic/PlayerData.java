package cn.jja8.knapsackToGo4.bukkit.basic;


/**
 * 一个中介类，为了兼容多版本
 * 可在使用别的插件在load阶段给playerDataSupport赋值，用于兼容更多版本。
 * */
public class PlayerData {
    public static PlayerDataCase playerDataCase = null;
    public static PlayerDataSerialize playerDataSerialize = null;
}
