package me.alen_alex.advancedtags.gui;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.configurations.files.MenuConfiguration;
import me.alen_alex.advancedtags.gui.menus.MainMenu;
import me.alen_alex.advancedtags.gui.menus.PlayerTags;
import me.alen_alex.advancedtags.gui.menus.TagShop;

public class GUIHandler {

    private final AdvancedTags plugin;
    private MainMenu mainMenu;
    private MenuConfiguration menuConfiguration;
    private TagShop tagShop;
    private PlayerTags playerTagsMenu;

    public GUIHandler(AdvancedTags plugin) {
        this.plugin = plugin;
        this.menuConfiguration = plugin.getConfigurationHandler().getMenuConfiguration();
    }

    public void init(){
        this.mainMenu = new MainMenu(this);
        this.tagShop = new TagShop(this);
        this.playerTagsMenu = new PlayerTags(this);
    }

    public AdvancedTags getPlugin() {
        return this.plugin;
    }


    public MenuConfiguration getMenuConfiguration() {
        return this.menuConfiguration;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public TagShop getTagShop() {
        return tagShop;
    }

    public PlayerTags getPlayerTagsMenu() {
        return playerTagsMenu;
    }

    public void reloadMenuStatics(){
        this.mainMenu.loadStatics();
        this.tagShop.loadStatics();
        this.playerTagsMenu.loadStatics();
    }
}
