package me.alen_alex.advancedtags.command.commands.tag;

import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.command.CommandMessages;
import me.alen_alex.advancedtags.command.CommandWorkerImpl;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TagCommandSet implements CommandWorkerImpl {

    private final CommandHandler handler;

    public TagCommandSet(CommandHandler handler) {
        this.handler = handler;
    }


    @Override
    public void executeCommand(CommandSender sender, String[] args) {
        return;
    }

    @Override
    public void executeCommand(Player player, String[] args) {
        if(args.length < 2){
            handler.getPlugin().getChatUtils().sendSimpleMessage(player, CommandMessages.MISSING_ARGS.getMessage());
        }else {
            final String tagToSelect = args[1];

            if(!handler.getPlugin().getPluginManager().containsTag(tagToSelect)){
                handler.getPlugin().getChatUtils().sendSimpleMessage(player,handler.getPlugin().getConfigurationHandler().getMessageConfiguration().getUnknownTag(tagToSelect));
                return;
            }

            if(!handler.getPlugin().getPluginManager().hasTag(player,tagToSelect)){
                handler.getPlugin().getChatUtils().sendSimpleMessage(player,handler.getPlugin().getConfigurationHandler().getMessageConfiguration().getTagNotYetBought());
                return;
            }
            final Tag playerTag = handler.getPlugin().getPluginManager().getTag(tagToSelect);

            handler.getPlugin().getPluginManager().setTagForPlayer(player,playerTag);
        }

    }

    @NotNull
    @Override
    public String getCommandPermission() {
        return "adt.command.set";
    }

    @NotNull
    @Override
    public String getCommandName() {
        return "set";
    }

    @Override
    public List<String> getCommandAliases() {
        return null;
    }

    @NotNull
    @Override
    public String getCommandDescription() {
        return "Set a players tag";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntax() {
        return "adt set [tag-name]";
    }

    @NotNull
    @Override
    public String getCommandHelpSyntaxSuggestion() {
        return "/adt set ";
    }

    @Override
    public boolean isConsoleCommand() {
        return false;
    }

    @Override
    public List<String> completeTab(String[] args) {
        return null;
    }
}
