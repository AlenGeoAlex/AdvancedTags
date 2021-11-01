package me.alen_alex.advancedtags.database.methods;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.database.Storage;
import me.alen_alex.advancedtags.database.StorageWorker;

public class SQL extends Storage implements StorageWorker {

    public SQL(AdvancedTags plugins) {
        super(plugins);
    }

    @Override
    public boolean init() {
        return false;
    }
}
