package me.alen_alex.advancedtags.command;

import org.bukkit.entity.Player;

import java.util.List;

public interface Subcommand {

    boolean doRunOnConsole();

    boolean registerTabCompletion();

    boolean isTabCompletion();

    String getCommandPermission();

    String getCommandName();

    List<String> getAliases();

    String getCommandSyntax();

    String getSuggestionString();

    String getCommandDescription();

    void runMethod(Player player,String[] args);

}
