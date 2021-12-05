package me.alen_alex.advancedtags.command.commands.adt;

import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdvancedTagDefault implements CommandWorkerImpl {

    private final CommandHandler handler;
    final List<TextComponent> components = new ArrayList<TextComponent>();


    public AdvancedTagDefault(CommandHandler handler) {
        this.handler = handler;
        prepPluginMessage();
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {

    }

    @Override
    public void executeCommand(Player player, String[] args) {

    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "";
    }

    @Override
    public boolean isConsoleCommand() {
        return true;
    }

    @Override
    public boolean registerTabCompleter() {
        return false;
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }

    private void prepPluginMessage(){

    }
}
