package me.alen_alex.advancedtags.database.methods;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.SQLStorage;
import me.alen_alex.advancedtags.database.StorageWorker;

public class SQL extends SQLStorage implements StorageWorker {

    public SQL(AdvancedTags plugins) {
        super(plugins);
    }

    @Override
    public boolean init() {
        return connect();
    }

    @Override
    public boolean handleIntial() {
        return false;
    }

    @Override
    public String getDatabaseType() {
        return getType().name();
    }


}
