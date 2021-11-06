package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.object.Tag;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class TagConfiguration extends ConfigurationFile {

    private Yaml tagConfig;

    private ConfigurationHandler handler;
    private String version;

    public TagConfiguration(ConfigurationHandler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    public boolean init() {
        tagConfig = handler.getFileUtils().createFile(getInputStreamFromFile("tags.yml"),"tags.yml");
        if(tagConfig != null){
            tagConfig.setDefault("version",getPluginVersion());
            //loadConfig();
            b();
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

    public void b(){
        final List<Tag> tagBatch = new ArrayList<Tag>();
        for(String tagName : tagConfig.singleLayerKeySet()){
            if(tagName.equalsIgnoreCase("version"))
                continue;

            if(StringUtils.isBlank(tagConfig.getString(tagName+".display-name"))){
                this.handler.getPlugin().getLogger().warning("display-name for the tag "+tagName+" seems to be empty, Skipping it!");
                continue;
            }

            Tag tag = new Tag(tagName,tagConfig.getString(tagName+".display-name"));
            if(tagConfig.getBoolean(tagName+".permission.required")){
                if(StringUtils.isBlank(tagConfig.getString(tagName+".permission.node"))){
                    this.handler.getPlugin().getLogger().warning("Permission has been required as true, but node is not specified for the tag in "+tagName+". Permission will be skipped!");
                }else {
                    tag.setPermissionRequired(true);
                    tag.setPermission(tagConfig.getString(tagName+".permission.node").replaceAll("%tag-name%",tagName));
                }
            }

            if(tagConfig.contains(tagName+".dynamic"))
                if(this.handler.getPlugin().isPlaceholderAPIEnabled()) {
                    tag.setDynamicTag(tagConfig.getBoolean(tagName + ".dynamic"));
                }else{
                    tag.setDynamicTag(false);
                    this.handler.getPlugin().getLogger().warning("PlaceholderAPI has been not enabled, tag "+tagName+" has been set as not dynamic");
                }
            else tag.setDynamicTag(false);

            if(tagConfig.contains(tagName+".lore"))
                tag.setLore(tagConfig.getStringList(tagName+".lore"));

            if(tagConfig.contains(tagName+".material"))
            {
                System.out.println(tagConfig.getString(tagName+".material"));
                System.out.println(tagConfig.getEnum(tagName+".material",Material.class));
                 if(EnumUtils.isValidEnum(Material.class,tagConfig.getString(tagName+".material"))){
                     tag.setMenuMaterial(XMaterial.matchXMaterial(tagConfig.getEnum(tagName+".material",Material.class)));
                 }else{
                    this.handler.getPlugin().getLogger().warning("Specified material name for "+tagName+" is not a valid name. Setting to default "+tag.getMenuMaterial().name());
                 }
            }

            if(tagConfig.contains(tagName+".money")){
                if(this.handler.getPlugin().isVaultEnabled()){
                    tag.setMoney(tagConfig.getFloat(tagName+".money"));
                }else{
                    tag.setMoney(0.0F);
                    this.handler.getPlugin().getLogger().warning("VaultAPI has been not enabled, tag "+tagName+" has been set as not vault");
                }
            }

            if(tag != null){
                tagBatch.add(tag);
                System.out.println(tag.getDisplayTag());
                System.out.println(tag.getName());
                System.out.println(tag.isDynamicTag());
                System.out.println(tag.isPermissionRequired());
            }
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
