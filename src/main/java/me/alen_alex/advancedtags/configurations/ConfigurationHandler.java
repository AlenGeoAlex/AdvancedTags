package me.alen_alex.advancedtags.configurations;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.configurations.files.Configuration;
import me.alen_alex.advancedtags.configurations.files.MenuConfiguration;
import me.alen_alex.advancedtags.configurations.files.MessageConfiguration;
import me.alen_alex.advancedtags.configurations.files.TagConfiguration;
import me.alen_alex.advancedtags.utils.FileUtils;

public class ConfigurationHandler {

    private final AdvancedTags plugin;
    private FileUtils fileUtils;
    private Configuration pluginConfig;
    private MessageConfiguration messageConfiguration;
    private TagConfiguration tagConfiguration;
    private MenuConfiguration menuConfiguration;

    enum FileType{
        PLUGIN_CONFIG,
        MESSAGE_CONFIG,
        TAG_CONFIG,
        MENU_CONFIG;
    }

    public ConfigurationHandler(AdvancedTags plugins) {
        this.plugin = plugins;
        setupHandler();
    }

    private void setupHandler(){
        fileUtils = new FileUtils(plugin);
        pluginConfig = new Configuration(this);
        messageConfiguration = new MessageConfiguration(this);
        tagConfiguration = new TagConfiguration(this);
        menuConfiguration = new MenuConfiguration(this);
    }

    public boolean loadAllPluginFiles(){
        boolean loaded;
        loaded = pluginConfig.init() && messageConfiguration.init() && tagConfiguration.init() && menuConfiguration.init();
        return loaded;
    }

    public long reloadAll(){
        final long startTime = System.currentTimeMillis();
        pluginConfig.reloadConfig();
        messageConfiguration.reloadConfig();
        tagConfiguration.reloadConfig();
        menuConfiguration.reloadConfig();
        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);
    }

    public long reloadFile(FileType fileType){
        long returnTime = Long.MIN_VALUE;
        switch (fileType){
            case PLUGIN_CONFIG:
                returnTime = pluginConfig.reloadConfig();
                break;
            case MESSAGE_CONFIG:
                returnTime = messageConfiguration.reloadConfig();
                break;
            case TAG_CONFIG:
                returnTime = tagConfiguration.reloadConfig();
                break;
            case MENU_CONFIG:
                returnTime = menuConfiguration.reloadConfig();
            default:
        }
        return returnTime;
    }

    public void saveAllConfigs(){
        pluginConfig.saveConfig();
        messageConfiguration.saveConfig();
        tagConfiguration.saveConfig();
        menuConfiguration.saveConfig();
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

    public TagConfiguration getTagConfiguration() {
        return tagConfiguration;
    }

    public MenuConfiguration getMenuConfiguration() {
        return menuConfiguration;
    }
}
