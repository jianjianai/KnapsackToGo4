package cn.jja8.knapsackToGo4.sponge.work;

import cn.jja8.knapsackToGo4.all.work.Go4Player;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.entity.living.player.Player;

import java.util.UUID;

public class SpongeG4Player implements Go4Player {
    final Player player;

    SpongeG4Player(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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


    @Override
    public boolean equals(Object o) {
        if (o==null){
            return false;
        }
        if (!(o instanceof SpongeG4Player)) {
            return false;
        }
        return ((SpongeG4Player) o).player.uniqueId().equals(player.uniqueId());
    }

    @Override
    public int hashCode() {
        return player.uniqueId().hashCode();
    }
}
