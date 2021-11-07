package me.alen_alex.advancedtags.database.methods;

import me.Abhigya.core.database.DatabaseType;
import me.Abhigya.core.database.sql.SQLConsumer;
import me.Abhigya.core.database.sql.SQLDatabase;
import me.Abhigya.core.database.sql.hikaricp.HikariClientBuilder;
import me.alen_alex.advancedtags.database.StorageHandler;
import me.alen_alex.advancedtags.database.StorageWorker;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQL implements StorageWorker {

    private SQLDatabase databaseEngine;
    private final StorageHandler handler;

    public MySQL(StorageHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean init() {
        try {
            databaseEngine =  new HikariClientBuilder(handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlHost(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPort(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlDatabse(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlUsername(),
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().getSqlPassword(),
                    true,
                    handler.getPlugin().getConfigurationHandler().getPluginConfig().isSqlUseSSL()).build();
            databaseEngine.connect();
            return databaseEngine.isConnected();
        }catch (Exception e){
            handler.getPlugin().getLogger().severe("Unable to connect to MySQL (HikkariCP)");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean handleInitial() {
        try {
            final String QUERY_CREATE_PLAYERDATA = "CREATE TABLE IF NOT EXISTS "+this.handler.getSqlPlayerDataTable()+" (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `uuid` VARCHAR(50) NOT NULL,`current` VARCHAR(40) ,`tags` TEXT);";
            final String QUERY_CREATE_GLOBALTAG = "CREATE TABLE IF NOT EXISTS "+this.handler.getSqlGlobalTagTable()+" (`id` int NOT NULL AUTO_INCREMENT PRIMARY KEY, `tagName` VARCHAR(50) NOT NULL, `tagDisplay` VARCHAR(50) NOT NULL, `permission` VARCHAR(50), `lore` TEXT, `dynamic` BIT, `material` VARCHAR(70), `money` DOUBLE(16,4));";
            this.databaseEngine.executeAsync(QUERY_CREATE_PLAYERDATA);
            if(this.handler.getPlugin().getConfigurationHandler().getPluginConfig().isGlobalEnabled()){
                if(this.handler.isUsingOnlineDatabase()){
                    this.databaseEngine.executeAsync(QUERY_CREATE_GLOBALTAG);
                    this.handler.getPlugin().getLogger().info("Global Database has been created!");
                }else this.handler.getPlugin().getLogger().severe("Global database system is enabled, But is using an offline method (Local Storage) to save the data");
            }
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
        return DatabaseType.MYSQL;
    }

    @Override
    public CompletableFuture<Boolean> registerUser(UUID uuid) {
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();

        try {
            this.databaseEngine.executeAsync("INSERT INTO `" + handler.getSqlPlayerDataTable() + "` (`uuid`) VALUES ('" + uuid.toString() + "');").thenAccept(b -> future.complete(b));
        }catch (Exception e){
            this.handler.getPlugin().getLogger().warning("Unable to register playerdata for "+uuid);
            future.complete(false);
        }

        return future;
    }

    @Override
    public CompletableFuture<Boolean> doUserExist(UUID uuid){
        final CompletableFuture<Boolean> future = new CompletableFuture<Boolean>();
        try {
            this.databaseEngine.queryAsync("SELECT * FROM `" + this.handler.getSqlPlayerDataTable() + "` WHERE `uuid` = '" + uuid.toString() + "';", new SQLConsumer<ResultSet>() {
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
    public CompletableFuture<ATPlayer> loadPlayer(UUID uuid){
        final CompletableFuture<ATPlayer> future = new CompletableFuture<ATPlayer>();
        try {
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
        final CompletableFuture future = new CompletableFuture<Boolean>();
        try {
            //System.out.println("UPDATE `"+this.handler.getSqlPlayerDataTable()+"` SET `current` =  '"+playerObj.getTagOnDatabase()+"', `tags` = '"+this.handler.concatTags(playerObj.getPlayerUnlockedTags())+"';");
            PreparedStatement ps = this.databaseEngine.getConnection().prepareStatement("UPDATE ? SET `current` = ?,`tags` =? WHERE `uuid` = ?;");
            ps.setString(1, handler.getSqlPlayerDataTable());
            ps.setString(2,playerObj.getTagOnDatabase());
            ps.setString(3, handler.concatTags(playerObj.getPlayerUnlockedTags()));
            ps.setString(4,playerObj.getPlayerID().toString());
            //TODO
            //this.databaseEngine.executeAsync(ps).thenAccept(s -> future.complete(s));
            ps.close();
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
    public boolean setCurrentTag(String name) {
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
}
