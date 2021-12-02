package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLConsumer;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class H2 implements StorageWorker {

    private SQLDatabase databaseEngine;
    private String dataPath;
    private StorageHandler handler;

    public H2(StorageHandler handler) {
        this.handler = handler;
        this.dataPath = handler.getPlugin().getDataFolder().getPath()+ File.separator+"storage"+File.separator+"database.db";
    }

    @Override
    public boolean init() {
        try {
            final File databaseFile = new File(dataPath);
            databaseEngine = new me.Abhigya.core.database.sql.h2.H2(databaseFile, true);
            databaseEngine.connect();
            return databaseEngine.isConnected();
        }catch (Exception e) {
            handler.getPlugin().getLogger().severe("Unable to connect to H2 Database");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean handleInitial() {
        try {
            final String QUERY_CREATE_PLAYERDATA = "CREATE TABLE IF NOT EXISTS "+this.handler.getSqlPlayerDataTable()+" (`id` IDENTITY NOT NULL PRIMARY KEY, `uuid` VARCHAR(50) NOT NULL,`current` VARCHAR(40) ,`tags` TEXT);";
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            this.databaseEngine.updateAsync(QUERY_CREATE_PLAYERDATA);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void disconnect() {
        try {
            this.databaseEngine.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public DatabaseType getType() {
        return DatabaseType.H2;
    }

    @Override
    public CompletableFuture<Boolean> registerUser(UUID player) {
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        System.out.println(databaseEngine.isConnected()+ "registerUSer");
        try {
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            System.out.println(databaseEngine.isConnected()+ "registerUSer2");
            this.databaseEngine.updateAsync("INSERT INTO `" + handler.getSqlPlayerDataTable() + "` (`uuid`) VALUES ('" + player.toString() + "');").thenAccept(b -> future.complete(true));
        }catch (Exception e){
            this.handler.getPlugin().getLogger().warning("Unable to register playerdata for "+player);
            future.complete(false);
        }

        return future;
    }

    @Override
    public CompletableFuture<Boolean> doUserExist(UUID uuid) {
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            System.out.println(databaseEngine.isConnected()+ "do userExist");
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            System.out.println(databaseEngine.isConnected()+ "do userExist2");

            this.databaseEngine.queryAsync("SELECT 1 FROM `" + this.handler.getSqlPlayerDataTable() + "` WHERE `uuid` = '" + uuid.toString() + "';", new SQLConsumer<ResultSet>() {
                @Override
                public void accept(ResultSet resultSet) throws SQLException {
                    if(resultSet == null)
                        future.complete(false);

                    if(resultSet.next())
                        future.complete(true);
                    else
                        future.complete(false);

                }
            });
        }catch (Exception e){
            this.handler.getPlugin().getLogger().warning("Unable to load playerdata for "+uuid);
            future.complete(false);
        }
        return future;

    }
    @Override
    public CompletableFuture<ATPlayer> loadPlayer(UUID uuid) {

        final CompletableFuture<ATPlayer> future = new CompletableFuture<ATPlayer>();
        try {
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            this.databaseEngine.queryAsync("SELECT * FROM `" + this.handler.getSqlPlayerDataTable() + "` WHERE `uuid` = '" + uuid.toString() + "';", new SQLConsumer<ResultSet>() {
                @Override
                public void accept(ResultSet resultSet) throws SQLException {
                    if(resultSet == null)
                        future.complete(null);

                    if(resultSet.next()){
                        future.complete(new ATPlayer(handler.getPlugin(),
                                UUID.fromString(resultSet.getString("uuid")),
                                resultSet.getString("current"),
                                handler.fetchAndSplitTags(resultSet.getString("tags"))));
                    }
                }
            });
        }catch (Exception e){
            this.handler.getPlugin().getLogger().warning("Unable to load playerdata for "+uuid);
            future.complete(null);
        }

        return future;

    }

    @Override
    public CompletableFuture<Boolean> savePlayerTag(ATPlayer playerObj) {
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            PreparedStatement ps = this.databaseEngine.getConnection().prepareStatement("UPDATE "+handler.getSqlPlayerDataTable()+" SET `current` = ?,`tags` = ? WHERE `uuid` = ?;");
            if(playerObj.doPlayerHaveTag())
                ps.setString(1, playerObj.getPlayerCurrentTag().getName());
            final String tagString = handler.concatTags(playerObj.getPlayerUnlockedTagNames());
            if(StringUtils.isBlank(tagString)) ps.setNull(2, Types.NULL);
            else ps.setString(2, tagString);
            ps.setString(3, playerObj.getPlayerID().toString());
            this.databaseEngine.updateAsync(ps).thenAccept((val -> {
                if(val == 1) future.complete(true);
                else future.complete(false);
            }));
        }catch (Exception e){
            future.complete(false);
            e.printStackTrace();
        }
        return future;
    }

    @Override
    public List<Tag> loadBatchTags() {
        return null;
    }

    @Override
    public boolean setCurrentTag(Tag tag) {
        return false;
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
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            if(!databaseEngine.isConnected())
                this.databaseEngine.connect();
            PreparedStatement ps = this.databaseEngine.getConnection().prepareStatement("UPDATE "+handler.getSqlPlayerDataTable()+" SET `current` = ?,`tags` = ? WHERE `uuid` = ?;");
            ps.setNull(1,Types.NULL);
            ps.setNull(2,Types.NULL);
            ps.setString(3,uuid.toString());
            this.databaseEngine.updateAsync(ps);
            future.complete(true);
        }catch (Exception e){
            future.complete(false);
            e.printStackTrace();
        }
        return future;
    }
}
