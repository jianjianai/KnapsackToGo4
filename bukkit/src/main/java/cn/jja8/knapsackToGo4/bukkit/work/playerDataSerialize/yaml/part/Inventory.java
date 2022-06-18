package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
/**
 * 物品栏
 * */
public class Inventory implements YamlDataSerializePart {
    private static final String
            ItemStack = "ItemStack",
            ItemStackLen = "Len";

    @Override
    public String key() {
        return "Inventory";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        ItemStack[] all = player.getInventory().getContents();
        configuration.set(ItemStackLen,all.length);
        ConfigurationSection itemStack = configuration.createSection(ItemStack);
        for (int i = 0; i < all.length; i++) {
            itemStack.set(String.valueOf(i),all[i]);
        }
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
        if (!configuration.contains(ItemStackLen)) {
            return;
        }
        int len = configuration.getInt(ItemStackLen);
        ConfigurationSection itemStack = configuration.getConfigurationSection(ItemStack);
        if (itemStack==null){
            return;
        }
        ItemStack[] all = new ItemStack[len];
        for (int i = 0; i < all.length; i++) {
            all[i] = itemStack.getItemStack(String.valueOf(i));
        }
        player.getInventory().setContents(all);
    }
}
