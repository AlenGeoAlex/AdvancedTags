package me.alen_alex.advancedtags.commandold.advancedtag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.commandold.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReloadCommand implements Subcommand {

    private AdvancedTags plugin;
    private final List<String> aliases = new ArrayList<String>();

    public ReloadCommand(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean doRunOnConsole() {
        return true;
    }

    @Override
    public boolean registerTabCompletion() {
        return true;
    }

    @Override
    public boolean isTabCompletion() {
        return true;
    }

    @Override
    public String getCommandPermission() {
        return "adt.reload";
    }

    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getCommandSyntax() {
        return "advancedtags reload";
    }

    @Override
    public String getSuggestionString() {
        return "/advancedtags reload";
    }

    @Override
    public String getCommandDescription() {
        return "Reload the command";
    }

    @Override
    public void runMethod(Player player, String[] args) {
        plugin.callReload();
    }

    @Override
    public void runMethod(CommandSender sender, String[] args) {
        plugin.callReload();
    }
}
