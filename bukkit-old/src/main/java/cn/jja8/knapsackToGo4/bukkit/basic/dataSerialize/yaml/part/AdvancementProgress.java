package cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.part;

import cn.jja8.knapsackToGo4.bukkit.basic.dataSerialize.yaml.YamlDataSerializePart;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 进度
 * */
public class AdvancementProgress implements YamlDataSerializePart {
    private static final String
            Reach = "Reach",
            NotReached = "NotReached";
    @Override
    public String key() {
        return "AdvancementProgress";
    }

    @Override
    public void saveToYaml(Player player, ConfigurationSection configuration) {
        Iterator<Advancement> advancementList = Bukkit.advancementIterator();
        while (advancementList.hasNext()) {
            Advancement advancement = advancementList.next();
            String advancementName = advancement.getKey().toString();//成就的名字
            org.bukkit.advancement.AdvancementProgress advancementProgress = player.getAdvancementProgress(advancement);
            Collection<String> completeColl = advancementProgress.getAwardedCriteria();//已到达的全部条件
            ArrayList<String> completeList = new ArrayList<>(completeColl);
            Collection<String> criteriaColl = advancementProgress.getRemainingCriteria();//未达到的全部条件
            ArrayList<String> criteriaList = new ArrayList<>(criteriaColl);
            configuration.set(advancementName+"."+Reach,completeList);
            configuration.set(advancementName+"."+NotReached,criteriaList);
        }
    }

    @Override
    public void loadFormYaml(Player player, ConfigurationSection configuration) {
            Iterator<Advancement> advancementList = Bukkit.advancementIterator();
            while (advancementList.hasNext()) {
                Advancement advancement = advancementList.next();
                String advancementName = advancement.getKey().toString();//成就的名字
                org.bukkit.advancement.AdvancementProgress advancementProgress = player.getAdvancementProgress(advancement);
                List<String> complete = configuration.getStringList(advancementName+"."+Reach);
                List<String> noComplete = configuration.getStringList(advancementName+"."+NotReached);
                for(String a:complete){
                    advancementProgress.awardCriteria(a);
                }
                for(String a:noComplete){
                    advancementProgress.revokeCriteria(a);
                }
            }
    }
}
