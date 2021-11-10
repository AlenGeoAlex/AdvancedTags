package me.alen_alex.advancedtags.listeners;

import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public final class AsyncPlayerJoinEvent implements Listener {

    private final AdvancedTags plugin;

    public AsyncPlayerJoinEvent(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerJoinEvent(AsyncPlayerPreLoginEvent event){
        if(event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED)
            return;


        final UUID playerUUID = event.getUniqueId();
        plugin.getStorageHandler().getDatabaseImpl().doUserExist(playerUUID).thenAccept(exist -> {
            if(!exist) {
                plugin.getStorageHandler().getDatabaseImpl().registerUser(playerUUID).thenAccept(registered -> {
                    if(!registered){
                        if(plugin.getConfigurationHandler().getPluginConfig().isFailedToRegister()){
                            SchedulerUtils.runTask(new Runnable() {
                                @Override
                                public void run() {
                                    event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
                                    event.setKickMessage(plugin.getConfigurationHandler().getPluginConfig().getFailedRegisterKickMessage());
                                }
                            },plugin);

                        }
                    }
                });
            }
        });

    }

}
