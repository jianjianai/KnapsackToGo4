package cn.jja8.knapsackToGo4.bukkit.command;

import cn.jja8.knapsackToGo4.bukkit.KnapsackToGo4;
import cn.jja8.knapsackToGo4.bukkit.command.commands.*;
import cn.jja8.patronSaint.bukkit.v3.command.Command;
import cn.jja8.patronSaint.bukkit.v3.command.CommandList;
import cn.jja8.patronSaint.bukkit.v3.command.CommandManger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandManager {
    public static void load(){
        File configFiles = new File(KnapsackToGo4.INSTANCE.getDataFolder(),"Command");
        List<String> aliases = new ArrayList<>();
        aliases.add("ktg4");
        aliases.add("go4");
        new CommandManger(
                new CommandList("knapsackToGo4")
                        .setAliases(aliases)
                        .setPower("knapsackToGo4.userAdmin")
                        .addCommand(new Command("SaveAllPlayerData").setPower("knapsackToGo4.admin.SaveAllPlayerData").setCommandImplement(new SaveAllPlayerData()))
                        .addCommand(new Command("SavePlayerData").setPower("knapsackToGo4.admin.SavePlayerData").setCommandImplement(new SavePlayerData()))
                        .addCommand(new Command("LoadAllPlayerData").setPower("knapsackToGo4.admin.LoadAllPlayerData").setCommandImplement(new LoadAllPlayerData()))
                        .addCommand(new Command("LoadPlayerData").setPower("knapsackToGo4.admin.LoadPlayerData").setCommandImplement(new LoadPlayerData()))
                        .addCommand(new Command("CancelError").setPower("knapsackToGo4.admin.CancelError").setCommandImplement(new CancelError()))
        ).load(new File(configFiles,"knapsackToGo4.yaml")).run(KnapsackToGo4.INSTANCE);

    }
}
