package me.alen_alex.advancedtags.hook;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.hook.placeholder.PlaceholderAPI;

public class HookManager {

    private AdvancedTags plugin;

    public HookManager(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    public void initHooks(){
        if(plugin.isPlaceholderAPIEnabled()){
            new PlaceholderAPI(this).register();
            getPlugin().getLogger().info("Advanced Tags Has Hooked With PlaceholderAPI");
        }
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }
}
