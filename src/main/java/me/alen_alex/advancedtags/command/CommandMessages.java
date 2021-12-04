package me.alen_alex.advancedtags.command;

import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;

public enum CommandMessages {
    FILE_RELOADED(IridiumColorAPI.process("&aThe file/Tag has been reloaded &6Successfully")),
    MISSING_ARGS(IridiumColorAPI.process("&cThe command is not complete, &bRefer &6/adt help &cfor correct usage")),
    TAG_RELOAD_SUCCESS(IridiumColorAPI.process("&aThe tag has been reloaded successfully")),
    TAG_RELOAD_FAILED(IridiumColorAPI.process("&cThe tag reload has been failed, Is it still on db/config!")),
    RELOAD_FILE_UNKNOWN_ARGS(IridiumColorAPI.process("&cUnknown option. &fYou should choose one of &6Config,Msg,Tag,Menu."));

    private final String message;


    CommandMessages(String message) {
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
