package me.alen_alex.advancedtags.listeners;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.object.ATPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerJoinEvent implements Listener {

    private AdvancedTags plugin;

    public PlayerJoinEvent(AdvancedTags plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this,this.plugin);
    }

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
        final UUID playerUUID = event.getPlayer().getUniqueId();

        if(plugin.getStorageHandler().getDatabaseImpl().doUserExist(playerUUID)){
            ATPlayer newPlayer = plugin.getStorageHandler().getDatabaseImpl().loadPlayer(playerUUID);
            if(newPlayer == null){
                if(plugin.getConfigurationHandler().getPluginConfig().isFailedToFetch())
                    event.getPlayer().kickPlayer(plugin.getConfigurationHandler().getPluginConfig().getFailedFetchKickMessage());
                return;
            }
            plugin.getPluginManager().addPlayer(newPlayer);
        }else event.getPlayer().kickPlayer("Rejoin to register your data on the server");

    }

}
