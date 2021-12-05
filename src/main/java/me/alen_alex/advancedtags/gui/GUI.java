package me.alen_alex.advancedtags.gui;

import com.pepedevs.corelib.gui.inventory.Item;
import com.pepedevs.corelib.gui.inventory.ItemMenu;
import com.pepedevs.corelib.gui.inventory.action.ItemClickAction;
import com.pepedevs.corelib.gui.inventory.custom.book.BookItemMenu;
import com.pepedevs.corelib.gui.inventory.item.action.ActionItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
        filler.setName("");
        menu.fillToAll(filler);
    }

    public void setStaticItemStacks(HashMap<ItemStack, List<Integer>> configurationSection,ItemMenu inventory){
        Iterator<Map.Entry<ItemStack, List<Integer>>> staticISIterator = configurationSection.entrySet().iterator();
        while (staticISIterator.hasNext()){
            Map.Entry<ItemStack, List<Integer>> currentConfig = staticISIterator.next();
            try {
                ActionItem item = new ActionItem(currentConfig.getKey());
                item.setName("");
                currentConfig.getValue().forEach((position) -> {
                    inventory.setItem(position,item);
                });
            }catch (Exception e){
                handler.getPlugin().getLogger().warning("Failed to set static items on the inventory "+inventory.getTitle());
                e.printStackTrace();
            }
        }
    }

    public void setStaticItemStacks(HashMap<ItemStack, List<Integer>> configurationSection, BookItemMenu inventory){
        Iterator<Map.Entry<ItemStack, List<Integer>>> staticISIterator = configurationSection.entrySet().iterator();
        while (staticISIterator.hasNext()){
            Map.Entry<ItemStack, List<Integer>> currentConfig = staticISIterator.next();
            try {
                ActionItem item = new ActionItem(currentConfig.getKey());
                item.setName("");
                currentConfig.getValue().forEach((position) -> {
                    inventory.setBarButton(position-45,item);
                });
            }catch (Exception e){
                handler.getPlugin().getLogger().warning("Failed to set static items on the inventory "+inventory.getTitle());
                e.printStackTrace();
            }
        }
    }

    public abstract void init();

    public abstract void loadStatics();

    public abstract void openMenu(Player player);

    protected abstract CompletableFuture<Object> setUpMenu(Player player);

    protected GUIHandler getHandler() {
        return handler;
    }

}
