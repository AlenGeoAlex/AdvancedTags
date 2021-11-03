package me.alen_alex.advancedtags;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.plugin.PluginAdapter;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.utils.ChatUtils;

public final class AdvancedTags extends PluginAdapter {

    private static AdvancedTags plugin;
    private PluginManager pluginManager;
    private ConfigurationHandler configurationHandler;
    private StorageHandler storageHandler;
    private ChatUtils chatUtils;

    @Override
    public void onLoad(){
        CoreAPI.init(this);
    }

    @Override
    protected boolean setUp() {
        plugin = this;
        CoreAPI.getInstance().load();
        pluginManager = new PluginManager(this);
        return true;
    }

    @Override
    protected boolean setUpConfig(){
        configurationHandler = new ConfigurationHandler(this);
        return configurationHandler.loadAllPluginFiles();
    }

    @Override
    protected boolean setUpHandlers(){
        storageHandler = new StorageHandler(this);
        if(!storageHandler.connect()){
            getLogger().severe("Unable to connect to database... Plugin will be disabled");
            return false;
        }
        getLogger().info("Connected to storage worker on "+storageHandler.getType().name());
        chatUtils = new ChatUtils(this);
        if(configurationHandler.getPluginConfig().hasPluginPrefix())
            chatUtils.setPrefix(configurationHandler.getPluginConfig().getPluginPrefix());
        return true;
    }

    @Override
    public void onDisable() {
        if(storageHandler != null){
            storageHandler.disconnect();
            getLogger().info("Successfully disconnected from Database Service ["+storageHandler.getType().name()+"]");
        }else getLogger().warning("Storage Worker (Database) was not initialized, Hence didn't close the connection!");
        storageHandler = null;
        configurationHandler.saveAllConfigs();
        configurationHandler = null;
        chatUtils = null;
    }


    public AdvancedTags getPlugin() {
        return plugin;
    }


    /**
     * The Configuration Handler. It Handles Every Files Required By The Plugin
     * @return
     */
    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }



    /**
     * The Chat Handler
     * @return
     */
    public ChatUtils getChatUtils() {
        return chatUtils;
    }

    /**
     * The plugin handler, where the plugin caches PlayerData and Tag Data
     * @return
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * The Database Storage Worker
     * @return Storage Handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }
}
