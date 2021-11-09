package me.alen_alex.advancedtags.gui;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.configurations.files.MenuConfiguration;
import me.alen_alex.advancedtags.gui.menus.MainMenu;
import me.alen_alex.advancedtags.gui.menus.TagShop;

public class GUIHandler {

    private final AdvancedTags plugin;
    private MainMenu mainMenu;
    private MenuConfiguration menuConfiguration;
    private TagShop tagShop;

    public GUIHandler(AdvancedTags plugin) {
        this.plugin = plugin;
        this.menuConfiguration = plugin.getConfigurationHandler().getMenuConfiguration();
    }

    public void init(){
        mainMenu = new MainMenu(this);
        this.tagShop = new TagShop(this);
    }

    public AdvancedTags getPlugin() {
        return plugin;
    }


    public MenuConfiguration getMenuConfiguration() {
        return menuConfiguration;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public TagShop getTagShop() {
        return tagShop;
    }

    public void reloadMenuStatics(){
        this.mainMenu.loadStatics();
        this.tagShop.loadStatics();
    }
}
