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

    //TagShop
    private String tagShopName;
    private String tagShopCloseName;
    private ItemStack tagShopCloseItem;
    private List<String> tagShopCloseLore;
    private int tagShopCloseSlot;
    private String tagShopGoBackName,tagShopGoNextName,tagShopGoMainMenuName;
    private ItemStack tagShopGoBackItem,tagShopGoNextItem,tagShopMainMenuItem;
    private List<String> tagShopGoBackLore,tagShopGoNextLore,tagShopMainMenuLore;
    private int tagShopGoBackSlot,tagShopGoNextSlot,tagShopGoMainMenuSlot;
    private final HashMap<ItemStack,List<Integer>> tagMenuStaticMaterials = new HashMap<ItemStack,List<Integer>>();
    private boolean closeTagShopAfterEachSelection;

    //MyTag
    private String myTagName;
    private String myTagCloseItemName,myTagGoBackName,myTagGoNextName,myTagMainMenuName;
    private ItemStack myTagCloseItem,myTagGoBackItem,myTagGoNextItem,myTagMainMenuItem;
    private List<String> myTagCloseLore,myTagGoBackLore,myTagGoNextLore,myTagMainMenuLore;
    private int myTagCloseSlot,myTagGoBackSlot,myTagGoNextSlot,myTagMainMenuSlot;
    private final HashMap<ItemStack,List<Integer>> myTagStaticMaterials = new HashMap<ItemStack,List<Integer>>();


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
        this.version = menuConfig.getString("version");

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

        //TagShop
        this.tagShopName = IridiumColorAPI.process(menuConfig.getString("tagShop.name"));
        this.tagShopCloseName = IridiumColorAPI.process(menuConfig.getString("tagShop.closeMenu.display-name"));
        this.tagShopCloseItem = processMaterial("tagShop.closeMenu.material");
        this.tagShopCloseLore = IridiumColorAPI.process(menuConfig.getStringList("tagShop.closeMenu.lore"));
        this.tagShopCloseSlot = menuConfig.getInt("tagShop.closeMenu.slot");
        this.tagShopGoBackItem = processMaterial("tagShop.goBack.material");
        this.tagShopGoNextItem = processMaterial("tagShop.goNext.material");
        this.tagShopMainMenuItem = processMaterial("tagShop.mainMenu.material");
        this.tagShopGoBackName = IridiumColorAPI.process(menuConfig.getString("tagShop.goBack.display-name"));
        this.tagShopGoNextName = IridiumColorAPI.process(menuConfig.getString("tagShop.goNext.display-name"));
        this.tagShopGoMainMenuName = IridiumColorAPI.process(menuConfig.getString("tagShop.mainMenu.display-name"));
        this.tagShopGoNextLore = IridiumColorAPI.process(menuConfig.getStringList("tagShop.goNext.lore"));
        this.tagShopGoBackLore = IridiumColorAPI.process(menuConfig.getStringList("tagShop.goBack.lore"));
        this.tagShopMainMenuLore = IridiumColorAPI.process(menuConfig.getStringList("tagShop.mainMenu.lore"));
        this.tagShopGoBackSlot = menuConfig.getInt("tagShop.goBack.slot");
        this.tagShopGoNextSlot =  menuConfig.getInt("tagShop.goNext.slot");
        this.tagShopGoMainMenuSlot = menuConfig.getInt("tagShop.mainMenu.slot");
        this.menuConfig.singleLayerKeySet("tagShop.others").forEach((string) -> {
            final ItemStack material = processMaterial("tagShop.others."+string+".material");
            final List<Integer> slots = menuConfig.getStringList("tagShop.others."+string+".slots").stream().map(Integer::parseInt).collect(Collectors.toList());
            this.tagMenuStaticMaterials.put(material,slots);
        });
        this.closeTagShopAfterEachSelection = menuConfig.getBoolean("tagShop.close-tag-shop-after-each-selection");

        //MyTags
        this.myTagName = IridiumColorAPI.process(menuConfig.getString("myTag.name"));
        this.myTagCloseItemName = IridiumColorAPI.process(menuConfig.getString("myTag.closeMenu.display-name"));
        this.myTagGoBackName = IridiumColorAPI.process(menuConfig.getString("myTag.goBack.display-name"));
        this.myTagGoNextName = IridiumColorAPI.process(menuConfig.getString("myTag.goNext.display-name"));
        this.myTagMainMenuName = IridiumColorAPI.process(menuConfig.getString("myTag.mainMenu.display-name"));
        this.myTagCloseItem = processMaterial("myTag.closeMenu.material");
        this.myTagGoBackItem = processMaterial("myTag.goBack.material");
        this.myTagGoNextItem = processMaterial("myTag.goNext.material");
        this.myTagMainMenuItem = processMaterial("myTag.mainMenu.material");
        this.myTagCloseLore = IridiumColorAPI.process(menuConfig.getStringList("myTag.closeMenu.lore"));
        this.myTagGoBackLore = IridiumColorAPI.process(menuConfig.getStringList("myTag.goBack.lore"));
        this.myTagGoNextLore = IridiumColorAPI.process(menuConfig.getStringList("myTag.goNext.lore"));
        this.myTagMainMenuLore = IridiumColorAPI.process(menuConfig.getStringList("myTag.mainMenu.lore"));
        this.myTagCloseSlot = menuConfig.getInt("myTag.closeMenu.slot");
        this.myTagGoNextSlot = menuConfig.getInt("myTag.goNext.slot");
        this.myTagGoBackSlot = menuConfig.getInt("myTag.goBack.slot");
        this.myTagMainMenuSlot = menuConfig.getInt("myTag.mainMenu.slot");
        this.menuConfig.singleLayerKeySet("myTag.others").forEach((string) -> {
            final ItemStack material = processMaterial("myTag.others."+string+".material");
            final List<Integer> slots = menuConfig.getStringList("myTag.others."+string+".slots").stream().map(Integer::parseInt).collect(Collectors.toList());
            this.myTagStaticMaterials.put(material,slots);
        });

        menuConfig.getFileData().clear();
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
        if(menuConfig.contains(configPath))
        {
            if(menuConfig.getString(configPath).startsWith("base64")){
                final String[] texture = menuConfig.getString(configPath).split(":");
                return ItemStackUtils.getSkull(texture[1]);
            }else {
                if (EnumUtils.isValidEnum(XMaterial.class, menuConfig.getString(configPath))) {
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

    public String getTagShopName() {
        return tagShopName;
    }

    public String getTagShopCloseName() {
        return tagShopCloseName;
    }

    public ItemStack getTagShopCloseItem() {
        return tagShopCloseItem;
    }

    public List<String> getTagShopCloseLore() {
        return tagShopCloseLore;
    }

    public int getTagShopCloseSlot() {
        return tagShopCloseSlot;
    }

    public String getTagShopGoBackName() {
        return tagShopGoBackName;
    }

    public String getTagShopGoNextName() {
        return tagShopGoNextName;
    }

    public String getTagShopGoMainMenuName() {
        return tagShopGoMainMenuName;
    }

    public ItemStack getTagShopGoBackItem() {
        return tagShopGoBackItem;
    }

    public ItemStack getTagShopGoNextItem() {
        return tagShopGoNextItem;
    }

    public ItemStack getTagShopMainMenuItem() {
        return tagShopMainMenuItem;
    }

    public List<String> getTagShopGoBackLore() {
        return tagShopGoBackLore;
    }

    public List<String> getTagShopGoNextLore() {
        return tagShopGoNextLore;
    }

    public List<String> getTagShopMainMenuLore() {
        return tagShopMainMenuLore;
    }

    public int getTagShopGoBackSlot() {
        return tagShopGoBackSlot;
    }

    public int getTagShopGoNextSlot() {
        return tagShopGoNextSlot;
    }

    public int getTagShopGoMainMenuSlot() {
        return tagShopGoMainMenuSlot;
    }

    public HashMap<ItemStack, List<Integer>> getTagMenuStaticMaterials() {
        return tagMenuStaticMaterials;
    }

    public String getMyTagName() {
        return myTagName;
    }

    public String getMyTagCloseItemName() {
        return myTagCloseItemName;
    }

    public String getMyTagGoBackName() {
        return myTagGoBackName;
    }

    public String getMyTagGoNextName() {
        return myTagGoNextName;
    }

    public String getMyTagMainMenuName() {
        return myTagMainMenuName;
    }

    public ItemStack getMyTagCloseItem() {
        return myTagCloseItem;
    }

    public ItemStack getMyTagGoBackItem() {
        return myTagGoBackItem;
    }

    public ItemStack getMyTagGoNextItem() {
        return myTagGoNextItem;
    }

    public ItemStack getMyTagMainMenuItem() {
        return myTagMainMenuItem;
    }

    public List<String> getMyTagCloseLore() {
        return myTagCloseLore;
    }

    public List<String> getMyTagGoBackLore() {
        return myTagGoBackLore;
    }

    public List<String> getMyTagGoNextLore() {
        return myTagGoNextLore;
    }

    public List<String> getMyTagMainMenuLore() {
        return myTagMainMenuLore;
    }

    public int getMyTagCloseSlot() {
        return myTagCloseSlot;
    }

    public int getMyTagGoBackSlot() {
        return myTagGoBackSlot;
    }

    public int getMyTagGoNextSlot() {
        return myTagGoNextSlot;
    }

    public int getMyTagMainMenuSlot() {
        return myTagMainMenuSlot;
    }

    public HashMap<ItemStack, List<Integer>> getMyTagStaticMaterials() {
        return myTagStaticMaterials;
    }

    public boolean isCloseTagShopAfterEachSelection() {
        return closeTagShopAfterEachSelection;
    }


}
