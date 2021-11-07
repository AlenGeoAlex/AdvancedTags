package me.alen_alex.advancedtags.configurations.files;

import de.leonhard.storage.Yaml;
import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.configurations.ConfigurationFile;
import me.alen_alex.advancedtags.configurations.ConfigurationHandler;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MenuConfiguration extends ConfigurationFile {

    private Yaml menuConfig;

    private ConfigurationHandler handler;
    private String version;
    //MainMenu
    private String mainMenuName;
    private boolean mainMenuFillerEnabled,mainMenuAdminEnabled;
    private int mainMenuSize,myTagSize,tagShopSize,adminMenuSize;
    private ItemStack fillerItem;
    private ItemStack myTags,tagShop,adminMenu,closeButton;
    private List<String> myTagsLore,tagShopLore,adminMenuLore,closeButtonLore;
    private int myTagSlot,tagShopSlot,adminMenuSlot,closeButtonSlot;
    private String myTagDisplayName,tagShopDisplayName,adminMenuDisplayMenu,closeButtonDisplayName;
    private String adminMenuPermission;
    private final HashMap<ItemStack,List<Integer>> mainMenuStaticMaterials = new HashMap<ItemStack,List<Integer>>();

    public MenuConfiguration(ConfigurationHandler handler) {
        super(handler);
        this.handler = handler;
    }


    @Override
    public boolean init() {
        menuConfig = handler.getFileUtils().createFile(getInputStreamFromFile("menu.yml"),"menu.yml");
        if(menuConfig != null){
            menuConfig.setDefault("version",getPluginVersion());
            loadConfig();
            return true;
        }else return false;
    }

    @Override
    public void loadConfig() {
        version = menuConfig.getString("version");

        this.mainMenuName = IridiumColorAPI.process(this.menuConfig.getString("main-menu.name"));
        this.mainMenuSize = this.menuConfig.getInt("main-menu.size");
        this.mainMenuFillerEnabled = this.menuConfig.getBoolean("main-menu.filler.fill-empty-slots");
        this.fillerItem = processMaterial("main-menu.filler.filler");
        this.myTags = processMaterial("main-menu.items.myTags.material");
        this.myTagSize = menuConfig.getInt("main-menu.items.myTags.size");
        this.myTagsLore =IridiumColorAPI.process(menuConfig.getStringList("main-menu.items.myTags.lore"));
        this.myTagSlot = menuConfig.getInt("main-menu.items.myTags.slot");
        this.myTagDisplayName = IridiumColorAPI.process(menuConfig.getString("main-menu.items.myTags.display-name"));
        this.tagShop = processMaterial("main-menu.items.tagShop.material");
        this.tagShopLore = IridiumColorAPI.process(menuConfig.getStringList("main-menu.items.tagShop.lore"));
        this.tagShopSize = menuConfig.getInt("main-menu.items.tagShop.size");
        this.tagShopSlot = menuConfig.getInt("main-menu.items.tagShop.slot");
        this.tagShopDisplayName = IridiumColorAPI.process(menuConfig.getString("main-menu.items.tagShop.display-name"));
        this.mainMenuAdminEnabled = menuConfig.getBoolean("main-menu.items.admin-menu.enabled");
        this.adminMenu = processMaterial("main-menu.items.admin-menu.material");
        this.adminMenuLore = IridiumColorAPI.process(menuConfig.getStringList("main-menu.items.admin-menu.lore"));
        this.adminMenuSize = menuConfig.getInt("main-menu.items.admin-menu.size");
        this.adminMenuSlot = menuConfig.getInt("main-menu.items.admin-menu.slot");
        this.adminMenuDisplayMenu = IridiumColorAPI.process(menuConfig.getString("main-menu.items.admin-menu.display-name"));
        this.adminMenuPermission = menuConfig.getString("main-menu.items.admin-menu.permission");
        this.closeButton = processMaterial("main-menu.items.closeMenu.material");
        this.closeButtonLore = IridiumColorAPI.process(menuConfig.getStringList("main-menu.items.closeMenu.lore"));
        this.closeButtonDisplayName = IridiumColorAPI.process(menuConfig.getString("main-menu.items.closeMenu.display-name"));
        this.closeButtonSlot = menuConfig.getInt("main-menu.items.closeMenu.slot");
        this.menuConfig.singleLayerKeySet("main-menu.others").forEach((string) -> {
            final ItemStack material = processMaterial("main-menu.others."+string+".material");

            final List<Integer> slots = menuConfig.getStringList("main-menu.others."+string+".slots").stream().map(Integer::parseInt).collect(Collectors.toList());
            this.mainMenuStaticMaterials.put(material,slots);
        });

    }

    @Override
    public long reloadConfig() {
        return 0;
    }

    @Override
    public void saveConfig() {

    }

    @Override
    public Yaml getConfigFile() {
        return menuConfig;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public ConfigurationHandler getHandler() {
        return handler;
    }

    public String getMainMenuName() {
        return mainMenuName;
    }

    public boolean isMainMenuFillerEnabled() {
        return mainMenuFillerEnabled;
    }

    public int getMainMenuSize() {
        return mainMenuSize;
    }

    public ItemStack getFillerItem() {
        return fillerItem;
    }

    private ItemStack processMaterial(String configPath){
        System.out.println(configPath+" contains -- "+menuConfig.contains(configPath));
        if(menuConfig.contains(configPath))
        {
            if(menuConfig.getString(configPath).startsWith("base64")){
                return ItemStackUtils.getSkull(menuConfig.getString(configPath));
            }else {
                System.out.println(EnumUtils.isValidEnum(XMaterial.class, menuConfig.getString(configPath))+" -- "+menuConfig.getString(configPath));
                if (EnumUtils.isValidEnum(XMaterial.class, menuConfig.getString(configPath))) {
                    System.out.println(configPath+" is a valid Xmaterial");
                    return XMaterial.matchXMaterial(menuConfig.getString(configPath)).get().parseItem();
                } else {
                    return XMaterial.CRAFTING_TABLE.parseItem();
                }
            }
        }else return XMaterial.CRAFTING_TABLE.parseItem();
    }

    public boolean isMainMenuAdminEnabled() {
        return mainMenuAdminEnabled;
    }

    public ItemStack getMyTags() {
        return myTags;
    }

    public ItemStack getTagShop() {
        return tagShop;
    }

    public ItemStack getAdminMenu() {
        return adminMenu;
    }

    public int getMyTagSize() {
        return myTagSize;
    }

    public int getTagShopSize() {
        return tagShopSize;
    }

    public int getAdminMenuSize() {
        return adminMenuSize;
    }

    public List<String> getMyTagsLore() {
        return myTagsLore;
    }

    public List<String> getTagShopLore() {
        return tagShopLore;
    }

    public List<String> getAdminMenuLore() {
        return adminMenuLore;
    }

    public int getMyTagSlot() {
        return myTagSlot;
    }

    public int getTagShopSlot() {
        return tagShopSlot;
    }

    public int getAdminMenuSlot() {
        return adminMenuSlot;
    }

    public String getMyTagDisplayName() {
        return myTagDisplayName;
    }

    public String getTagShopDisplayName() {
        return tagShopDisplayName;
    }

    public String getAdminMenuDisplayMenu() {
        return adminMenuDisplayMenu;
    }

    private ArrayList<String> processLore(List<String> lore){
        ArrayList arrayList = new ArrayList<String>();

        if(!lore.isEmpty()){
            lore.forEach((line) -> {
                arrayList.add(IridiumColorAPI.process(line));
            });
        }

        return arrayList;
    }

    public HashMap<ItemStack, List<Integer>> getMainMenuStaticMaterials() {
        return mainMenuStaticMaterials;
    }

    public String getAdminMenuPermission() {
        return adminMenuPermission;
    }

    public ItemStack getCloseButton() {
        return closeButton;
    }

    public List<String> getCloseButtonLore() {
        return closeButtonLore;
    }

    public int getCloseButtonSlot() {
        return closeButtonSlot;
    }

    public String getCloseButtonDisplayName() {
        return closeButtonDisplayName;
    }
}
