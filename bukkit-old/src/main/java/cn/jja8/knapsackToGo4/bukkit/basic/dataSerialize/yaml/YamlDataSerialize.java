package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml;

import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.error.PatrLoadError;
import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.error.PatrSaveError;
import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.part.*;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.bukkit.error.ConfigLoadError;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class YamlDataSerialize implements PlayerDataSerialize {
    static public YamlDataSerialize yamlDataSerialize = null;
    public static YamlDataSerialize get() {
        if (yamlDataSerialize==null){
            try {
                yamlDataSerialize = new YamlDataSerialize();
            } catch (IOException e) {
                throw new ConfigLoadError(e,"配置文件YamlDataSerializeSetUp.yml加载错误");
            }
        }
        return yamlDataSerialize;
    }


    public final Set<YamlDataSerializePart> yamlDataSerializePartSet = new HashSet<>();
    private YamlDataSerialize() throws IOException {
        YamlDataSerializeSetUp c = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"YamlDataSerializeSetUp.yml"),new YamlDataSerializeSetUp());
        if (c.AdvancementProgress) yamlDataSerializePartSet.add(new AdvancementProgress());
        if (c.EnderChest) yamlDataSerializePartSet.add(new EnderChest());
        if (c.Experience) yamlDataSerializePartSet.add(new Experience());
        if (c.FoodLevel) yamlDataSerializePartSet.add(new FoodLevel());
        if (c.Health) yamlDataSerializePartSet.add(new Health(c.MaxHealth));
        if (c.Inventory) yamlDataSerializePartSet.add(new Inventory());
        if (c.PotionEffects) yamlDataSerializePartSet.add(new PotionEffects());
    }

    @Override
    public byte[] save(Player player) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        for (YamlDataSerializePart yamlDataSerializePart : yamlDataSerializePartSet) {
            String key = null;
            try {
                key = yamlDataSerializePart.key();
                ConfigurationSection part = yamlConfiguration.createSection(key);
                yamlDataSerializePart.saveToYaml(player,part);
            }catch (Exception|Error e){
                new PatrSaveError(e,"在保存玩家"+player.getName()+"的"+key+"部分数据时发生异常，已经跳过！").printStackTrace();
            }
        }
        return yamlConfiguration.saveToString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void load(Player player, byte[] bytes) {
        if (bytes==null){
            return;
        }
        String ymlString = new String(bytes,StandardCharsets.UTF_8);
        StringReader stringReader = new StringReader(ymlString);
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(stringReader);
        for (YamlDataSerializePart yamlDataSerializePart : yamlDataSerializePartSet) {
            String key = null;
            try {
                key = yamlDataSerializePart.key();
                ConfigurationSection part = yamlConfiguration.getConfigurationSection(key);
                if (part==null){
                    continue;
                }
                yamlDataSerializePart.loadFormYaml(player,part);
            }catch (Exception|Error e){
                new PatrLoadError(e,"在保加载家"+player.getName()+"的"+key+"部分数据时发生异常，已经跳过！").printStackTrace();
            }
        }
        stringReader.close();
    }
}
