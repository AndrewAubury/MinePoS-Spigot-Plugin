package net.MinePoS.Objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Andrew on 07/06/2017.
 */
public class Items {
    private static Items ourInstance = new Items();

    public static Items getInstance() {
        return ourInstance;
    }

    private HashMap<String,Group> NameToGroup;
    private HashMap<Integer,Group> IDToGroup;

    private HashMap<Integer,Order> Waiting;

    public void addOrder(Order order){
        Waiting.put(order.getID(),order);
    }
    public void removeOrder(int id){
        Waiting.remove(id);
    }
public Collection<Order> getOrders(){
    return Waiting.values();
}

    private Items() {
        NameToGroup = new HashMap<String,Group>();
        Waiting = new HashMap<Integer,Order>();
    }

    public void reset(){
        NameToGroup = new HashMap<String,Group>();
    }
    public Integer AmtOfGroups(){
        return NameToGroup.values().size();
    }
    public void clearWaiting(){
        Waiting.clear();
    }
    public void storeGroup(Group g){
        NameToGroup.put(g.getName(),g);
    }
    public Group getGroup(String name){
        return NameToGroup.get(name);
    }
}
