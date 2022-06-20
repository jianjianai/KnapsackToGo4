package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
/**
 * 经验
 * */
public class Experience implements YamlDataSerializePart {
    private static final String Experience = "Experience";
    private static final String Level = "Level";
    private static final String Exp = "Exp";
    @Override
    public String key() {
        return "Experience";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        int experience = player.getTotalExperience();
        int level = player.getLevel();
        float exp = player.getExp();
        configuration.set(Experience,experience);
        configuration.set(Level,level);
        configuration.set(Exp,exp);

    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (configuration.contains(Experience)){
            player.setTotalExperience(configuration.getInt(Experience));
        }
        if (configuration.contains(Level)){
            player.setLevel(configuration.getInt(Level));
        }
        if (configuration.contains(Exp)){
            player.setExp((float) configuration.getDouble(Exp));
        }
    }
}
