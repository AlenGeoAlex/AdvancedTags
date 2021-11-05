package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.utils.ChatUtils;

public class MessageConfiguration extends ConfigurationFile{

    private Yaml messageConfig;

    private ConfigurationHandler handler;
    private String version;
    private ChatUtils chatUtils;

    private String selectedRandomTag,removedTagNoTag;

    public MessageConfiguration(ConfigurationHandler handler) {
        super(handler);
        this.handler = handler;
        chatUtils = handler.getPlugin().getChatUtils();
    }

    @Override
    public boolean init() {
        messageConfig = handler.getFileUtils().createFile(getInputStreamFromFile("messages.yml"),"messages.yml","language");
        if(messageConfig != null){
            messageConfig.setDefault("version",getPluginVersion());
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
        version = messageConfig.getString("version");

        //Tag Proccess
        this.selectedRandomTag = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("selecting-random-tag-no-tag-on-server"));
        this.removedTagNoTag = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("no-tag-on-the-server"));

        getHandler().getPlugin().getLogger().info("Message Configuration has been loaded with the version "+getVersion());
    }

    @Override
    public long reloadConfig() {
        final long startTime = System.currentTimeMillis();

        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public Yaml getConfigFile() {
        return this.messageConfig;
    }

    @Override
    public String getVersion() {
        return this.version;
    }



}
