package cn.jja8.knapsackToGo4.sponge;

import com.google.inject.Inject;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.LoadedGameEvent;
import org.spongepowered.api.event.lifecycle.RefreshGameEvent;
import org.spongepowered.api.event.lifecycle.StoppedGameEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

@Plugin("exampleplugin")
public class KnapsackToGo4 {
    @Inject
    public static Logger logger;

    @Inject
    public static PluginContainer pluginContainer;

    @Listener
    public void gameLoaded(LoadedGameEvent event){

    }

    @Listener
    public void refreshGame(RefreshGameEvent event){

    }

    @Listener
    public void serverStart(StoppedGameEvent event){

    }
}
