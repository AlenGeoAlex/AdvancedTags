package me.alen_alex.advancedtags.listeners;

import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public class AsyncPlayerJoinEvent implements Listener {

    private AdvancedTags plugin;

    public AsyncPlayerJoinEvent(AdvancedTags plugin) {
        this.plugin = plugin;
        plugin.getPlugin().getServer().getPluginManager().registerEvents(this,this.plugin);
    }

    @EventHandler
    public void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event){
        if(event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED)
            return;

        final UUID playerUUID = event.getUniqueId();
        if(!plugin.getStorageHandler().getDatabaseImpl().doUserExist(playerUUID)){
            if(!plugin.getStorageHandler().getDatabaseImpl().registerUser(playerUUID)){
                if(plugin.getConfigurationHandler().getPluginConfig().isFailedToRegister()){
                    event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                    event.setKickMessage(plugin.getConfigurationHandler().getPluginConfig().getFailedRegisterKickMessage());
                }
            }
        }
    }

}
