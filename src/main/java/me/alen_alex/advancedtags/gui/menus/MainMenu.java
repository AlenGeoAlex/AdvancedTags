package me.alen_alex.advancedtags.gui.menus;

import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.gui.GUI;
import me.alen_alex.advancedtags.gui.GUIHandler;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class MainMenu extends GUI {

    private ItemMenu mainMenu;

    public MainMenu(GUIHandler handler) {
        super(handler);
        mainMenu = new ItemMenu(handler.getMenuConfiguration().getMainMenuName(), ItemMenuSize.fitOf(handler.getMenuConfiguration().getMainMenuSize()),null,null);
        init();
    }

    @Override
    protected void init() {
        if(handler.getMenuConfiguration().isMainMenuFillerEnabled()){
            fillItems(mainMenu,handler.getMenuConfiguration().getFillerItem());
        }
        mainMenu.registerListener(handler.getPlugin());
    }


    @Override
    public void openMenu(Player player) {
        setUpMenu(player).thenAccept(menu -> menu.open(player));
    }

    @Override
    public CompletableFuture<ItemMenu> setUpMenu(Player player) {
        return CompletableFuture.supplyAsync( () -> {
            ActionItem myTags,tagShop,closeButton,adminButton;
            myTags = new ActionItem(handler.getMenuConfiguration().getMyTags());
            myTags.setName(handler.getMenuConfiguration().getMyTagDisplayName());
            myTags.setLore(handler.getMenuConfiguration().getMyTagsLore());
            myTags.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    System.out.println(itemClickAction.getClickedItem().getItemMeta().getDisplayName());
                }
            });

            tagShop = new ActionItem(handler.getMenuConfiguration().getTagShop());
            tagShop.setName(handler.getMenuConfiguration().getTagShopDisplayName());
            tagShop.setLore(handler.getMenuConfiguration().getTagShopLore());
            tagShop.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    System.out.println(itemClickAction.getClickedItem().getItemMeta().getDisplayName());

                }
            });

            if(handler.getMenuConfiguration().isMainMenuAdminEnabled()) {
                adminButton = new ActionItem(handler.getMenuConfiguration().getAdminMenu());
                adminButton.setName(handler.getMenuConfiguration().getAdminMenuDisplayMenu());
                adminButton.setLore(handler.getMenuConfiguration().getAdminMenuLore());
                adminButton.addAction(new ItemAction() {
                    @Override
                    public ItemActionPriority getPriority() {
                        return ItemActionPriority.NORMAL;
                    }

                    @Override
                    public void onClick(ItemClickAction itemClickAction) {
                        System.out.println(itemClickAction.getClickedItem().getItemMeta().getDisplayName());
                    }
                });
                if(player.hasPermission(handler.getMenuConfiguration().getAdminMenuPermission()))
                    mainMenu.setItem(handler.getMenuConfiguration().getAdminMenuSlot(),adminButton);
            }

            mainMenu.setItem(handler.getMenuConfiguration().getMyTagSlot(),myTags);
            mainMenu.setItem(handler.getMenuConfiguration().getTagShopSlot(),tagShop);
            return mainMenu;
        });

    }

}
