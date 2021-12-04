package me.alen_alex.advancedtags.command.commands.tag;

import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagCommandClear implements CommandWorkerImpl {

    private final CommandHandler handler;

    public TagCommandClear(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        return;
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        if(!handler.getPlugin().getPluginManager().getPlayer(player).doPlayerHaveTag()){
            handler.getPlugin().getChatUtils().sendSimpleMessage(player,handler.getPlugin().getConfigurationHandler().getMessageConfiguration().getNoActiveTag());
            return;
        }

        handler.getPlugin().getPluginManager().clearPlayerTag(player);

    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "adt.command.clear";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "Clears the current player tag";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "tag clear";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "/tag clear";
    }

    @Override
    public boolean isConsoleCommand() {
        return false;
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }
}
