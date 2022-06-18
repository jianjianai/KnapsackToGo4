package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * 血量
 * */
public class Health implements YamlDataSerializePart {
    private static final String Health = "Health",MaxHealth = "MaxHealth";
    private final boolean offMaxHealth;
    public Health(boolean offMaxHealth) {
        this.offMaxHealth = offMaxHealth;
    }

    @Override
    public String key() {
        return "Health";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        if(offMaxHealth)configuration.set(MaxHealth,player.getMaxHealth());//虽然过时了，但是先用着吧
        configuration.set(Health,player.getHealth());
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (offMaxHealth&&configuration.contains(MaxHealth)){
            double maxHealth = configuration.getDouble(MaxHealth);
            player.setMaxHealth(maxHealth);
        }
        if (configuration.contains(Health)) {
            double health = configuration.getDouble(Health);
            if (health<1){
                health=1;
            }
            double max = player.getMaxHealth();
            if (health>max){
                health = max;
            }
            player.setHealth(health);
        }
    }
}
