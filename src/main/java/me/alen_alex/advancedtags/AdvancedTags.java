package me.alen_alex.advancedtags;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.plugin.PluginAdapter;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.database.methods.MongoDB;
import me.alen_alex.advancedtags.database.methods.SQL;
import me.alen_alex.advancedtags.utils.ChatUtils;

public final class AdvancedTags extends PluginAdapter {

    private static AdvancedTags plugin;
    private ConfigurationHandler configurationHandler;
    private StorageWorker storageWorker;
    private ChatUtils chatUtils;

    @Override
    public void onLoad(){
        CoreAPI.init(this);
    }

    @Override
    protected boolean setUp() {
        plugin = this;
        CoreAPI.getInstance().load();
        return true;
    }

    @Override
    protected boolean setUpConfig(){
        configurationHandler = new ConfigurationHandler(this);
        return configurationHandler.loadAllPluginFiles();
    }

    @Override
    protected boolean setUpHandlers(){
        if(configurationHandler.getPluginConfig().isUsingNoSQL())
            storageWorker = new MongoDB(this);
        else
            storageWorker = new SQL(this);
        if(!storageWorker.init()){
            getLogger().severe("Unable to connect to database... Plugin will be disabled");
            return false;
        }
        getLogger().info("Connected to storage worker on "+storageWorker.getDatabaseType());
        chatUtils = new ChatUtils(this);
        if(configurationHandler.getPluginConfig().hasPluginPrefix())
            chatUtils.setPrefix(configurationHandler.getPluginConfig().getPluginPrefix());
        return true;
    }

    @Override
    public void onDisable() {
        if(storageWorker != null){
            storageWorker.disconnect();
            getLogger().info("Successfully disconnected from Database Service ["+storageWorker.getDatabaseType()+"]");
        }else getLogger().warning("Storage Worker (Database) was not initialized, Hence didn't close the connection!");
        storageWorker = null;
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
     * The Database Storage Worker
     * @return Storage Worker
     */
    public StorageWorker getStorageWorker() {
        return storageWorker;
    }

    /**
     * The Chat Handler
     * @return
     */
    public ChatUtils getChatUtils() {
        return chatUtils;
    }
}
