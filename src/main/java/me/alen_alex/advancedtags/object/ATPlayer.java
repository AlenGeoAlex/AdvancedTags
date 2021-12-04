package me.alen_alex.advancedtags.object;

import me.alen_alex.advancedtags.AdvancedTags;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ATPlayer  {

    private final UUID playerID;
    private final String playerName;
    private String tagOnDatabase;
    private final List<String> playerUnlockedTags;
    private Tag playerCurrentTag;
    private final Player player;
    private final AdvancedTags plugin;


    public ATPlayer(AdvancedTags plugin,UUID playerID, String tagOnDatabase,List<String> playerUnlockedTags) {
        this.playerID = playerID;
        this.tagOnDatabase = tagOnDatabase;
        this.plugin = plugin;
        this.playerUnlockedTags = playerUnlockedTags;
        this.player = Bukkit.getPlayer(this.playerID);
        this.playerName = player.getName();
        processPlayer();
    }

    private void processPlayer(){
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if(StringUtils.isNotBlank(tagOnDatabase)) {
                    if (plugin.getPluginManager().containsTag(tagOnDatabase)) {
                        playerCurrentTag = plugin.getPluginManager().getTagFromCache(tagOnDatabase);
                    } else {
                        if (plugin.getConfigurationHandler().getPluginConfig().isRandomTagOnInvalid()) {
                            boolean found = false;
                            for (String tagName : playerUnlockedTags) {
                                if (plugin.getPluginManager().containsTag(tagName)) {
                                    found = true;
                                    playerCurrentTag = plugin.getPluginManager().getTagFromCache(tagName);
                                    break;
                                }
                            }

                            if (!found) {
                                playerCurrentTag = null;
                            }
                            else {
                                plugin.getChatUtils().sendSimpleMessage(player, plugin.getConfigurationHandler().getMessageConfiguration().getSelectedRandomTag(tagOnDatabase, playerCurrentTag.getName()));
                            }
                        } else playerCurrentTag = null;

                        if(playerCurrentTag == null){
                            plugin.getChatUtils().sendSimpleMessage(player,plugin.getConfigurationHandler().getMessageConfiguration().getRemovedTagNoTag(tagOnDatabase));
                        }
                    }

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
        if(playerCurrentTag != null) {
            if(this.getPlayerCurrentTag().isDynamicTag())
                return PlaceholderAPI.setPlaceholders(this.player,this.playerCurrentTag.getDisplayTag());
            else return this.playerCurrentTag.getDisplayTag();
        } else return null;
    }

    public boolean doPlayerHaveTag(){
        return this.playerCurrentTag != null;
    }

    public int getTillUnlockedTagCount(){
        return this.playerUnlockedTags.size();
    }

    public int getRemainingToUnlock(){
        return this.plugin.getPluginManager().getAllTagsOnServer().size() - getTillUnlockedTagCount();
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

    public String getTagOnDatabase() {
        return tagOnDatabase;
    }

    public List<String> getPlayerUnlockedTagNames() {
        return playerUnlockedTags;
    }

    public List<Tag> getPlayerUnlockedTags(){
        List<Tag> playerUnlocked = new ArrayList<Tag>();

        this.playerUnlockedTags.forEach((stringTag) -> {
            if(plugin.getPluginManager().containsTag(stringTag))
                playerUnlocked.add(plugin.getPluginManager().getTagFromCache(stringTag));
        });

        return playerUnlocked;
    }

    public void addToUnlockedTag(Tag tag){
        playerUnlockedTags.add(tag.getName());
    }

    public List<String> getPlayerLockedTagName(){
        List<String> playerLocked = new ArrayList<String>();

        plugin.getPluginManager().getAllTagsOnServer().forEach((tag) -> {
            if(!this.playerUnlockedTags.contains(tag.getName()))
                playerLocked.add(tag.getName());
        });
        return playerLocked;
    }

    public List<Tag> getPlayerLockedTags(){
        List<Tag> playerLocked = new ArrayList<Tag>();


        plugin.getPluginManager().getAllTagsOnServer().forEach((tag) -> {
            if(!this.playerUnlockedTags.contains(tag.getName())) {
                playerLocked.add(tag);
            }
        });
        return playerLocked;
    }

    public void setPlayerTag(Tag tag){
        playerCurrentTag = tag;
        tagOnDatabase = tag.getName();
        if(plugin.getStorageHandler().getDatabaseImpl().setCurrentTag(tag))
            plugin.getLogger().severe("Unable to update "+getPlayerName()+"'s tag on database! Tag will be updated here, but not on DB");
    }

    public void clearPlayerTag(){
        playerCurrentTag = null;
        tagOnDatabase = null;
        if(plugin.getStorageHandler().getDatabaseImpl().setCurrentTag(null))
            plugin.getLogger().severe("Unable to update "+getPlayerName()+"'s tag on database! Tag will be updated here, but not on DB");
    }

    @Override
    public String toString() {
        return this.getPlayerName()+" occupied "+this.getCurrentTagDisplay();
    }

}
