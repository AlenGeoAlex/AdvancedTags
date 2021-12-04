package me.alen_alex.advancedtags.command.commands.tag;

import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagCommandPlayerTag implements CommandWorkerImpl {

    private final CommandHandler handler;

    public TagCommandPlayerTag(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        return;
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        handler.getPlugin().getGuiHandler().getPlayerTagsMenu().openMenu(player);
    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "adt.gui.tag";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "mytag";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "Open up a menu which shows the player tags";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "tag mytag";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "/tag";
    }

    @Override
    public boolean isConsoleCommand() {
        return false;
    }

    @Override
    public boolean registerTabCompleter() {
        return true;
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }
}
