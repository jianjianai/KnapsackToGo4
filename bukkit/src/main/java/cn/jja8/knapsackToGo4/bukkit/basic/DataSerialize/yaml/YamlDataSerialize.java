package cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml;

import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.basic.DataSerialize.yaml.part.Inventory;
import cn.jja8.knapsackToGo4.bukkit.basic.PlayerDataSerialize;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class YamlDataSerialize implements PlayerDataSerialize {
    private final Set<YamlDataSerializePart> yamlDataSerializePartSet = new HashSet<>();
    public YamlDataSerialize() throws IOException {
        YamlDataSerializeSetUp c = YamlConfig.loadFromFile(new File(KnapsackToGo4.knapsackToGo4.getDataFolder(),"FileDataCaseSetUp.yml"),new YamlDataSerializeSetUp());
        if (c.Inventory){
            yamlDataSerializePartSet.add(new Inventory());
        }
    }

    @Override
    public byte[] save(Player player) {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        for (YamlDataSerializePart yamlDataSerializePart : yamlDataSerializePartSet) {
            try {
                String key = yamlDataSerializePart.key();
                ConfigurationSection part = yamlConfiguration.createSection(key);
                yamlDataSerializePart.saveToYaml(player,part);
            }catch (Exception|Error e){
                e.printStackTrace();
            }
        }
        return yamlConfiguration.saveToString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void load(Player player, byte[] bytes) {
        String ymlString = new String(bytes,StandardCharsets.UTF_8);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        try {
            yamlConfiguration.load(ymlString);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (YamlDataSerializePart yamlDataSerializePart : yamlDataSerializePartSet) {
            try {
                String key = yamlDataSerializePart.key();
                ConfigurationSection part = yamlConfiguration.getConfigurationSection(key);
                if (part==null){
                    continue;
                }
                yamlDataSerializePart.loadFormYaml(player,part);
            }catch (Exception|Error e){
                e.printStackTrace();
            }
        }
    }
}
