package me.alen_alex.advancedtags.database;

import com.pepedevs.corelib.database.DatabaseType;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface StorageWorker {

    boolean init();

    boolean handleInitial();

    void disconnect();

    DatabaseType getType();

    CompletableFuture<Boolean> registerUser(UUID uuid);

    CompletableFuture<Boolean> doUserExist(UUID uuid);

    CompletableFuture<ATPlayer> loadPlayer(UUID uuid);

    CompletableFuture<Boolean> savePlayerTag(ATPlayer playerObj);

    List<Tag> loadBatchTags();

    boolean setCurrentTag(Tag tag);

    boolean insertGlobalTag(Tag tag);

    boolean updateGlobalTag(Tag tag);

    boolean removeGlobalTag(String tagName);

    Tag fetchTag(String tagName);

    CompletableFuture<Boolean> clearPlayerTags(UUID uuid);
}
