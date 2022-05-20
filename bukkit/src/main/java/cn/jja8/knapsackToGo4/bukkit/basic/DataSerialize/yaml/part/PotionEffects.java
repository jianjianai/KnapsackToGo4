package cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PotionEffects implements YamlDataSerializePart {
    private static final String PotionEffects = "PotionEffects";
    @Override
    public String key() {
        return "PotionEffects";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        Iterator<PotionEffect> effects = player.getActivePotionEffects().iterator();
        ArrayList<Map<String,Object>> ArrayList = new ArrayList<>();
        for(int i = 0;effects.hasNext();i++){
            ArrayList.add((effects.next()).serialize());
        }
        configuration.set(PotionEffects,ArrayList);
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        List<Map<?, ?>> List = configuration.getMapList(PotionEffects);
        PotionEffectType[] T = PotionEffectType.values();
        for (PotionEffectType potionEffectType : T) {
            if (potionEffectType != null) {
                player.removePotionEffect(potionEffectType);
            }
        }
        for (Map<?, ?> map : List) {
            player.addPotionEffect(new PotionEffect((Map<String, Object>) map));
        }
    }
}
