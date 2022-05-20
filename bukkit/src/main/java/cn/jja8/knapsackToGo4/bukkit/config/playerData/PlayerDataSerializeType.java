package cn.jja8.knapsackToGo4.bukkit.config.playerData;

import cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.YamlDataSerialize;

public enum PlayerDataSerializeType {
    Yaml(YamlDataSerialize.get());

    private final YamlDataSerialize yamlDataSerialize;
    PlayerDataSerializeType(YamlDataSerialize yamlDataSerialize) {
        this.yamlDataSerialize = yamlDataSerialize;
    }
    public YamlDataSerialize getYamlDataSerialize() {
        return yamlDataSerialize;
    }
}
