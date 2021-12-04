package me.alen_alex.advancedtags.database.methods;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import me.Abhigya.core.database.DatabaseType;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import me.alen_alex.advancedtags.utils.MongoAdapter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MongoDB implements StorageWorker {

    private me.Abhigya.core.database.mongo.MongoDB databaseEngine;
    private final StorageHandler handler;
    private DB mongoDatabase;
    private final MongoAdapter adapter;

    public MongoDB(StorageHandler handler) {
        this.handler = handler;
        adapter = new MongoAdapter(this);
    }

    @Override
    public boolean init() {
        databaseEngine = new me.Abhigya.core.database.mongo.MongoDB(
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongoHost(),
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongoPort(),
                handler.getPlugin().getConfigurationHandler().getPluginConfig().getMongodatabase()
        );
        return connect();
    }

    @Override
    public boolean handleInitial() {
        mongoDatabase = databaseEngine.getDb();
        mongoDatabase.createCollection(this.handler.getSqlPlayerDataTable(),null);
        if(this.handler.getPlugin().getConfigurationHandler().getPluginConfig().isGlobalEnabled()) {
            mongoDatabase.createCollection(this.handler.getSqlGlobalTagTable(),null);
        }
        return true;
    }

    @Override
    public void disconnect() {
        databaseEngine.disconnect();
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.MongoDB;
    }

    @Override
    public CompletableFuture<Boolean> registerUser(UUID player) {
        CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            mongoDatabase.getCollection(this.handler.getSqlPlayerDataTable()).insert(adapter.UUIDToDBObject(player));
            future.complete(true);
        }catch (Exception e){
            e.printStackTrace();
            future.complete(false);
        }
        return future;
    }

    @Override
    public CompletableFuture<Boolean> doUserExist(UUID uuid) {
        CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            final DBCursor queryResult = mongoDatabase.getCollection(this.handler.getSqlPlayerDataTable()).find(adapter.buildUUIDQuery(uuid));
            future.complete(queryResult.hasNext());
        }catch (Exception e){
            e.printStackTrace();
            future.complete(false);
        }
        return future;
    }

    @Override
    public CompletableFuture<ATPlayer> loadPlayer(UUID uuid) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> savePlayerTag(ATPlayer playerObj) {
        CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            mongoDatabase.getCollection(this.handler.getSqlPlayerDataTable()).update(adapter.buildUUIDQuery(playerObj.getPlayerID()),adapter.toObject(playerObj));
        }catch (Exception e){
            e.printStackTrace();
            future.complete(false);
        }
        return future;
    }

    @Override
    public List<Tag> loadBatchTags() {
        return null;
    }

    @Override
    public boolean setCurrentTag(Tag tag) {
        return true;
    }

    @Override
    public boolean insertGlobalTag(Tag tag) {
        return false;
    }

    @Override
    public boolean updateGlobalTag(Tag tag) {
        return false;
    }

    @Override
    public boolean removeGlobalTag(String tagName) {
        return false;
    }

    @Override
    public Tag fetchTag(String tagName) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> clearPlayerTags(UUID uuid) {
        return null;
    }

    private boolean connect(){
        databaseEngine.connect();
        return databaseEngine.isConnected();
    }

    public StorageHandler getHandler() {
        return handler;
    }
}
