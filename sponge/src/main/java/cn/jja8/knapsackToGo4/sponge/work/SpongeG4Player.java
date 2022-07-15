package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class SpongeG4Player implements Go4Player {
    final Player player;

    private SpongeG4Player(Player player) {
        this.player = player;
    }

    @Override
    public void loadingMessage(String message) {
        player.sendActionBar(Component.newline().content(message));
    }

    @Override
    public String getName() {
        return player.name();
    }

    @Override
    public UUID getUUID() {
        return player.uniqueId();
    }
}
