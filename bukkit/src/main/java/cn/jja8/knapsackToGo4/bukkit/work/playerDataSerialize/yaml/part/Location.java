package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
        org.bukkit.Location location = player.getLocation();
        configuration.set("X",location.getX());
        configuration.set("Y",location.getY());
        configuration.set("Z",location.getZ());
        configuration.set("Pitch",location.getPitch());
        configuration.set("Yaw",location.getYaw());
        configuration.set("World",location.getWorld().getName());
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        String worldName = configuration.getString("World");
        if (worldName==null){
            return;
        }
        World world =Bukkit.getWorld(worldName);
        if (world==null){
            return;
        }
        org.bukkit.Location location = new org.bukkit.Location(
                world,
                configuration.getDouble("X"),
                configuration.getDouble("Y"),
                configuration.getDouble("Z"),
                (float) configuration.getDouble("Yaw"),
                (float) configuration.getDouble("Pitch")
        );
        player.teleport(location);
    }
}
