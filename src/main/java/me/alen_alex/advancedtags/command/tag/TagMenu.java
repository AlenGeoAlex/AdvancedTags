package me.alen_alex.advancedtags.command.tag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.command.Subcommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagMenu implements Subcommand {

    private AdvancedTags plugin;

    public TagMenu(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean doRunOnConsole() {
        return false;
    }

    @Override
    public boolean registerTabCompletion() {
        return false;
    }

    @Override
    public boolean isTabCompletion() {
        return false;
    }

    @Override
    public String getCommandPermission() {
        return "at.gui.tag";
    }

    @Override
    public String getCommandName() {
        return "tag";
    }

    @Override
    public List<String> getAliases() {
        return new ArrayList<String>();
    }

    @Override
    public String getCommandSyntax() {
        return "tag";
    }

    @Override
    public String getSuggestionString() {
        return null;
    }

    @Override
    public String getCommandDescription() {
        return "Open The Tag Menu";
    }

    @Override
    public void runMethod(Player player, String[] args) {
        plugin.getGuiHandler().getMainMenu().openMenu(player);
    }
}
