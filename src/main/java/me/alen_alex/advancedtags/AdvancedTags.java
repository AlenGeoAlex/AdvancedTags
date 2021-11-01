package me.alen_alex.advancedtags;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.plugin.PluginAdapter;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;

public final class AdvancedTags extends PluginAdapter {

    private static AdvancedTags plugin;
    private ConfigurationHandler configurationHandler;

    @Override
    public void onLoad(){
        CoreAPI.init(this);
    }

    @Override
    protected boolean setUp() {
        plugin = this;
        CoreAPI.getInstance().load();
        if(initPlugin())
            return true;
        else
            return false;
    }

    @Override
    protected boolean setUpConfig(){
        configurationHandler = new ConfigurationHandler(this);
        return configurationHandler.loadAllPluginFiles();
    }

    @Override
    public void onDisable() {

    }

    public static AdvancedTags getPlugin() {
        return plugin;
    }

    private boolean initPlugin(){

        return true;
    }

}
