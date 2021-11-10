package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.command.advancedtag.AdvancedTag;

public class CommandHandler {

    private final AdvancedTags plugin;

    private AdvancedTag mainCommand;

    public CommandHandler(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    public void initCommands(){
        this.mainCommand = new AdvancedTag("at","at.",this,null);
    }


    public AdvancedTags getPlugin() {
        return plugin;
    }
}
