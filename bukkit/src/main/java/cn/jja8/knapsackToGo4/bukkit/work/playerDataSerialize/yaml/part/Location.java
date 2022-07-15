package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * 位置
 * */
public class Location implements YamlDataSerializePart {

    @Override
    public String key() {
        return "Location";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        configuration.set("Location",player.getLocation());
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        org.bukkit.Location location = configuration.getLocation("Location");
        if (location==null){
            return;
        }
        player.teleport(location);
    }
}
