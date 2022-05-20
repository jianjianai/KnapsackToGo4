package cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class FoodLevel implements YamlDataSerializePart {
    private static final String
            FoodLevel = "FoodLevel",
            Saturation = "Saturation";
    @Override
    public String key() {
        return "FoodLevel";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        configuration.set(FoodLevel,player.getFoodLevel());
        configuration.set(Saturation,(double)player.getSaturation());
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (!configuration.contains(FoodLevel)) {
            return;
        }
        if (!configuration.contains(Saturation)){
            return;
        }
        int foodLevel = configuration.getInt(FoodLevel);
        float saturation = (float) configuration.getDouble(Saturation);
        player.setFoodLevel(foodLevel);
        player.setSaturation(saturation);
    }
}
