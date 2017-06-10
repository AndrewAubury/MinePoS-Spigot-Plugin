package net.MinePoS.Objects;

import net.MinePoS.Main;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/06/2017.
 */
public class Package {
    private int id;
    private String cost;
    private String currency;
    private String name;
    private String itemid;
    private String itemdata;
    private String longdesc;
    private String shortdesc;
    private ItemStack item;
    private ItemType itemType;

    public Package(int id, String cost, String currency, String name, String itemid, String itemdata, String longdesc, String shortdesc) {
        this.id = id;
        this.cost = cost;
        this.currency = currency;
        this.name = name;
        this.itemid = itemid;
        this.itemdata = itemdata;
        this.longdesc = longdesc;
        this.shortdesc = shortdesc;
        itemType = ItemType.Package;
        makeItem();
    }
    private void makeItem() {
        ItemStack is = new ItemStack(Integer.parseInt(itemid));
        MaterialData data = is.getData();
        data.setData(Byte.valueOf(itemdata));
        is.setData(data);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(cost+" "+currency);
        lore.add("");
        lore.add(shortdesc);
        im.setLore(lore);
        is.setItemMeta(im);
        item = is;
    }
    public int getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getCost() {
        return cost;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public String getItemid() {
        return itemid;
    }

    public String getItemdata() {
        return itemdata;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public ItemType getItemType() {
        return itemType;
    }
}
