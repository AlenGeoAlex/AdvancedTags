package me.alen_alex.advancedtags;

import me.alen_alex.advancedtags.exceptions.UnknownDataException;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PluginDataManager {

    private final AdvancedTags plugin;
    private final HashMap<String, Tag> tagCache;
    private final HashMap<UUID, ATPlayer> playerLoaded;

    public PluginDataManager(AdvancedTags plugin) {
        this.plugin = plugin;
        tagCache = new HashMap<String,Tag>();
        playerLoaded = new HashMap<UUID,ATPlayer>();
    }

    public void insertTag(@NotNull String tagName, @NotNull Tag tagObject){
        if(containsTag(tagName)){
            if(plugin.getConfigurationHandler().getPluginConfig().isGlobalPriority())
            {
                if(tagObject.isGlobal()){
                    this.tagCache.remove(tagName);
                    this.tagCache.put(tagName,tagObject);
                }
            }else {
                if(!tagObject.isGlobal()) {
                    this.tagCache.remove(tagName);
                    this.tagCache.put(tagName,tagObject);
                }
            }
        }else
            this.tagCache.put(tagName,tagObject);
    }

    public boolean containsTag(@NotNull String tagName){
        return this.tagCache.containsKey(tagName);
    }

    public boolean containsTag(@NotNull Tag tagObj){
        return this.tagCache.containsValue(tagObj);
    }

    public Tag getTagFromCache(@NotNull String tagName){
        return this.tagCache.get(tagName);
    }

    public void insertTagAsBatch(@NotNull List<Tag> tags){
        tags.forEach((tag) -> this.insertTag(tag.getName(),tag));
    }

    public void addPlayer(@NotNull ATPlayer player){
        this.playerLoaded.put(player.getPlayerID(),player);
    }

    public boolean containsPlayer(@NotNull Player player){
        return this.containsPlayer(player.getUniqueId());
    }

    public boolean containsPlayer(@NotNull UUID playerID){
        return this.playerLoaded.containsKey(playerID);
    }

    public boolean containsPlayer(@NotNull ATPlayer player){
        return this.playerLoaded.containsValue(player);
    }

    public ATPlayer getPlayer(@NotNull UUID playerID){
        return this.playerLoaded.get(playerID);
    }

    public ATPlayer getPlayer(@NotNull Player player){
        return this.getPlayer(player.getUniqueId());
    }

    public void removePlayer(@NotNull Player player){
        this.removePlayer(player.getUniqueId());
    }

    public void removePlayer(@NotNull UUID playerID){
        this.playerLoaded.remove(playerID);
    }

    public boolean hasTag(@NotNull Player player,@NotNull String tagName) throws UnknownDataException {
        if(!this.tagCache.containsKey(tagName))
            throw new UnknownDataException("The tag "+tagName+" has not been loaded or does not exist");

        if(!this.playerLoaded.containsKey(player))
            throw new UnknownDataException("The player "+player.getName()+"/"+player.getUniqueId()+" has not been loaded into the plugin cache!");

        return this.playerLoaded.get(player.getUniqueId()).getPlayerUnlockedTags().contains(tagName);
    }

    public List<Tag> getAllTagsOnServer(){
        final List<Tag> currentTags = new ArrayList<Tag>();
        for(Map.Entry<String,Tag> entry : tagCache.entrySet()){
            System.out.println(entry.getValue().getName());
            currentTags.add(entry.getValue());
        }
        return currentTags;
    }


}
