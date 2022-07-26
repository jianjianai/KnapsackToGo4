package cn.jja8.knapsackToGo4.bukkit.command.commands;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import cn.jja8.knapsackToGo4.all.work.Work;
import cn.jja8.knapsackToGo4.all.work.error.NoPlayerLockException;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.work.BukkitGo4Player;
import cn.jja8.patronSaint.bukkit.v3.command.CanSetUp;
import cn.jja8.patronSaint.bukkit.v3.command.CommandImplement;
import cn.jja8.patronSaint.bukkit.v3.command.canSetUpLoadType.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LoadPlayerData implements CommandImplement , CanSetUp {
    @Lang
    public String NeedPlayerName = "需要指定玩家名称。";
    @Lang
    public String PlayerNoOnline = "<player>玩家不在线。";
    @Lang
    public String PlayerError = "<player>玩家数据加载错误";
    @Lang
    public String PlayerFinish = "<player>玩家数据加载完成";

    @Override
    public boolean command(CommandSender commandSender, String[] args) {
        if (args.length<1){
            commandSender.sendMessage(NeedPlayerName);
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player==null){
            commandSender.sendMessage(PlayerNoOnline.replaceAll("<player>",args[0]));
            return true;
        }
        try {
            KnapsackToGo4.INSTANCE.work.loadPlayerData(new BukkitGo4Player(player), new Work.SavePlayerDataRet() {
                @Override
                public void finish(Go4Player player) {
                    commandSender.sendMessage(PlayerFinish.replaceAll("<player>",args[0]));
                }
                @Override
                public void error(Go4Player player, Throwable throwable) {
                    throwable.printStackTrace();
                    commandSender.sendMessage(PlayerError.replaceAll("<player>",args[0]));
                }
            });
        } catch (NoPlayerLockException e) {
            e.printStackTrace();
            commandSender.sendMessage(PlayerError.replaceAll("<player>",args[0]));
        }
        return true;
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        List<String> list = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            list.add(onlinePlayer.getName());
        }
        return list;
    }
}
