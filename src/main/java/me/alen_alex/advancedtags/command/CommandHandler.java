package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.command.advancedtag.AdvancedTagCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private final AdvancedTags plugin;
    private final List<TextComponent> helpMessage;
    //
    private AdvancedTagCommand mainCommand;

    public CommandHandler(AdvancedTags plugin) {
        this.plugin = plugin;
        this.helpMessage = new ArrayList<TextComponent>();
    }

    public void initCommands(){
        this.mainCommand = new AdvancedTagCommand("advancedtag","at.command",this);
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
            tc.setText(getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpPlaceholder(subcommand.getCommandName(),subcommand.getCommandDescription()));
            if(isJson)
                tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,subcommand.getSuggestionString()));
            helpMessage.add(tc);
        });
        getPlugin().getConfigurationHandler().getMessageConfiguration().getHelpFooter().forEach((s -> {
            final TextComponent textComponent = new TextComponent(s);
            helpMessage.add(textComponent);
        }));
    }


    public AdvancedTags getPlugin() {
        return plugin;
    }

    public void sendHelpMessage(Player player){
        for (TextComponent msg : helpMessage)
            player.spigot().sendMessage(msg);
    }
}
