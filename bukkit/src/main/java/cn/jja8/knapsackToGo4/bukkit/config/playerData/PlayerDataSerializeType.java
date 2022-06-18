package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.all.work.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerialize;

public enum PlayerDataSerializeType {
    Yaml;


    public PlayerDataSerialize getYamlDataSerialize() {
        switch (this){
            default:return null;
            case Yaml:return YamlDataSerialize.get();
        }
    }
}
