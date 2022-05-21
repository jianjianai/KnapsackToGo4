package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.YamlDataSerialize;

public enum PlayerDataSerializeType {
    Yaml;


    public PlayerDataSerialize getYamlDataSerialize() {
        switch (this){
            default:return null;
            case Yaml:return YamlDataSerialize.get();
        }
    }
}
