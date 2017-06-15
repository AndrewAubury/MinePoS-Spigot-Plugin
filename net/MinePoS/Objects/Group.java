package net.MinePoS.Objects;


import net.MinePoS.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 07/06/2017.
 */
public class Group {
    Inventory groupInv;
    HashMap<String, Package> StrToPackage;
    HashMap<Integer, Package> IDToPackage;
    ArrayList<ItemStack> items;
    private int id;
    private String name;
    private String itemid;
    private String itemdata;
    private String smalldesc;
    private ItemStack item;

    public Group(int id, String name, String itemid, String itemdata, String smalldesc) {
        this.id = id;
        this.name = name;
        this.itemid = itemid;
        this.itemdata = itemdata;
        this.smalldesc = smalldesc;
        StrToPackage = new HashMap<String, Package>();
        IDToPackage = new HashMap<Integer, Package>();
        items = new ArrayList<ItemStack>();
        makeItem();
        getPackages();
        makeInventory();
    }
    public Package StringToPackage(String str){
        return StrToPackage.get(str);
    }
    public void getPackages(){
        Items.getInstance().reset();
        String res = Main.getInstance().API.makeCall("action=getPackages&CategoryID="+id);
        //Main.getInstance().getLogger().info("Response: "+res);
        JSONObject jo = new JSONObject(res);
        JSONArray groups = jo.getJSONArray("packages");
        for (int i = 0; i < groups.length(); i++) {
            JSONObject row = groups.getJSONObject(i);
            int id = row.getInt("ID");
            String cost = row.getString("Cost");
            String currency = row.getString("Currency");
            String name = row.getString("Name");
            String itemid = row.getString("ItemID");
            String itemdata = row.getString("ItemData");
            String longdesc = row.getString("LongDesc");
            String shortdesc = row.getString("ShortDesc");
            Package p  = new Package(id, cost, currency, name, itemid, itemdata, longdesc, shortdesc);
            p.getItem();
            StrToPackage.put(p.getName(),p);
            IDToPackage.put(p.getId(),p);
            items.add(p.getItem());
        }
        //Main.getInstance().getServer().getLogger().info("In the Group "+ getName() +" there are "+items.size()+ " package(s)");
        makeInventory();

    }
    private int round(int num, int multiple) {
        return multiple * (int) Math.ceil((float) num / (float) multiple);
    }
    private void makeInventory() {
        groupInv = Main.getInstance().getServer().createInventory(null,round(items.size(),9),"MinePoSCommand: "+getName());
    for(ItemStack item: items){
        if(item != null){
            groupInv.addItem(item);
        }
    }
    }

    private void makeItem() {
        ItemStack is = new ItemStack(Integer.parseInt(itemid),1);
        ItemMeta im = is.getItemMeta();
        is.setDurability(Short.parseShort(itemdata));
        im.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(smalldesc);
        im.setLore(lore);
        is.setItemMeta(im);
        item = is;
    }

    public int getId() {
        return id;
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

    public String getSmalldesc() {
        return smalldesc;
    }

    public ItemStack getItem() {
        return item;
    }
    public void open(Player p){
        p.openInventory(groupInv);
    }
}
