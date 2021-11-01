package me.alen_alex.advancedtags.configurations;

import de.leonhard.storage.Yaml;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public abstract class ConfigurationFile {

    private ConfigurationHandler handler;

    public ConfigurationFile(ConfigurationHandler handler) {
        this.handler = handler;
    }

    public abstract boolean init();

    public abstract void loadConfig();

    public abstract long reloadConfig();

    public abstract void saveConfig();

    public abstract Yaml getConfigFile();

    public abstract String getVersion();

    public ConfigurationHandler getHandler() {
        return handler;
    }

    public String getPluginVersion(){
        return handler.getPlugin().getDescription().getVersion();
    }

    public InputStream getInputStreamFromFile(@NotNull String fileName){
        return handler.getPlugin().getResource(fileName);
    }
}
