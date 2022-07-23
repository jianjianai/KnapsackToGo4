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

public class SaveAllPlayerData implements CommandImplement , CanSetUp {
    @Lang
    public String StartLoading = "共保存<i>个玩家的数据。";
    @Lang
    public String PlayerFinish = "<player>玩家数据保存完成。";
    @Lang
    public String Error = "<player>玩家数据保存失败，将在1秒后重试。";

    @Override
    public boolean command(CommandSender commandSender, String[] args) {
        KnapsackToGo4.knapsackToGo4.work.saveAllPlayerData(new Work.SavePlayerDataRet() {
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
                Bukkit.getScheduler().runTaskLaterAsynchronously(KnapsackToGo4.knapsackToGo4, () -> {
                    try {
                        KnapsackToGo4.knapsackToGo4.work.savePlayerData(player,this);
                    } catch (NoPlayerLockException e) {
                        e.printStackTrace();
                    }
                },20);
            }
        });
        return true;
    }
}
