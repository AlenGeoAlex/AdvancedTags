package me.alen_alex.advancedtags.command.advancedtag;

import me.alen_alex.advancedtags.command.CommandFramework;
import me.alen_alex.advancedtags.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AdvancedTagCommand extends CommandFramework {

    public AdvancedTagCommand(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler) {
        super(name, commandPermission, handler);
        registerCommand();
        registerSubcommands(new ReloadCommand(handler.getPlugin()));
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        executeCommand(sender,commandLabel,args);
        return true;
    }
}
