package me.alen_alex.advancedtags.commandold.advancedtag;

import me.alen_alex.advancedtags.commandold.CommandFramework;
import me.alen_alex.advancedtags.commandold.CommandHandler_;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AdvancedTagCommand extends CommandFramework {

    public AdvancedTagCommand(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler_ handler) {
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
