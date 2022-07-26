package cn.jja8.knapsackToGo4.bukkit.command.commands;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import cn.jja8.knapsackToGo4.all.work.Work;
import cn.jja8.knapsackToGo4.all.work.error.NoPlayerLockException;
import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.patronSaint.bukkit.v3.command.CanSetUp;
import cn.jja8.patronSaint.bukkit.v3.command.CommandImplement;
import cn.jja8.patronSaint.bukkit.v3.command.canSetUpLoadType.Lang;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class LoadAllPlayerData implements CommandImplement, CanSetUp {

    @Lang
    public String StartLoading = "共加载<i>个玩家的数据。";
    @Lang
    public String PlayerFinish = "<player>玩家数据加载完成。";
    @Lang
    public String Error = "<player>玩家数据加载失败，将在1秒后重试。";

    @Override
    public boolean command(CommandSender commandSender, String[] args) {
        KnapsackToGo4.INSTANCE.work.loadAllPlayerData(new Work.SavePlayerDataRet() {
            @Override
            public void numberOfAllPlayer(int i) {
                commandSender.sendMessage(StartLoading.replaceAll("<i>", String.valueOf(i)));
            }

            @Override
            public void finish(Go4Player player) {
                commandSender.sendMessage(PlayerFinish.replaceAll("<player>",player.getName()));
            }

            @Override
            public void error(Go4Player player, Throwable throwable) {
                commandSender.sendMessage(Error.replaceAll("<player>",player.getName()));
                Bukkit.getScheduler().runTaskLaterAsynchronously(KnapsackToGo4.INSTANCE, () -> {
                    try {
                        KnapsackToGo4.INSTANCE.work.loadPlayerData(player,this);
                    } catch (NoPlayerLockException e) {
                        e.printStackTrace();
                    }
                },20);
            }
        });
        return true;
    }
}
