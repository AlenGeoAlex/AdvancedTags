package me.alen_alex.advancedtags.database;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.h2.H2;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.methods.MongoDB;
import me.alen_alex.advancedtags.database.methods.MySQL;
import me.alen_alex.advancedtags.database.methods.PostGreSQL;
import me.alen_alex.advancedtags.database.methods.SQLite;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageHandler {

    private final AdvancedTags plugin;
    private StorageWorker databaseImpl;
    private static final String SQL_PLAYER_DATA_TABLE = "adtag_pd";
    private static final String SQL_GLOBAL_TAG_TABLE = "adtab_gt";
    private static final String TAG_DELIMITER = "/:/";

    public StorageHandler(AdvancedTags plugins) {
        this.plugin = plugins;
        buildConnection();
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    private void buildConnection(){
        switch (plugin.getConfigurationHandler().getPluginConfig().getDatabaseType().toUpperCase()){
            case "H2":
                databaseImpl = new me.alen_alex.advancedtags.database.methods.H2(this);
                break;
            case "MYSQL":
                databaseImpl = new MySQL(this);
                break;
            case "SQLITE":
                databaseImpl = new SQLite(this);
                break;
            case "POSTGRESQL":
                databaseImpl = new PostGreSQL(this);
                break;
            case "MONGODB":
                databaseImpl = new MongoDB(this);
                break;
            default:
                databaseImpl = new me.alen_alex.advancedtags.database.methods.H2(this);
                getPlugin().getLogger().severe("Unknown database type found in config, Forcing to H2");
        }
    }

    public boolean connect(){
        return databaseImpl.init();
    }

    public boolean doStartupWorks(){
        return databaseImpl.handleInitial();
    }

    public void disconnect(){
        databaseImpl.disconnect();
    }


    public boolean isUsingOnlineDatabase(){
        return getType() == DatabaseType.HikariCP || getType() == DatabaseType.PostGreSQL || getType() == DatabaseType.MYSQL;
    }

    public StorageWorker getDatabaseImpl() {
        return databaseImpl;
    }

    public String getSqlPlayerDataTable(){
        return SQL_PLAYER_DATA_TABLE;
    }

    public String getSqlGlobalTagTable(){
        return SQL_GLOBAL_TAG_TABLE;
    }

    public DatabaseType getType(){
        return this.databaseImpl.getType();
    }

    public List<String> fetchAndSplitTags(String fromDB){
        List<String> tags = new ArrayList<String>();
        if(StringUtils.isBlank(fromDB))
            return tags;
        Arrays.stream(fromDB.split(TAG_DELIMITER)).forEach(s -> tags.add(s));

        return tags;
    }

    public String concatTags(@NotNull List<String> tags){
        if(tags.isEmpty())
            return null;

        return String.join(TAG_DELIMITER,tags);
    }
}
