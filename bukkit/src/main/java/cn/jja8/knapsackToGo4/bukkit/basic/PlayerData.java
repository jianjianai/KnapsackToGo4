package cn.jja8.knapsackToGo4.bukkit.basic;


import cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.PlayerDataSupport;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 一个中介类，为了兼容多版本
 * 可在使用别的插件在load阶段给playerDataSupport赋值，用于兼容更多版本。
 * */
public class PlayerData {
    public static PlayerDataSupport playerDataSupport = null;
    public static void load() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (playerDataSupport==null){
            String v = Bukkit.getServer().getClass().getName().split("\\.")[3];
            Class<?> cl = Class.forName("cn.jja8.knapsackToGo4.bukkit.basic.playerDataSupport.file."+v);
            Constructor<?> cn = cl.getConstructor(File.class);
            playerDataSupport = (PlayerDataSupport) cn.newInstance();
        }
    }
}
