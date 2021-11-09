package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.AdvancedTags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test3  implements CommandExecutor {

    private AdvancedTags plugin;

    public Test3(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        final Player player = (Player) sender;

        plugin.getGuiHandler().getTagShop().openMenu(player);

        return true;
    }
}