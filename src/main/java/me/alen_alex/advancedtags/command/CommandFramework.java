package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public abstract class CommandFramework extends Command {

    protected String commandName;
    protected String commandPermission;
    protected CommandHandler handler;

    public CommandFramework(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler) {
        super(name);
        this.commandName = name;
        this.commandPermission = commandPermission;
        this.handler = handler;
    }

    protected void register(){
        Field commandField = null;
        try {
            commandField = this.handler.getPlugin().getServer().getClass().getDeclaredField("commandMap");
            commandField.setAccessible(true);
            CommandMap commandMap = null;
            commandMap = (CommandMap) commandField.get(this.handler.getPlugin().getServer());
            commandMap.register(commandName, this);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public abstract void initCommand();

    public abstract void registerSubCommand();


}
