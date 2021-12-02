package me.alen_alex.advancedtags.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.alen_alex.advancedtags.database.methods.MongoDB;
import me.alen_alex.advancedtags.object.ATPlayer;
import me.alen_alex.advancedtags.object.Tag;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MongoAdapter {

    private final MongoDB databasePackage;

    public MongoAdapter(MongoDB databasePackage) {
        this.databasePackage = databasePackage;
    }

    public DBObject UUIDToDBObject(@NotNull UUID playerID){
        return new BasicDBObject("uuid",playerID.toString())
                .append("current",null)
                .append("tags",null);
    }

    public DBObject buildUUIDQuery(@NotNull UUID playerUUID){
        return new BasicDBObject("uuid",playerUUID.toString());
    }

    public  DBObject toObject(@NotNull Tag tag){
        return null;
    }

    public  DBObject toObject(@NotNull ATPlayer player){
        BasicDBObject object = new BasicDBObject("uuid",player.getPlayerID());
        if(player.doPlayerHaveTag())
            object.append("",player.getPlayerCurrentTag().getName());

        final String tagString = databasePackage.getHandler().concatTags(player.getPlayerUnlockedTagNames());

        if(StringUtils.isBlank(tagString)) object.append("tags",null);
        else object.append("tags",tagString);


        return object;
    }

    public  ATPlayer getPlayerFromObject(DBObject object){
        return null;
    }

    public  Tag getTagFromObject(DBObject object){
        return null;
    }

}
