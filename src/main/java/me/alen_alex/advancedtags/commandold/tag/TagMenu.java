package me.alen_alex.advancedtags.commandold.tag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.commandold.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagMenu implements Subcommand {

    private AdvancedTags plugin;
    private final List<String> aliases = new ArrayList<String>();

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
        return aliases;
    }

    @Override
    public String getCommandSyntax() {
        return "tag menu";
    }

    @Override
    public String getSuggestionString() {
        return "/tag menu";
    }

    @Override
    public String getCommandDescription() {
        return "Open The Tag Menu";
    }

    @Override
    public void runMethod(Player player, String[] args) {
        plugin.getGuiHandler().getMainMenu().openMenu(player);
    }

    @Override
    public void runMethod(CommandSender sender, String[] args) {
        return;
    }
}
