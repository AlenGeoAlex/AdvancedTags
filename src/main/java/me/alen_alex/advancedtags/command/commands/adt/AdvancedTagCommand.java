package me.alen_alex.advancedtags.command.commands.adt;

import me.alen_alex.advancedtags.command.CommandFramework;
import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvancedTagCommand extends CommandFramework {

    public AdvancedTagCommand(@NotNull String name, @NotNull String permission, @NotNull CommandHandler handler, CommandWorkerImpl defaultCommand) {
        super(name, permission, handler);
        registerCommand();
        registerSubcommands(
                new AdvancedTagReload(handler)
        );
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        super.executeCommand(sender,commandLabel,args);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return executeTabCompletion(sender,command,alias,args);
    }
}
