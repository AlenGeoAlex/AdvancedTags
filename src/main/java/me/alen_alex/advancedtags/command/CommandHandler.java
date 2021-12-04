package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private final AdvancedTags plugin;
    private final List<TextComponent> helpMessage;

    public CommandHandler(AdvancedTags plugin) {
        this.plugin = plugin;
        this.helpMessage = new ArrayList<>();
    }

    public void initCommandHandler(){

        prepareHelpMessage();
    };

    private void prepareHelpMessage(){

    }

    public void reloadHandler(){
        prepareHelpMessage();
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    private void sendHelpMessage(Player player){
        for (TextComponent msg : helpMessage)
            player.spigot().sendMessage(msg);
    }

    public void sendHelpMessage(CommandSender sender){
        if(sender instanceof Player){
            sendHelpMessage(((Player) sender).getPlayer());
        }else {
            for (TextComponent msg : helpMessage)
                sender.sendMessage(msg.getText());
        }
    }

    public void sendUnknownCommand(CommandSender sender){
        if(sender instanceof Player) plugin.getChatUtils().sendSimpleMessage((Player) sender,getPlugin().getConfigurationHandler().getMessageConfiguration().getUnknownCommand());
        else sender.sendMessage(getPlugin().getConfigurationHandler().getMessageConfiguration().getUnknownCommand());
    }

    public void sendNoPermission(Player player){
        getPlugin().getChatUtils().sendSimpleMessage(player,getPlugin().getConfigurationHandler().getMessageConfiguration().getNoPermission());

    }

    public void sendCannotRunFromConsole(CommandSender sender){
        getPlugin().getChatUtils().sendSimpleMessage(sender,getPlugin().getConfigurationHandler().getMessageConfiguration().getNotAConsoleCommand());
    }

    public boolean hasPermission(CommandSender sender,String perm){
        if(StringUtils.isBlank(perm))
            return true;

        if(sender instanceof Player)
            return sender.hasPermission(perm);
        else return true;
    }
}
