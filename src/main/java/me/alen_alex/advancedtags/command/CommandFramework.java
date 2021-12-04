package me.alen_alex.advancedtags.command;

import com.google.common.base.Objects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class CommandFramework extends Command implements TabCompleter {

    private final String name;
    private final String permission;
    private final CommandHandler handler;
    private final HashMap<String,CommandWorkerImpl> subcommands;
    private boolean helpOnNoArgs;
    private final CommandWorkerImpl defaultCommand;

    public CommandFramework(@NotNull String name, @NotNull String permission, @NotNull CommandHandler handler, CommandWorkerImpl defaultCommand) {
        super(name);
        this.name = name;
        this.permission = permission;
        this.handler = handler;
        if(defaultCommand != null)
            this.helpOnNoArgs = true;
        this.defaultCommand = defaultCommand;
        subcommands = new HashMap<String,CommandWorkerImpl>();
    }

    public CommandFramework(@NotNull String name, @NotNull String permission, @NotNull CommandHandler handler) {
        super(name);
        this.name = name;
        this.permission = permission;
        this.handler = handler;
        this.helpOnNoArgs = false;
        this.defaultCommand = null;
        subcommands = new HashMap<String,CommandWorkerImpl>();
    }

    @NotNull
    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    @NotNull
    public CommandHandler getHandler() {
        return handler;
    }

    protected void executeCommand(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args){
        //If args is 0
        if(args.length == 0){
            //If the default message is to show the help message
            if(helpOnNoArgs)
                handler.sendHelpMessage(sender);
            //If the default message is assigned
            else {
                //If the player is not an instance of player
                if(! (sender instanceof Player)){
                    //If the default message allocated is not null and is a console command
                    if(java.util.Objects.requireNonNull(defaultCommand).isConsoleCommand())
                        //Execute the console part
                        defaultCommand.executeCommand(sender,args);
                    //Else send cannot run from console message
                    else handler.sendCannotRunFromConsole(sender);
                }else java.util.Objects.requireNonNull(defaultCommand).executeCommand(((Player) sender).getPlayer(), args);
            }
        }else {
            //If the args entered matches the subcommand registered
            if(subcommands.containsKey(args[0])){
                final CommandWorkerImpl commandWorker = subcommands.get(args[0]);
                //Checks Where is the sender is a player
                if(sender instanceof Player) {
                    //If its a player, Checks whether he/she has permission to execute the command
                    if (!handler.hasPermission(sender, commandWorker.getCommandPermission())) {
                        handler.sendNoPermission((Player) sender);
                        return;
                    }

                    commandWorker.executeCommand((Player) sender,args);
                    //If its not a player
                }else {
                    //Checks the command can be executed from console
                    if(!commandWorker.isConsoleCommand()){
                        handler.sendCannotRunFromConsole(sender);
                        return;
                    }
                    commandWorker.executeCommand(sender,args);
                }
            //If the command is not present, Send unknown command
            }else handler.sendUnknownCommand(sender);
        }
    }

    protected List<String> executeTabCompletion(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args){
        final List<String> tabSugst = new ArrayList<String>();

        return tabSugst;
    }



    protected void registerSubcommands(CommandWorkerImpl... commands){
        Arrays.stream(commands).forEach(this::registerSubcommand);
    }

    protected void registerSubcommand(@NotNull CommandWorkerImpl commandWorker){
        if(subcommands.containsKey(commandWorker.getCommandName()))
            return;

        subcommands.put(commandWorker.getCommandDescription(),commandWorker);
        if(commandWorker.getCommandAliases() != null){
            commandWorker.getCommandAliases().forEach((aliases) ->{
                subcommands.put(aliases,commandWorker);
            } );
        }
    }

    protected void registerCommand(){
        Field commandField = null;
        try {
            commandField = this.handler.getPlugin().getServer().getClass().getDeclaredField("commandMap");
            commandField.setAccessible(true);
            CommandMap commandMap = null;
            commandMap = (CommandMap) commandField.get(this.handler.getPlugin().getServer());
            commandMap.register(name, this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        registerTabCompleter();
    }

    private void registerTabCompleter(){
        handler.getPlugin().getCommand(name).setTabCompleter(this);
    }

    public HashMap<String, CommandWorkerImpl> getSubcommands() {
        return subcommands;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommandFramework that = (CommandFramework) o;
        return Objects.equal(getName(), that.getName()) && Objects.equal(getPermission(), that.getPermission()) && Objects.equal(getHandler(), that.getHandler()) && Objects.equal(getSubcommands(), that.getSubcommands());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getPermission(), getHandler(), getSubcommands());
    }
}
