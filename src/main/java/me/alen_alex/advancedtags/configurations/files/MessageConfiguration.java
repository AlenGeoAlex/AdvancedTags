package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.utils.ChatUtils;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageConfiguration extends ConfigurationFile{

    private Yaml messageConfig;

    private ConfigurationHandler handler;
    private String version;

    private String selectedRandomTag,removedTagNoTag;
    private String unknownCommand,noPermission,notAConsoleCommand;

    //HelpMessage
    private List<String> helpHeader, helpFooter;
    private boolean enableJsonHelpMessage;
    private String helpPlaceholder;
    private String testMessage;
    private List<String> testMessageList;

    public MessageConfiguration(ConfigurationHandler handler) {
        super(handler);
        this.handler = handler;
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

        this.helpHeader = new ArrayList<String>();
        this.helpFooter = new ArrayList<String>();

        //Plugin messages
        this.unknownCommand = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("errors.unknown-command"));
        this.noPermission = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("errors.no-permission"));
        this.notAConsoleCommand = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("errors.unable-to-run-from-console"));


        //Tag Proccess
        this.selectedRandomTag = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("selecting-random-tag-no-tag-on-server"));
        this.removedTagNoTag = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("no-tag-on-the-server"));

        //test-messages
        this.testMessage = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("test-message.format"));
        this.testMessageList = IridiumColorAPI.process(this.messageConfig.getStringList("test-message.messages"));

        //HelpMessage
        this.helpHeader = IridiumColorAPI.process(this.messageConfig.getStringList("help-message.header"));
        this.helpFooter = IridiumColorAPI.process(this.messageConfig.getStringList("help-message.footer"));
        this.enableJsonHelpMessage = this.messageConfig.getBoolean("help-message.enable-json-suggestion");
        this.helpPlaceholder = handler.getPlugin().getChatUtils().parseColorCodes(this.messageConfig.getString("help-message.message-placeholder"));
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

    public String getSelectedRandomTag(String oldTag,String newTag){
        return this.selectedRandomTag.replaceAll("%selected_tag%",oldTag).replaceAll("%temp_tag%",newTag);
    }

    public String getRemovedTagNoTag(String oldTag){
        return this.selectedRandomTag.replaceAll("%selected_tag%",oldTag);
    }

    @Override
    public Yaml getConfigFile() {
        return this.messageConfig;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    public String getUnknownCommand() {
        return unknownCommand;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getNotAConsoleCommand() {
        return notAConsoleCommand;
    }

    public List<String> getHelpHeader() {
        return helpHeader;
    }

    public List<String> getHelpFooter() {
        return helpFooter;
    }

    public boolean isEnableJsonHelpMessage() {
        return enableJsonHelpMessage;
    }

    public String getHelpPlaceholder() {
        return helpPlaceholder;
    }

    public String getHelpPlaceholder(String commandName,String description){
        return this.helpPlaceholder.replaceAll("%command%",commandName).replaceAll("%description%",description);
    }

    public String getTestMessage(String tagName, Player player){
        final String message = testMessageList.get(new Random().nextInt(testMessageList.size()));
        return PlaceholderAPI.setPlaceholders(player,(testMessage.replaceAll("%testtag%",tagName).replaceAll("%player_name%",player.getName()).replaceAll("%message%",message)));
    }
}
