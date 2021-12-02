package me.alen_alex.advancedtags;

import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.packet.packetevents.utils.gameprofile.GameProfileUtil;
import me.Abhigya.core.util.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    private final AdvancedTags plugin;

    public TestCommand(AdvancedTags plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        return true;
    }


}
