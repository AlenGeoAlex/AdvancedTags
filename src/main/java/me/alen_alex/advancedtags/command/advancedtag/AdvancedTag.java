package me.alen_alex.advancedtags.command.advancedtag;

import me.alen_alex.advancedtags.command.CommandFramework;
import me.alen_alex.advancedtags.command.CommandHandler;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdvancedTag extends CommandFramework {


    public AdvancedTag(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler,String... alias) {
        super(name, commandPermission, handler);
    }

    @Override
    public void initCommand() {

    }

    @Override
    public void registerSubCommand() {

    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        return false;
    }
}
