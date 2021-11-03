package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;

import java.io.File;

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
}
