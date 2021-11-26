package me.alen_alex.advancedtags.hook.placeholder;

import me.alen_alex.advancedtags.hook.HookManager;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.entity.Player;

public class DataManager {

    private HookManager manager;

    public DataManager(HookManager manager) {
        this.manager = manager;
    }

    private static final String UNKNOWN_PLAYER = IridiumColorAPI.process("&cUnknown Player");

    public String getPlayerFormattedTag(Player player){
        final ATPlayer atPlayer = this.manager.getPlugin().getPluginManager().getPlayer(player);
        if(atPlayer == null)
            return "";
        if(!atPlayer.doPlayerHaveTag())
            return "";

        final String playerTag = atPlayer.getCurrentTagDisplay();
        return this.manager.getPlugin().getConfigurationHandler().getPluginConfig().getTagSpaceFormat().replaceAll("%tag%",playerTag);
    }

    public String getPlayerRawTag(Player player){
        final ATPlayer atPlayer = this.manager.getPlugin().getPluginManager().getPlayer(player);
        if(atPlayer == null)
            return "";
        if(!atPlayer.doPlayerHaveTag())
            return "";

        return atPlayer.getCurrentTagDisplay();
    }

    public String getPlayerTagCount(Player player){
        final ATPlayer atPlayer = this.manager.getPlugin().getPluginManager().getPlayer(player);
        if(atPlayer == null)
            return UNKNOWN_PLAYER;

        return String.valueOf(atPlayer.getPlayerUnlockedTags().size());
    }

    public String getPlayerLockedTagCount(Player player){
        final ATPlayer atPlayer = this.manager.getPlugin().getPluginManager().getPlayer(player);
        if(atPlayer == null)
            return UNKNOWN_PLAYER;

        return String.valueOf(atPlayer.getPlayerLockedTags().size());
    }


}
