package cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import cn.jja8.knapsackToGo4.all.work.PlayerDataSerialize;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitGo4Player;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error.ConfigLoadError;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error.NoBukkitGo4PlayerError;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error.PatrLoadError;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.error.PatrSaveError;
import cn.jja8.knapsackToGo4.bukkit.work.playerDataSerialize.yaml.part.*;
import cn.jja8.patronSaint.all.V2.file.YamlConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
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
        YamlDataSerializeSetUp c = YamlConfig.loadFromFile(new File(KnapsackToGo4.INSTANCE.getDataFolder(),"YamlDataSerializeSetUp.yml"),new YamlDataSerializeSetUp());
        if (c.AdvancementProgress) yamlDataSerializePartSet.add(new AdvancementProgress());
        if (c.EnderChest) yamlDataSerializePartSet.add(new EnderChest());
        if (c.Experience) yamlDataSerializePartSet.add(new Experience());
        if (c.FoodLevel) yamlDataSerializePartSet.add(new FoodLevel());
        if (c.Health) yamlDataSerializePartSet.add(new Health(c.MaxHealth));
        if (c.Inventory) yamlDataSerializePartSet.add(new Inventory());
        if (c.PotionEffects) yamlDataSerializePartSet.add(new PotionEffects());
        if (c.Location) yamlDataSerializePartSet.add(new Location());
    }

    @Override
    public byte[] save(Go4Player go4Player) {
        if (!(go4Player instanceof BukkitGo4Player)){
            throw new NoBukkitGo4PlayerError("无法保存玩家"+go4Player.getName()+"的数据，因为他不是一个BukkitGo4Player！而是"+go4Player.getClass().getName());
        }
        Player player = ((BukkitGo4Player)go4Player).getPlayer();
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
    public void load(Go4Player go4Player, byte[] bytes) throws IOException, InvalidConfigurationException {
        if (!(go4Player instanceof BukkitGo4Player)){
            throw new NoBukkitGo4PlayerError("无法加载玩家"+go4Player.getName()+"的数据，因为他不是一个BukkitGo4Player！而是"+go4Player.getClass().getName());
        }
        Player player = ((BukkitGo4Player)go4Player).getPlayer();
        if (bytes==null){
            return;
        }
        String ymlString = new String(bytes,StandardCharsets.UTF_8);
        StringReader stringReader = new StringReader(ymlString);
        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.load(stringReader);
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
