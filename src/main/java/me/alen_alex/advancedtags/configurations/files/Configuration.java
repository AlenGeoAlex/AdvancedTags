package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;

public class Configuration extends ConfigurationFile {
    private Config config;

    private ConfigurationHandler handler;
    private String version;

    //Database-Settings

    private String databaseType,sqlHost,sqlPassword,sqlUsername,sqlDatabse;
    private String mongoHost,mongodatabase;
    private int mongoPort,sqlPort;
    private boolean sqlUseSSL;

    public Configuration(ConfigurationHandler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public boolean init() {
        config = handler.getFileUtils().createConfiguration();
        if(config != null){
            config.setDefault("version",getPluginVersion());
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
        config.getString("storage.mysql-settings");
        this.version = config.getString("version");
        //Database -- SQL
        this.databaseType = config.getString("storage.DBType");
        this.sqlHost = config.getString("storage.mysql-settings.host");
        this.sqlPort = config.getInt("storage.mysql-settings.port");
        this.sqlUsername = config.getString("storage.mysql-settings.username");
        this.sqlPassword = config.getString("storage.mysql-settings.password");
        this.sqlDatabse = config.getString("storage.mysql-settings.database");
        this.sqlUseSSL = config.getBoolean("storage.mysql-settings.use-ssl");
        //Database -- MongoDB
        if(databaseType.equalsIgnoreCase("MongoDB")) {
            this.mongoHost = config.getString("storage.mongoDB-settings.host");
            this.mongoPort = config.getInt("storage.mongoDB-settings.port");
            this.mongodatabase = config.getString("storage.mongoDB-settings.database");
        }
        getHandler().getPlugin().getLogger().info("Plugin Configuration has been loaded with the version "+getVersion());
    }

    @Override
    public long reloadConfig() {
        final long startTime = System.currentTimeMillis();
        this.config.forceReload();
        init();
        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);
    }

    @Override
    public void saveConfig() {
        //TODO
    }

    @Override
    public Yaml getConfigFile() {
        return (Yaml) config;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getSqlHost() {
        return sqlHost;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public String getSqlUsername() {
        return sqlUsername;
    }

    public int getSqlPort() {
        return sqlPort;
    }

    public String getSqlDatabse() {
        return sqlDatabse;
    }

    public boolean isSqlUseSSL() {
        return sqlUseSSL;
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public String getMongodatabase() {
        return mongodatabase;
    }

    public int getMongoPort() {
        return mongoPort;
    }

    public boolean isUsingNoSQL(){
        return this.databaseType.equalsIgnoreCase("MongoDB");
    }
}
