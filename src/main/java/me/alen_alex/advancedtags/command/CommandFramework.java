package me.alen_alex.advancedtags.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public abstract class CommandFramework extends Command {

    protected String commandName;
    protected String commandPermission;
    protected CommandHandler handler;
    private HashMap<String,Subcommand> subcommands;

    public CommandFramework(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler) {
        super(name);
        this.commandName = name;
        this.commandPermission = commandPermission;
        this.handler = handler;
        subcommands = new HashMap<String,Subcommand>();
    }

    public CommandFramework(@NotNull String name, @NotNull String commandPermission, @NotNull CommandHandler handler,Subcommand... subcommand) {
        super(name);
        this.commandName = name;
        this.commandPermission = commandPermission;
        this.handler = handler;
        subcommands = new HashMap<String,Subcommand>();
        Arrays.stream(subcommand).forEach(this::registerSubcommand);
    }

    protected void registerCommand(){
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

    protected void registerSubcommand(Subcommand command){
        if(subcommands.containsKey(command.getCommandName()))
            return;

        command.getAliases().forEach((s) -> {
            if(subcommands.containsKey(s)){
                handler.getPlugin().getLogger().warning("The command "+s+" has already been registered for the command on "+subcommands.get(s).getCommandName()+" - "+subcommands.get(s).getCommandDescription()+".");
                return;
            }
            subcommands.put(s,command);
        });
    }

    protected void registerSubcommands(Subcommand... command){
        Arrays.stream(command).forEach(this::registerSubcommand);
    }

    protected void unregisterSubCommand(Subcommand subcommand){
        this.unregisterSubCommand(subcommand.getCommandName());
    }

    protected void unregisterSubCommand(String subcommand){
        if(subcommands.containsKey(subcommand))
            subcommands.remove(subcommand);
    }

    protected void executeCommand(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args){
        if(args.length == 0){

            return;
        }else {
            if(subcommands.containsKey(args[0])){
                final Subcommand subcommand = subcommands.get(args[0]);
                if(!(sender instanceof Player) && !subcommand.doRunOnConsole()){
                    sender.sendMessage(sendCannotRunFromConsole());
                    return;
                }
                final Player player = (Player) sender;
                if(!sender.hasPermission(subcommand.getCommandPermission())){
                    getHandler().getPlugin().getChatUtils().sendMessage(player,sendNoPermission());
                    return;
                }

                subcommand.runMethod(player,args);
            }
        }
    }

    public HashMap<String, Subcommand> getSubcommands() {
        return subcommands;
    }

    protected String sendUnknownPermission(){

        return null;
    }

    protected String sendNoPermission(){
        return null;

    }

    protected String sendCannotRunFromConsole(){
        return null;

    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandPermission() {
        return commandPermission;
    }

    public CommandHandler getHandler() {
        return handler;
    }
}
