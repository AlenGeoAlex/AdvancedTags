package me.alen_alex.advancedtags.gui.menus;

import me.Abhigya.core.menu.inventory.Item;
import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.custom.book.BookItemMenu;
import me.Abhigya.core.menu.inventory.custom.book.item.AlternateBookPageActionItem;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import me.Abhigya.core.util.scheduler.SchedulerUtils;
import me.alen_alex.advancedtags.gui.GUI;
import me.alen_alex.advancedtags.gui.GUIHandler;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class TagShop extends GUI {

    private final BookItemMenu shopMenu;
    private AlternateBookPageActionItem goBack,goNext;

    public TagShop(GUIHandler handler) {
        super(handler);
        shopMenu = new BookItemMenu(handler.getMenuConfiguration().getTagShopName(), ItemMenuSize.SIX_LINE,ItemMenuSize.ONE_LINE,handler.getMainMenu().getMainMenu());
        init();
    }

    @Override
    public void init() {
        shopMenu.registerListener(getHandler().getPlugin());
        loadStatics();
    }

    @Override
    public void loadStatics() {
        this.goNext = null;
        this.goBack = null;

        this.goBack= new AlternateBookPageActionItem(getHandler().getMenuConfiguration().getTagShopGoBackName(),getHandler().getMenuConfiguration().getTagShopGoBackItem());
        this.goBack.setLore(getHandler().getMenuConfiguration().getTagShopGoBackLore());
        this.goBack.setBookMenu(this.shopMenu);
        this.goBack.setGoNext(false);

        this.goNext = new AlternateBookPageActionItem(getHandler().getMenuConfiguration().getTagShopGoNextName(),getHandler().getMenuConfiguration().getTagShopGoNextItem());
        this.goNext.setLore(getHandler().getMenuConfiguration().getTagShopGoNextLore());
        this.goNext.setBookMenu(this.shopMenu);
        this.goNext.setGoNext(true);
    }

    @Override
    public void openMenu(Player player) {
        if(handler.getPlugin().getConfigurationHandler().getPluginConfig().isTagShopEnabled()){
            setUpMenu(player).thenAccept((obj) -> {
                SchedulerUtils.runTask(new Runnable() {
                    @Override
                    public void run() {
                        if(obj instanceof BookItemMenu) {
                            ((BookItemMenu) obj).open(player);
                        }
                    }
                },this.getHandler().getPlugin());
            });
        }
    }

    @Override
    protected CompletableFuture<Object> setUpMenu(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            this.shopMenu.clearContents();
            setStaticItemStacks(getHandler().getMenuConfiguration().getTagMenuStaticMaterials(), this.shopMenu);


            ActionItem closeMenu = new ActionItem(getHandler().getMenuConfiguration().getTagShopCloseName(),getHandler().getMenuConfiguration().getTagShopCloseItem(),getHandler().getMenuConfiguration().getTagShopCloseLore());
            closeMenu.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    SchedulerUtils.runTask(new Runnable() {
                        @Override
                        public void run() {
                            shopMenu.close(player);
                        }
                    }, handler.getPlugin());
                }
            });

            ActionItem goMainMenu = new ActionItem(getHandler().getMenuConfiguration().getTagShopGoMainMenuName(),getHandler().getMenuConfiguration().getTagShopMainMenuItem(),getHandler().getMenuConfiguration().getTagShopMainMenuLore());
            goMainMenu.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    shopMenu.close(player);
                    getHandler().getMainMenu().openMenu(player);
                }
            });

            //The buttons start from 0-8
            shopMenu.setBarButton(getHandler().getMenuConfiguration().getTagShopCloseSlot()-45,closeMenu);
            shopMenu.setBarButton(getHandler().getMenuConfiguration().getTagShopGoBackSlot()-45,this.goBack);
            shopMenu.setBarButton(getHandler().getMenuConfiguration().getTagShopGoNextSlot()-45,this.goNext);
            shopMenu.setBarButton(getHandler().getMenuConfiguration().getTagShopGoMainMenuSlot()-45,goMainMenu);
                this.handler.getPlugin().getPluginManager().getPlayer(player).getPlayerLockedTags().forEach((lockedTags) -> {
                    ActionItem tagItem = new ActionItem(lockedTags.getName(), lockedTags.getMenuItem());
                    tagItem.setLore(lockedTags.getLore());
                    tagItem.addAction(new ItemAction() {
                        @Override
                        public ItemActionPriority getPriority() {
                            return ItemActionPriority.NORMAL;
                        }

                        @Override
                        public void onClick(ItemClickAction itemClickAction) {
                            if(itemClickAction.getClickType().isLeftClick())
                                if(getHandler().getMenuConfiguration().isCloseTagShopAfterEachSelection())
                                    shopMenu.close(player);
                                getHandler().getPlugin().getPluginManager().unlockTagForPlayer(player,lockedTags);
                            if(itemClickAction.getClickType().isRightClick()){
                                shopMenu.close(player);
                                getHandler().getPlugin().getChatUtils().sendMessage(player,getHandler().getPlugin().getConfigurationHandler().getMessageConfiguration().getTestMessage(lockedTags.getDisplayTag(), player));
                            }
                        }
                    });
                    shopMenu.addItem(tagItem);

                });
            return shopMenu;
        });
    }
}
