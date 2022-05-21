package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * 血量
 * */
public class Health implements YamlDataSerializePart {
    private static final String Health = "Health",MaxHealth = "MaxHealth";
    @Override
    public String key() {
        return "Health";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        configuration.set(MaxHealth,player.getMaxHealth());
        configuration.set(Health,player.getHealth());
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (!configuration.contains(Health)) {
            return;
        }
        if (!configuration.contains(MaxHealth)){
            return;
        }
        double maxHealth = configuration.getDouble(MaxHealth);
        double health = configuration.getDouble(Health);
        if (health<1){
            health=1;
        }
        player.setMaxHealth(maxHealth);
        player.setHealth(health);
    }
}
