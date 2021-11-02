package me.alen_alex.advancedtags;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.plugin.PluginAdapter;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.database.methods.MongoDB;
import me.alen_alex.advancedtags.database.methods.SQL;

public final class AdvancedTags extends PluginAdapter {

    private static AdvancedTags plugin;
    private ConfigurationHandler configurationHandler;
    private StorageWorker storageWorker;

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
        storageWorker.init();
        getLogger().info("Connected to storage worker on "+storageWorker.getDatabaseType());
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
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }

    public StorageWorker getStorageWorker() {
        return storageWorker;
    }
}
