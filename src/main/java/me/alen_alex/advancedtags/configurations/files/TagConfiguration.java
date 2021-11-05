package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.object.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagConfiguration extends ConfigurationFile {

    private Yaml tagConfig;

    private ConfigurationHandler handler;
    private String version;

    public TagConfiguration(ConfigurationHandler handler) {
        super(handler);
    }

    @Override
    public boolean init() {
        tagConfig = handler.getFileUtils().createFile(getInputStreamFromFile("tags.yml"),"tags.yml","language");
        if(tagConfig != null){
            tagConfig.setDefault("version",getPluginVersion());
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
         final List<Tag> batchTag = new ArrayList<Tag>();
         for(String tagName : tagConfig.singleLayerKeySet()){
             tagConfig.setPathPrefix(tagName);
             //Redo with setters, check vaulthook, placeholderhooks
             Tag loadable;
             if(tagConfig.contains("permission") && !tagConfig.getBoolean("permission.required"))
                loadable  = new Tag(tagName,
                        tagConfig.getString("display-name"),
                        tagConfig.getBoolean("dynamic"),
                        tagConfig.getStringList("lore"),
                        tagConfig.getString("material"));
             else
                 loadable  = new Tag(tagName,
                         tagConfig.getString("display-name"),
                         tagConfig.getBoolean("permission.required"),
                         tagConfig.getString("permission.node") ,
                         tagConfig.getBoolean("dynamic"),
                         tagConfig.getStringList("lore"),
                         tagConfig.getString("material"));
             if(loadable == null){
                 getHandler().getPlugin().getLogger().warning("Somehow, the object creation for tag "+tagName+" has became null!, Skipping");
             }else{
                 loadable.setGlobal(false);
                 batchTag.add(loadable);
             }
         }
         if(!batchTag.isEmpty()){
             getHandler().getPlugin().getPluginManager().insertTagAsBatch(batchTag);
             getHandler().getPlugin().getLogger().info("Loaded "+batchTag.size()+" local tags to cache!");
             batchTag.clear();
         }
    }



    @Override
    public long reloadConfig() {
        final long startTime = System.currentTimeMillis();
            init();
        final long endTime = System.currentTimeMillis();
        return (endTime-startTime);    }

    @Override
    public void saveConfig() {

    }

    @Override
    public Yaml getConfigFile() {
        return this.tagConfig;
    }

    @Override
    public String getVersion() {
        return null;
    }

    public Tag fetchTag(String name){
        if(this.tagConfig.contains(name)){
            tagConfig.setPathPrefix(name);
            Tag loadable;
            if(tagConfig.contains("permission") && !tagConfig.getBoolean("permission.required"))
                loadable  = new Tag(name, tagConfig.getString("display-name"), tagConfig.getBoolean("dynamic"), tagConfig.getStringList("lore"), tagConfig.getString("material"));
            else
                loadable  = new Tag(name, tagConfig.getString("display-name"),tagConfig.getBoolean("permission.required"),tagConfig.getString("permission.node") ,tagConfig.getBoolean("dynamic"), tagConfig.getStringList("lore"), tagConfig.getString("material"));
            if(loadable == null)
                getHandler().getPlugin().getLogger().warning("Somehow, the object creation for tag "+name+" has became null!, Skipping");
            return loadable;
        }else
            return null;
    }

    public boolean stillContains(String tagName){
        return this.tagConfig.contains(tagName);
    }
}
