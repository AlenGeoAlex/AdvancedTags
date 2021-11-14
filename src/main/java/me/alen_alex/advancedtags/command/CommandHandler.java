package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.command.advancedtag.AdvancedTagCommand;

public class CommandHandler {

    private final AdvancedTags plugin;

    //
    private AdvancedTagCommand mainCommand;

    public CommandHandler(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    public void initCommands(){
        this.mainCommand = new AdvancedTagCommand("advancedtag","at.command.*",this);
    }


    public AdvancedTags getPlugin() {
        return plugin;
    }
}
