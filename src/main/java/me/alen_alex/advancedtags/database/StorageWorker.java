package me.alen_alex.advancedtags.database;

import me.Abhigya.core.database.DatabaseType;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import org.bukkit.entity.Player;

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

    List<Tag> loadBatchTags();

    boolean setCurrentTag(String name);

    boolean insertGlobalTag(Tag tag);

    boolean updateGlobalTag(Tag tag);

    boolean removeGlobalTag(String tagName);

    Tag fetchTag(String tagName);
}
