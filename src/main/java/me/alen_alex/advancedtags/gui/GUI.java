package me.alen_alex.advancedtags.gui;

import me.Abhigya.core.menu.inventory.Item;
import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.CompletableFuture;

public abstract class GUI {

    protected GUIHandler handler;

    public GUI(GUIHandler handler) {
        this.handler = handler;
    }

    public void fillItems(ItemMenu menu, ItemStack item){
        final Item filler = new Item(item) {
            @Override
            public void onClick(ItemClickAction itemClickAction) {
                return;
            }
        };
        menu.fillToAll(filler);
    }

    protected abstract void init();

    public abstract void openMenu(Player player);

    public abstract CompletableFuture<ItemMenu> setUpMenu(Player player);


}
