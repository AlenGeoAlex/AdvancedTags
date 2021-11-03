package me.alen_alex.advancedtags.object;

import com.google.common.base.Objects;
import me.Abhigya.core.util.xseries.XMaterial;
import me.alen_alex.advancedtags.utils.iridiumcolorapi.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public final class Tag {

    private final String name;
    private final String displayTag;
    private final boolean permissionRequired;
    private final String permission;
    private final boolean dynamicTag;
    private final List<String> lore;
    private final XMaterial menuMaterial;
    private boolean global = false;
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
        this.menuMaterial = menuMaterial1;
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
        this.menuMaterial = menuMaterial1;
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

    public XMaterial getMenuMaterial() {
        return menuMaterial;
    }

    public boolean hasPlayerPermissionRequired(Player player){
        return player.hasPermission(this.permission);
    }

    public Material parseXMaterial(){
        return this.menuMaterial.parseMaterial();
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return permissionRequired == tag.permissionRequired && dynamicTag == tag.dynamicTag && global == tag.global && Objects.equal(name, tag.name) && Objects.equal(displayTag, tag.displayTag) && Objects.equal(permission, tag.permission) && Objects.equal(lore, tag.lore) && menuMaterial == tag.menuMaterial;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, displayTag, permissionRequired, permission, dynamicTag, lore, menuMaterial, global);
    }
}
