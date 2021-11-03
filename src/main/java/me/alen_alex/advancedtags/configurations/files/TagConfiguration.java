package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;

public class TagConfiguration extends ConfigurationFile {

    private Yaml tagConfig;

    private ConfigurationHandler handler;
    private String version;

    public TagConfiguration(ConfigurationHandler handler) {
        super(handler);
    }

    @Override
    public boolean init() {
        tagConfig = handler.getFileUtils().createFile(getInputStreamFromFile("tags.yml"),"tags.yml","language");
        if(tagConfig != null){
            tagConfig.setDefault("version",getPluginVersion());
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
    }

    @Override
    public long reloadConfig() {
        final long startTime = System.currentTimeMillis();

        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);    }

    @Override
    public void saveConfig() {

    }

    @Override
    public Yaml getConfigFile() {
        return this.tagConfig;
    }

    @Override
    public String getVersion() {
        return null;
    }
}
