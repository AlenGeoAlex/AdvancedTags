package me.alen_alex.advancedtags;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.plugin.PluginAdapter;
import me.alen_alex.advancedtags.command.CommandHandler;
import me.alen_alex.advancedtags.commandold.CommandHandler_;
import me.alen_alex.advancedtags.hook.HookManager;

import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.gui.GUIHandler;
import me.alen_alex.advancedtags.listeners.AsyncPlayerJoinEvent;
import me.alen_alex.advancedtags.listeners.PlayerJoinEvent;
import me.alen_alex.advancedtags.listeners.PlayerQuitEvent;
import me.alen_alex.advancedtags.task.AutoSaveData;
import me.alen_alex.advancedtags.utils.ChatUtils;


public final class AdvancedTags extends PluginAdapter {

    private static AdvancedTags plugin;
    private PluginDataManager pluginManager;
    private ConfigurationHandler configurationHandler;
    private StorageHandler storageHandler;
    private ChatUtils chatUtils;
    private GUIHandler guiHandler;
    private CommandHandler commandHandler;
    private HookManager hookManager;
    private boolean placeholderAPIEnabled;
    private AutoSaveData saveDataTask;

    @Override
    public void onLoad(){
        CoreAPI.init(this);
    }

    @Override
    protected boolean setUp() {


        plugin = this;
        CoreAPI.getInstance().load();
        pluginManager = new PluginDataManager(this);
        chatUtils = new ChatUtils(this);
        placeholderAPIEnabled = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        return true;
    }

    @Override
    protected boolean setUpConfig(){
        configurationHandler = new ConfigurationHandler(this);
        return configurationHandler.loadAllPluginFiles();
    }

    @Override
    protected boolean setUpHandlers(){
        storageHandler = new StorageHandler(this);
        try {
            if(!storageHandler.connect()){
                getLogger().severe("Unable to connect to database... Plugin will be disabled");
                return false;
            }
        }catch (Exception e){
            getLogger().severe("Unable to connect to database... Plugin will be disabled, Check console for more info");
            e.printStackTrace();
            return false;
        }


        if(!storageHandler.doStartupWorks())
            return false;

        getLogger().info("Connected to storage worker on "+storageHandler.getType().name());
        if(configurationHandler.getPluginConfig().hasPluginPrefix())
            chatUtils.setPrefix(configurationHandler.getPluginConfig().getPluginPrefix());

        guiHandler = new GUIHandler(this);
        guiHandler.init();

        hookManager = new HookManager(this);
        hookManager.initHooks();

        commandHandler = new CommandHandler(this);
        commandHandler.initCommandHandler();

        if(configurationHandler.getPluginConfig().isAutoSaveEnabled()) {
            saveDataTask = new AutoSaveData(this);
            saveDataTask.runTaskTimerAsynchronously(this,this.configurationHandler.getPluginConfig().getAutoSaveMins(),this.configurationHandler.getPluginConfig().getAutoSaveMins());
        }

        getCommand("test").setExecutor(new TestCommand(this));
        return true;
    }

    @Override
    protected boolean setUpListeners(){
        getServer().getPluginManager().registerEvents(new PlayerJoinEvent(this),this);
        getServer().getPluginManager().registerEvents(new AsyncPlayerJoinEvent(this),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEvent(this),this);


        return true;
    }

    @Override
    public void onDisable() {
        try {
            if(storageHandler != null){
                storageHandler.disconnect();
                getLogger().info("Successfully disconnected from Database Service ["+storageHandler.getType().name()+"]");
            }else getLogger().warning("Storage Worker (Database) was not initialized, Hence didn't close the connection!");
        }catch (Exception e){
            getLogger().info("Unable to close out the storage handler connection, Check out the console for more errors!");
            e.printStackTrace();
        }
        storageHandler = null;
        configurationHandler.saveAllConfigs();
        configurationHandler = null;
        chatUtils = null;
    }

    public boolean callReload(){
        long timeTaken;
        timeTaken = configurationHandler.reloadAll();
        guiHandler.reloadMenuStatics();
        commandHandler.reloadHandler();
        getLogger().info(configurationHandler.getMessageConfiguration().getReloadSuccess(timeTaken));
        return true;
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }


    /**
     * The Configuration Handler. It Handles Every Files Required By The Plugin
     * @return Configuration Handler
     */
    public ConfigurationHandler getConfigurationHandler() {
        return configurationHandler;
    }



    /**
     * The Chat Handler
     * @return ChatUtils
     */
    public ChatUtils getChatUtils() {
        return chatUtils;
    }

    /**
     * The plugin handler, where the plugin caches PlayerData and Tag Data
     * @return PluginDataManager
     */
    public PluginDataManager getPluginManager() {
        return pluginManager;
    }

    /**
     * The Database Storage Worker
     * @return Storage Handler
     */
    public StorageHandler getStorageHandler() {
        return storageHandler;
    }

    /**
     * The Command Handler Class of The Plugin!
     * @return CommandHandler
     */

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public GUIHandler getGuiHandler() {
        return guiHandler;
    }


    public boolean isPlaceholderAPIEnabled() {
        return placeholderAPIEnabled;
    }

    public HookManager getHookManager() {
        return hookManager;
    }
}
