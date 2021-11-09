package me.alen_alex.advancedtags.gui.menus;

import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.custom.book.BookItemMenu;
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

    public TagShop(GUIHandler handler) {
        super(handler);
        shopMenu = new BookItemMenu(handler.getMenuConfiguration().getTagShopName(), ItemMenuSize.SIX_LINE,handler.getMainMenu().getMainMenu());
        init();
    }

    @Override
    public void init() {
        shopMenu.registerListener(getHandler().getPlugin());
        loadStatics();
    }

    @Override
    public void loadStatics() {

    }

    @Override
    public void openMenu(Player player) {
        if(handler.getPlugin().getConfigurationHandler().getPluginConfig().isTagShopEnabled()){
            setUpMenu(player).thenAccept((obj) -> {
                SchedulerUtils.runTask(new Runnable() {
                    @Override
                    public void run() {
                        if(obj instanceof BookItemMenu) {
                            System.out.println(((BookItemMenu) obj).getPagesSize().name());
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
            this.shopMenu.clear();
            //TODO issue in getting all tags from server, its returning emoty list!
            this.handler.getPlugin().getPluginManager().getPlayer(player).getPlayerLockedTags().forEach((lockedTags) -> {
                System.out.println(lockedTags.getMenuItem().getType().name());
                ActionItem tagItem = new ActionItem(lockedTags.getName(),lockedTags.getMenuItem());
                System.out.println(tagItem.getName());
                tagItem.addAction(new ItemAction() {
                    @Override
                    public ItemActionPriority getPriority() {
                        return ItemActionPriority.NORMAL;
                    }

                    @Override
                    public void onClick(ItemClickAction itemClickAction) {
                        handler.getPlugin().getLogger().info("Check for money and unlock if needed!");
                    }
                });
                shopMenu.addItem(tagItem);
            });
            return shopMenu;
        });
    }
}
