package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PostGreSQL implements StorageWorker {

    private StorageHandler handler;
    private SQLDatabase databaseEngine;


    public PostGreSQL(StorageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean init() {
        try {
            databaseEngine = new me.Abhigya.core.database.sql.postgresql.PostGreSQL(
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                    true,
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()
            );
            databaseEngine.connect();
            return databaseEngine.isConnected();
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean handleInitial() {
        return false;
    }

    @Override
    public void disconnect() {
        try {
            this.databaseEngine.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.PostGreSQL;
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
    public boolean setCurrentTag(Tag tag) {
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
}
