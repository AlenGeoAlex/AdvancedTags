package me.alen_alex.advancedtags.gui;

import me.alen_alex.advancedtags.AdvancedTags;
import me.alen_alex.advancedtags.configurations.files.MenuConfiguration;
import me.alen_alex.advancedtags.gui.menus.MainMenu;

public class GUIHandler {

    private final AdvancedTags plugin;
    private MainMenu mainMenu;
    private MenuConfiguration menuConfiguration;

    public GUIHandler(AdvancedTags plugin) {
        this.plugin = plugin;
        menuConfiguration = plugin.getConfigurationHandler().getMenuConfiguration();
    }

    public void init(){
        mainMenu = new MainMenu(this);
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
}
