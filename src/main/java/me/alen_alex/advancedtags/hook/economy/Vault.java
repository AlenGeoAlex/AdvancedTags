package me.alen_alex.advancedtags.hook.economy;

import me.alen_alex.advancedtags.hook.HookManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class Vault implements EconomyWorker{

    private final HookManager handler;
    private final Economy economy;

    public Vault(HookManager handler, Economy economy) {
        this.handler = handler;
        this.economy = economy;
        handler.getPlugin().getLogger().info("Hooked with VaultAPI");
    }

    @Override
    public double getPlayerBalance(Player player) {
        return economy.getBalance(player);
    }

    @Override
    public boolean takeMoney(Player player, double amount) {
        return economy.withdrawPlayer(player,amount).transactionSuccess();
    }

    @Override
    public boolean giveMoney(Player player, double amount) {
        return economy.depositPlayer(player, amount).transactionSuccess();
    }

    @Override
    public boolean hasMoney(Player player, double amount) {
        return economy.has(player,amount);
    }
}
