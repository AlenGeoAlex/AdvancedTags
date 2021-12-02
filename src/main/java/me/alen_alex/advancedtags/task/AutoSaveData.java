package me.alen_alex.advancedtags.task;

import me.Abhigya.core.util.tps.TpsUtils;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.object.ATPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSaveData extends BukkitRunnable {

    private final AdvancedTags plugin;

    public AutoSaveData(AdvancedTags plugin) {
        this.plugin = plugin;
        plugin.getLogger().info("Auto saving has been enabled. Next data-saving at "+plugin.getConfigurationHandler().getPluginConfig().getAutoSaveMins()+" mins");
    }

    @Override
    public void run() {
        try {
            if(TpsUtils.getTicksPerSecond() < plugin.getConfigurationHandler().getPluginConfig().getAutoSaveMinTPS()){
                plugin.getLogger().info("Aborting save task. Current TPS: "+TpsUtils.getTicksPerSecond());
                return;
            }

            long start = System.currentTimeMillis();
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
            plugin.getLogger().info("Completed auto-save in "+(System.currentTimeMillis() - start)+" ms");
        }catch (Exception e){
            plugin.getLogger().info("Failed to start Auto-Save");
        }

    }
}
