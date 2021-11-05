package me.alen_alex.advancedtags.object;

import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ATPlayer {

    private final UUID playerID;
    private String playerName;
    private String tagOnDatabase;
    private List<String> playerUnlockedTags;
    private Tag playerCurrentTag;
    private final Player player;
    private final AdvancedTags plugin;


    public ATPlayer(AdvancedTags plugin,UUID playerID, String tagOnDatabase,List<String> playerUnlockedTags) {
        this.playerID = playerID;
        this.tagOnDatabase = tagOnDatabase;
        this.plugin = plugin;
        this.playerUnlockedTags = playerUnlockedTags;
        this.player = Bukkit.getPlayer(this.playerID);
        processPlayer();
    }

    private void processPlayer(){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if(plugin.getPluginManager().containsTag(tagOnDatabase)){
                    playerCurrentTag = plugin.getPluginManager().getTagFromCache(tagOnDatabase);
                }else {
                    if(plugin.getConfigurationHandler().getPluginConfig().isRandomTagOnInvalid()){
                        boolean found = false;
                        for(String tagName : playerUnlockedTags){
                            if(plugin.getPluginManager().containsTag(tagName)){
                                found = true;
                                playerCurrentTag = plugin.getPluginManager().getTagFromCache(tagName);
                                break;
                            }
                        }
                        if(!found)
                            playerCurrentTag = null;
                        else plugin.getChatUtils().sendSimpleMessage(player,"//TODO Current tag not on server, switching to random ${tagName}");
                    }else playerCurrentTag = null;
                }

                if(playerCurrentTag == null){
                    //TODO Send no tag on the server message
                }
            }
        });
    }

    public Tag getPlayerCurrentTag() {
        return playerCurrentTag;
    }

    public void setPlayerCurrentTag(Tag playerCurrentTag) {
        this.playerCurrentTag = playerCurrentTag;
    }

    public String getCurrentTagDisplay(){
        if(playerCurrentTag != null)
            return this.playerCurrentTag.getName();
        else return null;
    }

    public boolean doPlayerHaveTag(){
        return this.playerCurrentTag != null;
    }

    public int getTillUnlockedTagCount(){
        return this.playerUnlockedTags.size();
    }

    public UUID getPlayerID() {
        return playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player getPlayer() {
        return player;
    }
}
