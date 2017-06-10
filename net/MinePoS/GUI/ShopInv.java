package net.MinePoS.GUI;

import net.MinePoS.Main;
import net.MinePoS.Objects.Group;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 07/06/2017.
 */
public class ShopInv {
    private static ShopInv ourInstance;
    public static ShopInv getInstance() {
        return ourInstance;
    }

    private Inventory catList;
    public HashMap<String,Group> catInvs;

    public ShopInv() {
        ourInstance = this;
        HashMap<String,Group> catInvs = new HashMap<String,Group>();
    }

    public void makeInventory(){
        catList = Main.getInstance().getServer().createInventory(null,round(catInvs.size(),9),"MinePoSCommand Store");
        for(Group g : catInvs.values()){
            catList.addItem(g.getItem());
        }
    }
    public void open(Player p){
        p.openInventory(catList);
    }
    private int round(int num, int multiple) {
        return multiple * (int) Math.ceil((float) num / (float) multiple);
    }

}
