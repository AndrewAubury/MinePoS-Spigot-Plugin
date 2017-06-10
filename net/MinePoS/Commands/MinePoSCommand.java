package net.MinePoS.Commands;

import net.MinePoS.Handlers.ChatHandler;
import net.MinePoS.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Andrew on 08/06/2017.
 */
public class MinePoSCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
if(label.equalsIgnoreCase("minepos")){
    ChatHandler chat = new ChatHandler();
    if(sender.hasPermission("minepos.admin")){
        if(args.length == 0){
            chat.sendHelp(sender);
        }
    }else{
        chat.sendMessage(sender, Messages.noperm);
    }
}
        return false;
    }
}
