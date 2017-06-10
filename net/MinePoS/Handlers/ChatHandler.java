package net.MinePoS.Handlers;

import net.MinePoS.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

/**
 * Created by Andrew on 08/06/2017.
 */
public class ChatHandler {
    private String prefix;
    ConfigHandler confighandler;

    public ChatHandler(){
        confighandler = new ConfigHandler();
        prefix = confighandler.getMessage(Messages.prefix);
    }

    public void sendString(CommandSender p, String msg){
        p.sendMessage(cc(prefix+msg));
    }
    public void sendStringNoPrefix(CommandSender p, String msg){
        p.sendMessage(cc(msg));
    }
    public void sendMessage(CommandSender p,Messages msg){
        sendString(p,confighandler.getMessage(msg));
    }
    public void sendArray(CommandSender p, ArrayList<String> list){
        p.sendMessage(cc("&4---===["+prefix.trim()+"&4]===---"));
        for(String msg:list){
            p.sendMessage(cc(msg));
        }
        p.sendMessage(cc("&4---====================---"));
    }

    public String cc(String msg){
        return ChatColor.translateAlternateColorCodes('&',msg);
    }

    public void sendHelp(CommandSender sender) {
        ArrayList<String> help = new ArrayList<String>();
        help.add("&a/minepos - displays help");
        help.add("&a/minepos poll - Updates the order list");
        help.add("&a/minepos reload - Reloads Config");
        sendArray(sender, help);
    }
}
