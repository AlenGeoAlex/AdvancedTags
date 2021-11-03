package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;

public class MongoDB implements StorageWorker {

    private me.Abhigya.core.database.mongo.MongoDB databaseEngine;
    private final StorageHandler handler;

    public MongoDB(StorageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean init() {
        databaseEngine = new me.Abhigya.core.database.mongo.MongoDB(
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongoHost(),
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongoPort(),
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongodatabase()
        );
        return connect();
    }

    @Override
    public boolean handleInitial() {

        return false;
    }

    @Override
    public void disconnect() {
        databaseEngine.disconnect();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MongoDB;
    }

    private boolean connect(){
        databaseEngine.connect();
        return databaseEngine.isConnected();
    }

}
