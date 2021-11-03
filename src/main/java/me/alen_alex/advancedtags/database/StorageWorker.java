package me.alen_alex.advancedtags.database;

import me.Abhigya.core.database.DatabaseType;

public interface StorageWorker {

    boolean init();

    boolean handleInitial();

    void disconnect();

    DatabaseType getType();
}
