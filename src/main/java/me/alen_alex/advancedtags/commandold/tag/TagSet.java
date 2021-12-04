package me.alen_alex.advancedtags.commandold.tag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.commandold.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagSet implements Subcommand {

    private AdvancedTags plugin;
    private final List<String> aliases = new ArrayList<String>();

    public TagSet(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean doRunOnConsole() {
        return true;
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
        return "at.tag.set";
    }

    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getCommandSyntax() {
        return "tag set";
    }

    @Override
    public String getSuggestionString() {
        return "/tag set [tag-name]";
    }

    @Override
    public String getCommandDescription() {
        return "Set a particular tag";
    }

    @Override
    public void runMethod(Player player, String[] args) {

    }

    @Override
    public void runMethod(CommandSender sender, String[] args) {

    }
}
