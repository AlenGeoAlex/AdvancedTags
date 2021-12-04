package me.alen_alex.advancedtags.commandold.tag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.commandold.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TagShopMenu implements Subcommand {

    private AdvancedTags plugin;
    private final List<String> aliases = new ArrayList<String>();

    public TagShopMenu(AdvancedTags plugin) {
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
        return "at.gui.tagshop";
    }

    @Override
    public String getCommandName() {
        return "shop";
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public String getCommandSyntax() {
        return "tag shop";
    }

    @Override
    public String getSuggestionString() {
        return "/tag shop";
    }

    @Override
    public String getCommandDescription() {
        return "The tag shop for the plugin";
    }

    @Override
    public void runMethod(Player player, String[] args) {
        plugin.getGuiHandler().getTagShop().openMenu(player);
    }

    @Override
    public void runMethod(CommandSender sender, String[] args) {
        return;
    }
}
