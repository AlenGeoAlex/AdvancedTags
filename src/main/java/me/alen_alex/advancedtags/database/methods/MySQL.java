package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.hikaricp.HikariCP;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;

import java.sql.PreparedStatement;

public class MySQL implements StorageWorker {

    private SQLDatabase databaseEngine;
    private StorageHandler handler;

    public MySQL(StorageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean init() {
        try {
            databaseEngine =  new HikariClientBuilder(handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                    true,
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()).build();
            databaseEngine.connect();
            return databaseEngine.isConnected();
        }catch (Exception e){
            handler.getPlugin().getLogger().severe("Unable to connect to MySQL (HikkariCP)");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean handleInitial() {
        try {
            final String createPlayerData = "CREATE TABLE IF NOT EXISTS "+this.handler.getSqlPlayerDataTable()+" (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `uuid` VARCHAR(50) NOT NULL, `tags` VARCHAR(MAX));";
            final String createGlobalData = "CREATE TABLE IF NOT EXISTS "+this.handler.getSqlGlobalTagTable()+" (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY)";
            //TODO Change to Execute
            this.databaseEngine.executeAsync(createPlayerData);
            if(this.handler.getPlugin().getConfigurationHandler().getPluginConfig().isGlobalEnabled()){
                if(this.handler.isUsingOnlineDatabase()){
                    this.databaseEngine.executeAsync(createGlobalData);
                    this.handler.getPlugin().getLogger().info("Global Database has been created!");
                }else this.handler.getPlugin().getLogger().severe("Global database system is enabled, But is using an offline method (Local Storage) to save the data");
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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
        return DatabaseType.MYSQL;
    }
}
