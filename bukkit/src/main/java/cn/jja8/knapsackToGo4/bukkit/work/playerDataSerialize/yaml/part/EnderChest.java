package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * 末影箱
 * */
public class EnderChest implements YamlDataSerializePart {
    private static final String
            ItemStack = "ItemStack",
            ItemStackLen = "Len";
    @Override
    public String key() {
        return "EnderChest";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        org.bukkit.inventory.ItemStack[] all = player.getEnderChest().getContents();
        //为了安全，把物品复制一份。他给的数组是从新生成的，和玩家物品栏并不链接，但是里面的物品却是链接的。
        for (int i = 0; i < all.length; i++) {
            if (all[i]!=null) {
                all[i] = new ItemStack(all[i]);
            }
        }
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
        player.getEnderChest().setContents(all);
    }
}
