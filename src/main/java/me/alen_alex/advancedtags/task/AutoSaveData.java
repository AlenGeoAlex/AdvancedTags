package me.alen_alex.advancedtags.task;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.object.ATPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveData extends BukkitRunnable {

    private AdvancedTags plugin;

    public AutoSaveData(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach((player) -> {
            if(plugin.getPluginManager().containsPlayer(player)){
                final ATPlayer atPlayer = plugin.getPluginManager().getPlayer(player);
                if(atPlayer == null)
                    return;

                plugin.getStorageHandler().getDatabaseImpl().savePlayerTag(atPlayer).thenAccept((saved) -> {
                    if(!saved)
                        plugin.getLogger().warning("Failed to save player data for user "+atPlayer.getPlayerName());
                });
            }
        });
    }
}
