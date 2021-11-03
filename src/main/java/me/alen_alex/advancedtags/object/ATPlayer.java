package me.alen_alex.advancedtags.object;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ATPlayer {

    private final UUID playerID;
    private String playerName;
    private List<String> playerUnlockedTags;
    private Tag playerCurrentTag;
    private final Player player;

    public ATPlayer(UUID playerID) {
        this.playerID = playerID;
        this.player = Bukkit.getPlayer(this.playerID);
    }

    public ATPlayer(UUID playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.player = Bukkit.getPlayer(this.playerID);
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
}
