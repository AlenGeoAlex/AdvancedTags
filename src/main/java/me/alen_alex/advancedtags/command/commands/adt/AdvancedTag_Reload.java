package me.alen_alex.advancedtags.command.commands.adt;

import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandMessages;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdvancedTag_Reload implements CommandWorkerImpl {

    private final CommandHandler handler;

    public AdvancedTag_Reload(CommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        if(args.length == 1){
            handler.getPlugin().callReload();
        }else if(args.length == 2){
            final String reloadFile = args[1];
            boolean reloaded = false;
            switch (reloadFile.toUpperCase()){
                case "CONFIF":
                case "CONF":
                case "CFG":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.PLUGIN_CONFIG);
                    reloaded = true;
                    break;
                case "TAGS":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.TAG_CONFIG);
                    reloaded = true;
                    break;
                case "MESSAGE":
                case "MESSAGES":
                case "MSG":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.MESSAGE_CONFIG);
                    reloaded = true;
                    break;
                case "MENU":
                case "GUI":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.MENU_CONFIG);
                    handler.getPlugin().getGuiHandler().reloadMenuStatics();
                    reloaded = true;
                    break;
                case "ALL":
                    handler.getPlugin().callReload();
                    reloaded = true;
                    break;
                default: sender.sendMessage(CommandMessages.RELOAD_FILE_UNKNOWN_ARGS.getMessage());
            }
            if(reloaded)
               sender.sendMessage(CommandMessages.FILE_RELOADED.getMessage());
        }
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        if(args.length == 1){
            handler.getPlugin().callReload();
        }else if(args.length == 2){
            final String reloadFile = args[1];
            boolean reloaded = false;
            switch (reloadFile.toUpperCase()){
                case "CONFIF":
                case "CONF":
                case "CFG":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.PLUGIN_CONFIG);
                    reloaded = true;
                    break;
                case "TAGS":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.TAG_CONFIG);
                    reloaded = true;
                    break;
                case "MESSAGE":
                case "MESSAGES":
                case "MSG":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.MESSAGE_CONFIG);
                    reloaded = true;
                    break;
                case "MENU":
                case "GUI":
                    handler.getPlugin().getConfigurationHandler().reloadFile(ConfigurationHandler.FileType.MENU_CONFIG);
                    handler.getPlugin().getGuiHandler().reloadMenuStatics();
                    reloaded = true;
                    break;
                case "ALL":
                    handler.getPlugin().callReload();
                    reloaded = true;
                    break;
                default: handler.getPlugin().getChatUtils().sendSimpleMessage(player, CommandMessages.RELOAD_FILE_UNKNOWN_ARGS.getMessage());
            }
            if(reloaded)
                handler.getPlugin().getChatUtils().sendSimpleMessage(player,CommandMessages.FILE_RELOADED.getMessage());
        }
    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "adt.command.reload";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "reload";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "Reloads the plugin";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "adt reload";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "/adt reload";
    }

    @Override
    public List<String> completeTab(String[] args) {
        if(args.length == 1){

        }
        return null;
    }
}
