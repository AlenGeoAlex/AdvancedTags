package me.alen_alex.advancedtags.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface CommandWorkerImpl {

    /**
     * Execute Command As A Command Sender
     * Can be used to execute as console
     * @param sender CommandSender or Console
     * @param args Args passed
     */
    void executeCommand(CommandSender sender,String[] args);

    /**
     * Execute command as a Player
     * Can be used to execute as player
     * @param player Player
     * @param args Args passed
     */

    void executeCommand(Player player,String[] args);

    /**
     * Permission for the command, return null if no permission is required
     * @return String [Permission String]
     */
    @NotNull
    String getCommandPermission();

    /**
     * Name of the command that will be used to call the command and register it
     * @return String [The first world of the subcommand.]
     */
    @NotNull
    String getCommandName();

    /**
     * List of Aliases the command can be called
     * @return List<String> of aliases, do not return null
     */
    List<String> getCommandAliases();

    /**
     * Get the description of the command
     * @return String [A Sentence of what the command does]
     */
    @NotNull
    String getCommandDescription();

    /**
     * String that needs to be printed on Help Message
     * @return String [Message to be printed on HelpMessage]
     */
    @NotNull
    String getCommandHelpSyntax();

    /**
     * String that need to be shows as suggestion when clicked on an help message
     * Can contains optional <> as this and required as []
     * @return String [Message that need to be shown as suggestion when clicking the command!]
     */
    @NotNull
    String getCommandHelpSyntaxSuggestion();

    /**
     * Can the command be run from console too
     *
     * @return True/False Whether the command can be run from console too
     */
    default boolean isConsoleCommand() {
        return false;
    }

    /**
     * Do Register Tab Completeion
     * @return True/False Register Tab Completion
     */
    default boolean registerTabCompleter(){
        return true;
    }

    /**
     * Execute the tab completeion
     */
    List<String> completeTab(String[] args);


}
