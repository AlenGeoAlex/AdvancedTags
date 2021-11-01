package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;

public class Configuration extends ConfigurationFile {
    private Config config;

    private ConfigurationHandler handler;
    private String version;

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
        version = config.getString("version");
        getHandler().getPlugin().getLogger().info("Plugin Configuration has been loaded with the version "+getVersion());
    }

    @Override
    public long reloadConfig() {
        final long startTime = System.currentTimeMillis();

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
}
