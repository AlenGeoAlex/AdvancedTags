package me.alen_alex.advancedtags.configurations;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.configurations.files.Configuration;
import me.alen_alex.advancedtags.configurations.files.MessageConfiguration;
import me.alen_alex.advancedtags.utils.FileUtils;

public class ConfigurationHandler {

    private AdvancedTags plugin;
    private FileUtils fileUtils;
    //
    private Configuration pluginConfig;
    private MessageConfiguration messageConfiguration;

    enum FileType{
        PLUGINCONFIG,
        MESSAGECONFIG
    }

    public ConfigurationHandler(AdvancedTags plugins) {
        this.plugin = plugins;
        setupHandler();
    }

    private void setupHandler(){
        fileUtils = new FileUtils(plugin);
        pluginConfig = new Configuration(this);
        messageConfiguration = new MessageConfiguration(this);
    }

    public boolean loadAllPluginFiles(){
        boolean loaded = true;
        if(pluginConfig.init() && messageConfiguration.init())
            loaded = true;
        else
            loaded = false;
        return loaded;
    }

    public long reloadAll(){
        final long startTime = System.currentTimeMillis();
        pluginConfig.reloadConfig();
        messageConfiguration.reloadConfig();
        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);
    }

    public long reloadFile(FileType fileType){
        long returnTime = Long.MIN_VALUE;
        switch (fileType){
            case PLUGINCONFIG:
                returnTime = pluginConfig.reloadConfig();
                break;
            case MESSAGECONFIG:
                returnTime = messageConfiguration.reloadConfig();
                break;
            default:
        }
        return returnTime;
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    public Configuration getPluginConfig() {
        return pluginConfig;
    }

    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }
}
