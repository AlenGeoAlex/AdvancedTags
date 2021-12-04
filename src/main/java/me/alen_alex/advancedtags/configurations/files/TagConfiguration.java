package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.object.Tag;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;

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
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
        final List<Tag> tagBatch = new ArrayList<Tag>();
        for(String tagName : tagConfig.singleLayerKeySet()){
            if(tagName.equalsIgnoreCase("version"))
                continue;

            if(StringUtils.isBlank(tagConfig.getString(tagName+".display-name"))){
                this.handler.getPlugin().getLogger().warning("display-name for the tag "+tagName+" seems to be empty, Skipping it!");
                continue;
            }

            Tag tag = new Tag(handler.getPlugin(),tagName,tagConfig.getString(tagName+".display-name"));
            tag.setGlobal(false);
            if(tagConfig.getBoolean(tagName+".permission.required")){
                if(StringUtils.isBlank(tagConfig.getString(tagName+".permission.node"))){
                    this.handler.getPlugin().getLogger().warning("Permission has been required as true, but node is not specified for the tag in "+tagName+". Permission will be skipped!");
                }else {
                    tag.setPermissionRequired(true);
                    tag.setPermission(tagConfig.getString(tagName+".permission.node").replaceAll("%tag-name%",tagName));
                }
            }

            if(tagConfig.contains(tagName+".dynamic")) {
                if (this.handler.getPlugin().isPlaceholderAPIEnabled()) {
                    tag.setDynamicTag(tagConfig.getBoolean(tagName + ".dynamic"));
                } else {
                    tag.setDynamicTag(false);
                    this.handler.getPlugin().getLogger().warning("PlaceholderAPI has been not enabled, tag " + tagName + " has been set as not dynamic");
                }
            } else tag.setDynamicTag(false);

            if(tagConfig.contains(tagName+".lore"))
                tag.setLore(tagConfig.getStringList(tagName+".lore"));

            if(tagConfig.contains(tagName+".material"))
            {
                if(tagConfig.getString(tagName+".material").startsWith("base64")){
                    final String texture = tagConfig.getString(tagName+".material").substring(7);
                    tag.setMenuItem(ItemStackUtils.getSkull(texture));
                }else {
                    if (EnumUtils.isValidEnum(XMaterial.class, tagConfig.getString(tagName + ".material"))) {
                        tag.setMenuItem(tagConfig.getEnum(tagName + ".material", XMaterial.class));
                    } else {
                        this.handler.getPlugin().getLogger().warning("Specified material name for " + tagName + " is not a valid name. Setting to default " + tag.getMenuItem().getType().name());
                    }
                }
            }

            if(tagConfig.contains(tagName+".money")){
                tag.setMoney(tagConfig.getFloat(tagName+".money"));
            }

            if(tag != null){
                tagBatch.add(tag);
            }
        }
        getHandler().getPlugin().getPluginManager().insertTagAsBatch(tagBatch);
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
                loadable  = new Tag(handler.getPlugin(),name, tagConfig.getString("display-name"), tagConfig.getBoolean("dynamic"), tagConfig.getStringList("lore"), tagConfig.getString("material"));
            else
                loadable  = new Tag(handler.getPlugin(),name, tagConfig.getString("display-name"),tagConfig.getBoolean("permission.required"),tagConfig.getString("permission.node") ,tagConfig.getBoolean("dynamic"), tagConfig.getStringList("lore"), tagConfig.getString("material"));
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
