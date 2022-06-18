package cn.jja8.knapsackToGo4.bukkit.work;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class BukkitGo4Player implements Go4Player {
    private final Player player;
    public BukkitGo4Player(Player player) {
        this.player = player;
    }

    @Override
    public void loadingMessage(String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(message));
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BukkitGo4Player that = (BukkitGo4Player) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }
}
