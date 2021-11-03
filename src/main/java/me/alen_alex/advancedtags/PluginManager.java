package me.alen_alex.advancedtags;

import me.alen_alex.advancedtags.object.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class PluginManager {

    private final AdvancedTags plugin;
    private final HashMap<String, Tag> tagCache;

    public PluginManager(AdvancedTags plugin) {
        this.plugin = plugin;
        tagCache = new HashMap<String,Tag>();
    }

    public void insertTag(@NotNull String tagName, @NotNull Tag tagObject){
        if(containsTag(tagName)){
            if(plugin.getConfigurationHandler().getPluginConfig().isGlobalPriority())
            {
                if(tagObject.isGlobal()){
                    this.tagCache.remove(tagName);
                    this.tagCache.put(tagName,tagObject);
                }
            }else {
                if(!tagObject.isGlobal()) {
                    this.tagCache.remove(tagName);
                    this.tagCache.put(tagName,tagObject);
                }
            }
        }else
            this.tagCache.put(tagName,tagObject);
    }

    public boolean containsTag(@NotNull String tagName){
        return this.tagCache.containsKey(tagName);
    }

    public boolean containsTag(@NotNull Tag tagObj){
        return this.tagCache.containsValue(tagObj);
    }

    public Tag getTagFromCache(@NotNull String tagName){
        return this.tagCache.get(tagName);
    }

    public void insertTagAsBatch(@NotNull List<Tag> tags){
        tags.forEach((tag) -> this.insertTag(tag.getName(),tag));
    }

}
