package me.alen_alex.advancedtags.hook;


import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.hook.economy.EconomyWorker;
import me.alen_alex.advancedtags.hook.economy.Vault;
import me.alen_alex.advancedtags.hook.placeholder.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class HookManager {

    public EconomyWorker getEconomyWorker() {
        return economyWorker;
    }

    public enum EconomySelected {
        VAULT,
        NONE
    }

    private final AdvancedTags plugin;
    private EconomyWorker economyWorker;
    private EconomySelected currentEcoManager = EconomySelected.NONE;

    public HookManager(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    public void initHooks(){
        if(plugin.isPlaceholderAPIEnabled()){
            new PlaceholderAPI(this).register();
            getPlugin().getLogger().info("Advanced Tags Has Hooked With PlaceholderAPI");
        }

        if(currentEcoManager == EconomySelected.NONE && plugin.getServer().getPluginManager().isPluginEnabled("Vault")  ){
            RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                economyWorker = new Vault(this, rsp.getProvider());
                currentEcoManager = EconomySelected.VAULT;
            }
        }



    }

    public EconomySelected getCurrentEcoManager() {
        return currentEcoManager;
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }
}
