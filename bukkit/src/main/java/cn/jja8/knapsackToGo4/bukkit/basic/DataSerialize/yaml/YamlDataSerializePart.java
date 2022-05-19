package cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public interface YamlDataSerializePart {
    String key();
    void saveToYaml(Player player, ConfigurationSection configuration);
    void loadFormYaml(Player player,ConfigurationSection configuration);
}
