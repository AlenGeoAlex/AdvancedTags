package me.alen_alex.advancedtags.commandold;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.commandold.advancedtag.AdvancedTagCommand;
import me.alen_alex.advancedtags.commandold.tag.TagCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler_ {

    private final AdvancedTags plugin;
    private final List<TextComponent> helpMessage;
    //
    private AdvancedTagCommand mainCommand;
    private TagCommand tagCommand;

    public CommandHandler_(AdvancedTags plugin) {
        this.plugin = plugin;
        this.helpMessage = new ArrayList<TextComponent>();
    }

    public void initCommands(){
        this.mainCommand = new AdvancedTagCommand("advancedtag","at.command.main",this);
        this.tagCommand = new TagCommand("tag","at.command.tag",this);
        prepareHelpMessage();
    }

    private void prepareHelpMessage(){
        final boolean isJson = this.getPlugin().getConfigurationHandler().getMessageConfiguration().isEnableJsonHelpMessage();
        getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpHeader().forEach((s -> {
            final TextComponent textComponent = new TextComponent(s);
            helpMessage.add(textComponent);
        }));
        this.mainCommand.getSubcommands().values().forEach((subcommand) -> {
            final TextComponent tc = new TextComponent();
            tc.setText(getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpPlaceholder(subcommand.getCommandSyntax(),subcommand.getCommandDescription()));
            if(isJson)
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,subcommand.getSuggestionString()));
            helpMessage.add(tc);
        });
        this.tagCommand.getSubcommands().values().forEach((subcommand) -> {
            final TextComponent tc = new TextComponent();
            tc.setText(getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpPlaceholder(subcommand.getCommandSyntax(),subcommand.getCommandDescription()));
            if(isJson)
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,subcommand.getSuggestionString()));
            helpMessage.add(tc);
        });
        getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpFooter().forEach((s -> {
            final TextComponent textComponent = new TextComponent(s);
            helpMessage.add(textComponent);
        }));
    }

    public void reloadHandler(){
        prepareHelpMessage();
    }


    public AdvancedTags getPlugin() {
        return plugin;
    }

    public void sendHelpMessage(Player player){
        for (TextComponent msg : helpMessage)
            player.spigot().sendMessage(msg);
    }
}
