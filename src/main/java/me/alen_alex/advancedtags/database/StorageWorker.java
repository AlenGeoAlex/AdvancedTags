package me.alen_alex.advancedtags.database;

public interface StorageWorker {

    boolean init();

    boolean handleIntial();

    String getDatabaseType();
}
