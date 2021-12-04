package me.alen_alex.advancedtags.command.commands.tag;

import me.alen_alex.advancedtags.command.CommandFramework;
import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TagCommand extends CommandFramework {


    public TagCommand(@NotNull String name, @NotNull String permission, @NotNull CommandHandler handler, CommandWorkerImpl defaultCommand) {
        super(name, permission, handler,defaultCommand);
        registerCommand();
        registerSubcommands(
                new TagCommandShop(handler),
                new TagCommandPlayerTag(handler),
                new TagCommandSet(handler),
                new TagCommandClear(handler)
        );
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
