package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class H2 implements StorageWorker {

    private SQLDatabase databaseEngine;
    private String dataPath;
    private StorageHandler handler;

    public H2(StorageHandler handler) {
        this.handler = handler;
        this.dataPath = handler.getPlugin().getDataFolder().getPath()+ File.separator+"storage"+File.separator+"database.db";
    }

    @Override
    public boolean init() {
        try {
            final File databaseFile = new File(dataPath);
            databaseEngine = new me.Abhigya.core.database.sql.h2.H2(databaseFile, true);
            return databaseEngine.isConnected();
        }catch (Exception e) {
            handler.getPlugin().getLogger().severe("Unable to connect to H2 Database");
            e.printStackTrace();
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
        return DatabaseType.H2;
    }

    @Override
    public boolean registerUser(UUID player) {
        return false;
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
}
