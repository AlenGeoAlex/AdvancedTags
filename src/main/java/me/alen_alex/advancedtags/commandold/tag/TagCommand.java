package me.alen_alex.advancedtags.commandold.tag;

import me.alen_alex.advancedtags.commandold.CommandFramework;
import me.alen_alex.advancedtags.commandold.CommandHandler_;
import me.alen_alex.advancedtags.commandold.Subcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TagCommand extends CommandFramework {

    private final TagMenu tagMenu;

    public TagCommand(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler_ handler, Subcommand... subcommand) {
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
