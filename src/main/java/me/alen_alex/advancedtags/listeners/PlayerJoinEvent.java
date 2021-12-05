package me.alen_alex.advancedtags.listeners;

import com.pepedevs.corelib.utils.scheduler.SchedulerUtils;
import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public final class PlayerJoinEvent implements Listener {

    private final AdvancedTags plugin;

    public PlayerJoinEvent(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(org.bukkit.event.player.PlayerJoinEvent event){
        final UUID playerUUID = event.getPlayer().getUniqueId();
            plugin.getStorageHandler().getDatabaseImpl().doUserExist(playerUUID).thenAccept(exist -> {
                if(exist){
                   plugin.getStorageHandler().getDatabaseImpl().loadPlayer(playerUUID).thenAccept(player -> {

                       if(player == null){
                           if(plugin.getConfigurationHandler().getPluginConfig().isFailedToFetch())
                               SchedulerUtils.runTask(new Runnable() {
                                   @Override
                                   public void run() {
                                       event.getPlayer().kickPlayer(plugin.getConfigurationHandler().getPluginConfig().getFailedFetchKickMessage());
                                   }
                               },this.plugin);
                           return;
                       }
                       plugin.getPluginManager().addPlayer(player);
                   });
               }else {
                  SchedulerUtils.runTask(new Runnable() {
                      @Override
                      public void run() {
                          event.getPlayer().kickPlayer("Rejoin to register your data on the server");
                      }
                  },this.plugin);
               }
            });
    }

}
