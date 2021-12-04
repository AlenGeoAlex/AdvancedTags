package me.alen_alex.advancedtags.command.commands.tag;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagCommandShop implements CommandWorkerImpl {

    private final CommandHandler handler;

    public TagCommandShop(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        return;
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        handler.getPlugin().getGuiHandler().getTagShop().openMenu(player);
    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "adt.gui.shop";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "shop";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "Opens up a GUI For Tag Shop";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "tag shop";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "/tag shop";
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
