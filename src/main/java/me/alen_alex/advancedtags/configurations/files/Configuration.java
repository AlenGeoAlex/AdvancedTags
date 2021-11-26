package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import org.apache.commons.lang.StringUtils;

public class Configuration extends ConfigurationFile {
    private Config config;

    private ConfigurationHandler handler;
    private String version;

    //Database-Settings

    private String databaseType,sqlHost,sqlPassword,sqlUsername,sqlDatabse;
    private String mongoHost,mongodatabase;
    private String pluginPrefix;
    private int mongoPort,sqlPort;
    private boolean sqlUseSSL;
    private boolean globalEnabled,globalPriority;
    private boolean randomTagOnInvalid;
    private boolean failedToRegister,failedToFetch;
    private String failedRegisterKickMessage,failedFetchKickMessage;
    private boolean hookVault,hookPlaceholderAPI;
    private boolean tagShopEnabled,tagShopLockedTagsOnly,setNewTagOnUnlock;
    private String tagSpaceFormat;
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
        this.version = config.getString("version");
        //Plugin-Prefix
        this.pluginPrefix = handler.getPlugin().getChatUtils().parseColorCodes(config.getString("prefix"));
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
        //Global-Tag
        this.globalEnabled = config.getBoolean("global-tag.enable");
        this.globalPriority = config.getBoolean("global-tag.priority-for-global");

        //Failed-Database-Operations
        this.failedToRegister = config.getBoolean("kick-if-failed.register.enable");
        this.failedToFetch = config.getBoolean("kick-if-failed.data-fetch.enable");
        this.failedRegisterKickMessage = IridiumColorAPI.process(config.getString("kick-if-failed.register.message"));
        this.failedFetchKickMessage = IridiumColorAPI.process(config.getString("kick-if-failed.data-fetch.message"));

        //hooks
        this.hookVault = config.getBoolean("hooks.vault");
        this.hookPlaceholderAPI = config.getBoolean("hooks.placeholder-api");

        //tagShop
        this.tagShopEnabled = config.getBoolean("tag-shop.enabled");

        //format
        this.tagSpaceFormat = IridiumColorAPI.process(config.getString("tag-format.placeholder-format"));

        //
        this.setNewTagOnUnlock = config.getBoolean("set-new-tag-when-unlocked");

        this.randomTagOnInvalid = config.getBoolean("set-random-if-invalid-dbtag");
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

    public String getPluginPrefix() {
        return pluginPrefix;
    }

    public boolean hasPluginPrefix(){
        return StringUtils.isBlank(this.pluginPrefix);
    }

    public boolean isUsingNoSQL(){
        return this.databaseType.equalsIgnoreCase("MongoDB");
    }

    public boolean isGlobalEnabled() {
        return globalEnabled;
    }

    public boolean isGlobalPriority() {
        return globalPriority;
    }

    public boolean isRandomTagOnInvalid() {
        return randomTagOnInvalid;
    }

    public boolean isFailedToRegister() {
        return failedToRegister;
    }

    public boolean isFailedToFetch() {
        return failedToFetch;
    }

    public String getFailedRegisterKickMessage() {
        return failedRegisterKickMessage;
    }

    public String getFailedFetchKickMessage() {
        return failedFetchKickMessage;
    }

    public boolean isHookVault() {
        return hookVault;
    }

    public boolean isHookPlaceholderAPI() {
        return hookPlaceholderAPI;
    }

    public boolean isTagShopEnabled() {
        return tagShopEnabled;
    }

    public String getTagSpaceFormat() {
        return tagSpaceFormat;
    }

    public boolean isSetNewTagOnUnlock() {
        return setNewTagOnUnlock;
    }
}

