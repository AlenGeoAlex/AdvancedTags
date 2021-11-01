package me.alen_alex.advancedtags.database;

import me.Abhigya.core.database.Database;
import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.h2.H2;
import me.Abhigya.core.database.sql.hikaricp.HikariCP;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.Abhigya.core.database.sql.postgresql.PostGreSQL;
import me.Abhigya.core.database.sql.sqlite.SQLite;
import me.alen_alex.advancedtags.AdvancedTags;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class SQLStorage {

    private AdvancedTags plugin;
    private SQLDatabase databaseEngine;
    private static final String LOCAL_PATH = File.separator+"storage"+File.separator+"database.db";;

    public SQLStorage(AdvancedTags plugins) {
        this.plugin = plugins;
        buildConnection();
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    private void buildConnection(){
        final File localStoragePath = new File(plugin.getDataFolder()+LOCAL_PATH);
        switch (plugin.getConfigurationHandler().getPluginConfig().getDatabaseType().toUpperCase()){
            case "H2":
                databaseEngine = new H2(localStoragePath,true);
                break;
            case "MYSQL":
                databaseEngine = new HikariClientBuilder(getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                        true,
                        getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()).build();
                break;
            case "SQLITE":
                databaseEngine = new SQLite(localStoragePath,true);
                break;
            case "POSTGRESQL":
                databaseEngine = new PostGreSQL(
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                        true,
                        getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()
                );
                break;
            default:
                databaseEngine = new H2(localStoragePath,true);
                getPlugin().getLogger().severe("Unknown database type found in config, Forcing to H2");
        }
    }

    public boolean connect(){
        try {
            databaseEngine.connect();
            return databaseEngine.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {
        try {
            if(databaseEngine.isConnected())
                return databaseEngine.getConnection();
            else {
                connect();
                return databaseEngine.getConnection();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DatabaseType getType(){
        return databaseEngine.getDatabaseType();
    }
}
