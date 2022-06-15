package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
/**
 * 经验
 * */
public class Experience implements YamlDataSerializePart {
    private static final String Experience = "Experience";
    @Override
    public String key() {
        return "Experience";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        int exp = player.getTotalExperience();
        configuration.set(Experience,exp);
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (!configuration.contains(Experience)){
            return;
        }
        int exp = configuration.getInt(Experience);
        player.setTotalExperience(exp);
    }
}
