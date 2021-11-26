package me.alen_alex.advancedtags.hook.economy;


import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface EconomyWorker {

    double getPlayerBalance(Player player);

    boolean takeMoney(Player player,double amount);

    boolean giveMoney(Player player,double amount);

    boolean hasMoney(Player player,double amount);

}
