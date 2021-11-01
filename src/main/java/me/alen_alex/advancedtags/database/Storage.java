package me.alen_alex.advancedtags.database;

import me.Abhigya.core.database.Database;
import me.Abhigya.core.database.sql.h2.H2;
import me.Abhigya.core.database.sql.hikaricp.HikariCP;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.Abhigya.core.database.sql.sqlite.SQLite;
import me.alen_alex.advancedtags.AdvancedTags;

import java.io.File;

public abstract class Storage {

    private AdvancedTags plugin;
    private Database databaseEngine;
    private final String LOCAL_PATH;

    public Storage(AdvancedTags plugins) {
        this.plugin = plugins;
        LOCAL_PATH = plugin.getDataFolder().getAbsolutePath()+File.separator+"storage"+File.separator+"database.db";
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }

    private void configureDatabaseType(){

    }


    public void buildConnection(){
        switch (plugin.getConfigurationHandler().getPluginConfig().getDatabaseType().toUpperCase()){
            case "H2":
                final File h2File = new File(LOCAL_PATH);
                databaseEngine = new H2(h2File,true);
                break;
            case "MYSQL":
                /*databaseEngine = new HikariCP(new HikariClientBuilder(
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                        Integer.parseInt(getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort()),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                        getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                        true,
                        getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()).build());*/
                break;
            case "SQLITE":
                final File sqliteFile = new File(LOCAL_PATH);
                databaseEngine = new SQLite(sqliteFile,true);
                break;
            default:
                final File h2FileDB = new File(LOCAL_PATH);
                databaseEngine = new H2(h2FileDB,true);
                getPlugin().getLogger().severe("Unknown database type found in config, Forcing to H2");
        }
    }

    public abstract boolean handleStartupWorks();



}
