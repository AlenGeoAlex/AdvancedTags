package me.alen_alex.advancedtags.command.tag;

import me.alen_alex.advancedtags.command.CommandFramework;
import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.Subcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TagCommand extends CommandFramework {

    private final TagMenu tagMenu;

    public TagCommand(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler, Subcommand... subcommand) {
        super(name, commandPermission, handler, subcommand);
        tagMenu = new TagMenu(getHandler().getPlugin());
        registerCommand();
        registerSubcommand(new TagShopMenu(getHandler().getPlugin()));
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        executeCommand(commandSender,s,strings,tagMenu);
        return true;
    }
}
