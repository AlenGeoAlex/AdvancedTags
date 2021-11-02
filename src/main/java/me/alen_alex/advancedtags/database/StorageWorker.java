package me.alen_alex.advancedtags.database;

public interface StorageWorker {

    boolean init();

    boolean handleInitial();

    String getDatabaseType();

    void disconnect();
}
