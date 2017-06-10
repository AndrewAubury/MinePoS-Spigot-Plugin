package net.MinePoS.Objects;

import net.MinePoS.Main;

import java.util.ArrayList;

/**
 * Created by Andrew on 07/06/2017.
 */
public class Order {
    private int ID;
    private ArrayList<String> commands;
    private int OrderID;

    public Order(int id, String commandsSTR, int orderid){
        ID = id;
        commands = commandsToArray(commandsSTR);
        OrderID = orderid;
        Items.getInstance().addOrder(this);
    }
    private ArrayList<String> commandsToArray(String cmds){
        ArrayList<String> cmdal = new ArrayList<>();
         for(String command :  cmds.split(";")){
             if(command.startsWith("/")){
                 command = command.replaceFirst("/","");
             }
             cmdal.add(command);
         }
        return cmdal;
    }

    public int getID() {
        return ID;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public int getOrderID() {
        return OrderID;
    }
    public void markDone(){
        Main.getInstance().API.setOrderComplete(getID());
    }

    public void run() {
        for(String cmd : commands){
            //Main.getInstance().getServer().broadcastMessage(cmd);
            Main.getInstance().command(cmd);
        }

        markDone();
    }
}
