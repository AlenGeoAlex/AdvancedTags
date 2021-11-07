package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    @Override
    public CompletableFuture<Boolean> registerUser(UUID player) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> doUserExist(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<ATPlayer> loadPlayer(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> savePlayerTag(ATPlayer playerObj) {
        return null;
    }

    @Override
    public List<Tag> loadBatchTags() {
        return null;
    }

    @Override
    public boolean setCurrentTag(String name) {
        return false;
    }

    @Override
    public boolean insertGlobalTag(Tag tag) {
        return false;
    }

    @Override
    public boolean updateGlobalTag(Tag tag) {
        return false;
    }

    @Override
    public boolean removeGlobalTag(String tagName) {
        return false;
    }

    @Override
    public Tag fetchTag(String tagName) {
        return null;
    }

    private boolean connect(){
        databaseEngine.connect();
        return databaseEngine.isConnected();
    }

}
