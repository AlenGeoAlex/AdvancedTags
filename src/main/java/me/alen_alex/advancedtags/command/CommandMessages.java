package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;

public enum CommandMessages {
    FILE_RELOADED(IridiumColorAPI.process("&eThe file has been reloaded &6Successfully")),
    RELOAD_FILE_UNKNOWN_ARGS(IridiumColorAPI.process("&cUnknown option. &fYou should choose one of &6Config,Msg,Tag,Menu."));


    private String message;


    CommandMessages(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
