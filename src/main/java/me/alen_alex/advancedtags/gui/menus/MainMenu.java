package me.alen_alex.advancedtags.gui.menus;

import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.alen_alex.advancedtags.gui.GUI;
import me.alen_alex.advancedtags.gui.GUIHandler;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class MainMenu extends GUI {

    private final ItemMenu mainMenu;

    public MainMenu(GUIHandler handler) {
        super(handler);
        mainMenu = new ItemMenu(getHandler().getMenuConfiguration().getMainMenuName(), ItemMenuSize.fitOf(getHandler().getMenuConfiguration().getMainMenuSize()),null,null);
        init();
    }

    @Override
    public void init() {
        mainMenu.registerListener(getHandler().getPlugin());
    }

    @Override
    public void loadStatics() {
        if(getHandler().getMenuConfiguration().isMainMenuFillerEnabled()){
            fillItems(mainMenu,getHandler().getMenuConfiguration().getFillerItem());
        }

        setStaticItemStacks(getHandler().getMenuConfiguration().getMainMenuStaticMaterials(),this.mainMenu);
    }


    @Override
    public void openMenu(Player player) {
        setUpMenu(player).thenAccept(menu -> {
            SchedulerUtils.runTask(new Runnable() {
                @Override
                public void run() {
                    if(menu instanceof ItemMenu)
                        ((ItemMenu) menu).open(player);
                }
            }, getHandler().getPlugin());

        });
    }

    @Override
    protected CompletableFuture<Object> setUpMenu(Player player) {
        return CompletableFuture.supplyAsync( () -> {
            this.mainMenu.clear();
            loadStatics();
            ActionItem myTags,tagShop,closeButton,adminButton;
            myTags = new ActionItem(getHandler().getMenuConfiguration().getMyTags());
            myTags.setName(getHandler().getMenuConfiguration().getMyTagDisplayName());
            myTags.setLore(getHandler().getMenuConfiguration().getMyTagsLore());
            myTags.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    getHandler().getPlayerTagsMenu().openMenu(player);
                }
            });

            tagShop = new ActionItem(getHandler().getMenuConfiguration().getTagShop());
            tagShop.setName(getHandler().getMenuConfiguration().getTagShopDisplayName());
            tagShop.setLore(getHandler().getMenuConfiguration().getTagShopLore());
            tagShop.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    getHandler().getTagShop().openMenu(player);

                }
            });

            closeButton = new ActionItem(getHandler().getMenuConfiguration().getCloseButton());
            closeButton.setName(getHandler().getMenuConfiguration().getCloseButtonDisplayName());
            closeButton.setLore(getHandler().getMenuConfiguration().getCloseButtonLore());
            closeButton.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    SchedulerUtils.runTask(new Runnable() {
                        @Override
                        public void run() {
                            mainMenu.close(player);
                        }
                    }, getHandler().getPlugin());
                }
            });

            if(handler.getMenuConfiguration().isMainMenuAdminEnabled()) {
                adminButton = new ActionItem(getHandler().getMenuConfiguration().getAdminMenu());
                adminButton.setName(getHandler().getMenuConfiguration().getAdminMenuDisplayMenu());
                adminButton.setLore(getHandler().getMenuConfiguration().getAdminMenuLore());
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
                if(player.hasPermission(getHandler().getMenuConfiguration().getAdminMenuPermission()))
                    mainMenu.setItem(getHandler().getMenuConfiguration().getAdminMenuSlot(),adminButton);
            }

            mainMenu.setItem(getHandler().getMenuConfiguration().getMyTagSlot(),myTags);
            mainMenu.setItem(getHandler().getMenuConfiguration().getTagShopSlot(),tagShop);
            mainMenu.setItem(getHandler().getMenuConfiguration().getCloseButtonSlot(),closeButton);
            return mainMenu;
        });

    }

    public ItemMenu getMainMenu() {
        return mainMenu;
    }
}
