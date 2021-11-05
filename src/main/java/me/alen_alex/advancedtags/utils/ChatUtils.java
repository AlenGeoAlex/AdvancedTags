package me.alen_alex.advancedtags.utils;

import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class ChatUtils {

    private String prefix = "";
    private Plugin corePackage;

    public ChatUtils(Plugin corePackage) {
        this.corePackage = corePackage;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        prefix = IridiumColorAPI.process(this.prefix);
    }

    public String parseColorCodes(@NotNull String message) {
        if (StringUtils.isBlank(message))
            return "";
        return IridiumColorAPI.process(message);
    }

    private String formatMessage(String message) {
        if (StringUtils.isBlank(prefix))
            return parseColorCodes(message);
        else
            return (this.prefix + " " + parseColorCodes(message));
    }

    private String formatSimpleMessage(String message){
        if (StringUtils.isBlank(prefix))
            return (message);
        else
            return (this.prefix + " " + message);
    }

    public void sendMessage(@NotNull Player player, String message) {
        if (StringUtils.isBlank(message))
            return;
        player.sendMessage(formatMessage(message));
    }

    public void sendSimpleMessage(@NotNull Player player,String message){
        if(StringUtils.isBlank(message))
            return;

        player.sendMessage(formatSimpleMessage(message));
    }

    public void sendMessage(@NotNull CommandSender sender, String message) {
        if (StringUtils.isBlank(message))
            return;

        sender.sendMessage(formatMessage(message));
    }

    public void sendJsonSuggestion(@NotNull Player player, String message, @NotNull String suggestionCommand, String hoverText) {
        if (StringUtils.isBlank(message))
            return;

        final String parsedMessage = formatMessage(message);
        final String parsedHoverText = parseColorCodes(hoverText);
        final TextComponent tc = new TextComponent();

        tc.setText(parsedMessage);
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestionCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(parsedHoverText).create()));
        player.spigot().sendMessage(tc);
    }

    public void sendJsonCommand(@NotNull Player player, String message, @NotNull String commandWithoutSlash, String hoverText) {
        if (StringUtils.isBlank(message))
            return;

        final String parsedMessage = formatMessage(message);
        final String parsedHoverText = parseColorCodes(hoverText);
        final String parsedCommand = "/" + commandWithoutSlash;
        final TextComponent tc = new TextComponent();

        tc.setText(parsedMessage);
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, parsedCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(parsedHoverText).create()));
        player.spigot().sendMessage(tc);
    }

    public void sendJsonBroadcastCommand(String message, @NotNull String commandWithoutSlash, String hoverText) {
        corePackage.getServer().getOnlinePlayers().forEach((player -> {
            sendJsonCommand(player, message, commandWithoutSlash, hoverText);
        }));
    }

    public String stripColorCodes(@NotNull String message) {
        return ChatColor.stripColor(message);
    }
}

