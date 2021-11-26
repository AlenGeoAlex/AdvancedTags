package me.alen_alex.advancedtags.hook.placeholder;

import me.alen_alex.advancedtags.hook.HookManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    private HookManager handler;
    private DataManager manager;

    public PlaceholderAPI(HookManager handler) {
        this.handler = handler;
        this.manager = new DataManager(handler);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "adt";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alen_Alex";
    }

    @Override
    public @NotNull String getVersion() {
        return this.handler.getPlugin().getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if(identifier.equalsIgnoreCase("tag"))
            return manager.getPlayerFormattedTag(player);

        if(identifier.equalsIgnoreCase("tagraw"))
            return manager.getPlayerRawTag(player);

        if(identifier.equalsIgnoreCase("count"))
            return manager.getPlayerTagCount(player);

        if(identifier.equalsIgnoreCase("locked_count"))
            return manager.getPlayerLockedTagCount(player);

        return null;
    }
}
