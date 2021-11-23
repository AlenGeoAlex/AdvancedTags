package me.alen_alex.advancedtags.gui.menus;

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

public class PlayerTags extends GUI {

    private BookItemMenu playerTags;
    private AlternateBookPageActionItem goBack,goNext;

    public PlayerTags(GUIHandler handler) {
        super(handler);
        playerTags = new BookItemMenu(handler.getMenuConfiguration().getMyTagName(),ItemMenuSize.SIX_LINE,ItemMenuSize.ONE_LINE,getHandler().getMainMenu().getMainMenu());
        init();
    }

    @Override
    public void init() {
        this.playerTags.registerListener(handler.getPlugin());
        this.playerTags = new BookItemMenu(getHandler().getMenuConfiguration().getTagShopName(), ItemMenuSize.SIX_LINE,ItemMenuSize.ONE_LINE,getHandler().getMainMenu().getMainMenu());
        loadStatics();
    }

    @Override
    public void loadStatics() {
        this.goNext = null;
        this.goBack = null;

        this.goBack = new AlternateBookPageActionItem(getHandler().getMenuConfiguration().getMyTagGoBackName(),getHandler().getMenuConfiguration().getMyTagGoBackItem());
        this.goBack.setLore(getHandler().getMenuConfiguration().getTagShopGoBackLore());
        this.goBack.setGoNext(false);

        this.goNext = new AlternateBookPageActionItem(getHandler().getMenuConfiguration().getMyTagGoNextName(),getHandler().getMenuConfiguration().getMyTagGoNextItem());
        this.goNext.setLore(getHandler().getMenuConfiguration().getTagShopGoNextLore());
        this.goNext.setGoNext(true);
    }

    @Override
    public void openMenu(Player player) {
        this.setUpMenu(player).thenAccept((obj) -> {
            SchedulerUtils.runTask(new Runnable() {
                @Override
                public void run() {
                    if(obj instanceof BookItemMenu) {
                        ((BookItemMenu) obj).open(player);
                    }
                }
            }, handler.getPlugin());
        });
    }

    @Override
    protected CompletableFuture<Object> setUpMenu(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            this.playerTags.clearContents();
            setStaticItemStacks(getHandler().getMenuConfiguration().getMyTagStaticMaterials(), this.playerTags);
            this.playerTags.setBarButton(getHandler().getMenuConfiguration().getMyTagGoBackSlot()-45,this.goBack);
            this.playerTags.setBarButton(getHandler().getMenuConfiguration().getMyTagGoNextSlot()-45,this.goNext);
            final ActionItem closeMenu = new ActionItem(getHandler().getMenuConfiguration().getMyTagCloseItemName(),getHandler().getMenuConfiguration().getMyTagCloseItem());
            closeMenu.setLore(getHandler().getMenuConfiguration().getMyTagCloseLore());
            closeMenu.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    playerTags.close(player);
                }
            });
            playerTags.setBarButton(getHandler().getMenuConfiguration().getMyTagCloseSlot()-45,closeMenu);
            final ActionItem mainMenuItem = new ActionItem(getHandler().getMenuConfiguration().getMyTagMainMenuName(),getHandler().getMenuConfiguration().getMyTagMainMenuItem());
            mainMenuItem.setLore(getHandler().getMenuConfiguration().getMyTagMainMenuLore());
            mainMenuItem.addAction(new ItemAction() {
                @Override
                public ItemActionPriority getPriority() {
                    return ItemActionPriority.NORMAL;
                }

                @Override
                public void onClick(ItemClickAction itemClickAction) {
                    playerTags.close(player);
                    getHandler().getPlugin().getGuiHandler().getMainMenu().openMenu(player);
                }
            });
            playerTags.setBarButton(getHandler().getMenuConfiguration().getMyTagMainMenuSlot()-45,mainMenuItem);
            getHandler().getPlugin().getPluginManager().getPlayer(player).getPlayerUnlockedTags().forEach(tag -> {
                final ActionItem tagMaterial = new ActionItem(tag.getName(),tag.getMenuItem());
                tagMaterial.setLore(tag.getLore());
                tagMaterial.addAction(new ItemAction() {
                    @Override
                    public ItemActionPriority getPriority() {
                        return ItemActionPriority.NORMAL;
                    }

                    @Override
                    public void onClick(ItemClickAction itemClickAction) {
                        getHandler().getPlugin().getPluginManager().setTagForPlayer(player,tag);
                    }
                });
                playerTags.addItem(tagMaterial);
            });
            return this.playerTags;
        });
    }
}
