package me.alen_alex.advancedtags.object;

import com.google.common.base.Objects;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class Tag {

    private final String name;
    private final String displayTag;
    private boolean permissionRequired;
    private String permission;
    private boolean dynamicTag;
    private List<String> lore;
    private ItemStack menuItem;
    private boolean global = false;
    private float money;
    /**
     * Creates a new Tag
     * @param name
     * @param displayTag
     */

    public Tag(String name, String displayTag, boolean permissionRequired, String permission, boolean dynamicTag, List<String> lore, String menuMaterial) {
        XMaterial menuMaterial1;
        this.name = name;
        this.displayTag = IridiumColorAPI.process(displayTag);
        this.permissionRequired = permissionRequired;
        this.permission = permission;
        this.dynamicTag = dynamicTag;
        this.lore = lore;
        menuMaterial1 = XMaterial.matchXMaterial(menuMaterial).get();
        if(menuMaterial1 == null)
            menuMaterial1 = XMaterial.CRAFTING_TABLE;
        this.menuItem = menuMaterial1.parseItem();
    }

    public Tag(String name, String displayTag, boolean dynamicTag, List<String> lore, String menuMaterial) {
        XMaterial menuMaterial1;
        this.name = name;
        this.displayTag = IridiumColorAPI.process(displayTag);
        this.permissionRequired = false;
        this.permission = "";
        this.dynamicTag = dynamicTag;
        this.lore = lore;
        menuMaterial1 = XMaterial.matchXMaterial(menuMaterial).get();
        if(menuMaterial1 == null)
            menuMaterial1 = XMaterial.CRAFTING_TABLE;
        this.menuItem = menuMaterial1.parseItem();

    }

    public Tag(String name, String displayTag) {
        this.name = name;
        this.displayTag = IridiumColorAPI.process(displayTag);
        this.permissionRequired = false;
        this.permission = "";
        this.dynamicTag = false;
        this.lore = new ArrayList<String>();
        this.menuItem = XMaterial.CRAFTING_TABLE.parseItem();
    }

    public void setPermissionRequired(boolean permissionRequired) {
        this.permissionRequired = permissionRequired;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setDynamicTag(boolean dynamicTag) {
        this.dynamicTag = dynamicTag;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getDisplayTag() {
        return displayTag;
    }

    public boolean isPermissionRequired() {
        return permissionRequired;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isDynamicTag() {
        return dynamicTag;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean hasPlayerPermissionRequired(Player player){
        return player.hasPermission(this.permission);
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }


    public ItemStack getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(ItemStack menuItem) {
        this.menuItem = menuItem;
    }

    public void setMenuItem(XMaterial menuMaterial){
        this.menuItem = menuMaterial.parseItem();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return isPermissionRequired() == tag.isPermissionRequired() && isDynamicTag() == tag.isDynamicTag() && isGlobal() == tag.isGlobal() && Float.compare(tag.money, money) == 0 && Objects.equal(getName(), tag.getName()) && Objects.equal(getDisplayTag(), tag.getDisplayTag()) && Objects.equal(getPermission(), tag.getPermission()) && Objects.equal(getLore(), tag.getLore()) && Objects.equal(getMenuItem(), tag.getMenuItem());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getDisplayTag(), isPermissionRequired(), getPermission(), isDynamicTag(), getLore(), getMenuItem(), isGlobal(), money);
    }

    @Override
    public String toString() {
        return this.displayTag;
    }
}
