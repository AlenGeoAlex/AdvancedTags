package me.alen_alex.advancedtags.database.methods;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageWorker;

public class MongoDB implements StorageWorker {

    private me.Abhigya.core.database.mongo.MongoDB databaseEngine;
    private AdvancedTags plugin;

    public MongoDB(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean init() {
        databaseEngine = new me.Abhigya.core.database.mongo.MongoDB(
                plugin.getConfigurationHandler().getPluginConfig().getMongoHost(),
                plugin.getConfigurationHandler().getPluginConfig().getMongoPort(),
                plugin.getConfigurationHandler().getPluginConfig().getMongodatabase()
        );
        return connect();
    }

    @Override
    public boolean handleIntial() {
        return false;
    }

    @Override
    public String getDatabaseType() {
        return databaseEngine.getDatabaseType().name();
    }

    private boolean connect(){
        databaseEngine.connect();
        return databaseEngine.isConnected();
    }

}
