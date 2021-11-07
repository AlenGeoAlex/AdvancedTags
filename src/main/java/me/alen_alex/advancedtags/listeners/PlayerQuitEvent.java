package me.alen_alex.advancedtags.listeners;

import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class PlayerQuitEvent implements Listener {

    private AdvancedTags plugin;

    public PlayerQuitEvent(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(org.bukkit.event.player.PlayerQuitEvent event){
        final UUID playerUUID = event.getPlayer().getUniqueId();

        if(plugin.getPluginManager().containsPlayer(playerUUID)){
            plugin.getStorageHandler().getDatabaseImpl().savePlayerTag(plugin.getPluginManager().getPlayer(playerUUID)).thenAccept(done -> {
                if(!done)
                    plugin.getLogger().warning("Unable to save player data for "+event.getPlayer().getName()+".");
            });

            plugin.getPluginManager().removePlayer(playerUUID);
        }
    }

}
