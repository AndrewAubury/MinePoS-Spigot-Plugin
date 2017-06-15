package net.MinePoS.GUI;

import net.MinePoS.Handlers.ChatHandler;
import net.MinePoS.Handlers.ConfigHandler;
import net.MinePoS.Main;
import net.MinePoS.Messages;
import net.MinePoS.Objects.Group;
import net.MinePoS.Objects.Package;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Andrew on 07/06/2017.
 */
public class Events implements Listener{
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory().getTitle().equalsIgnoreCase("MinePoSCommand Store")) {
            if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                e.setCancelled(true);
                Group g = Main.getInstance().shopinv.catInvs.get(e.getCurrentItem().getItemMeta().getDisplayName());
                Player p = (Player) e.getWhoClicked();
                g.open(p);
            }

        } else if (e.getClickedInventory().getTitle().startsWith("MinePoSCommand: ")
                && Main.getInstance().shopinv.catInvs.containsKey(e.getClickedInventory().getName().replace("MinePoSCommand: ", ""))) {

            if (e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                Player p = (Player) e.getWhoClicked();
                e.setCancelled(true);
                p.closeInventory();
                Group g = Main.getInstance().shopinv.catInvs.get(e.getClickedInventory().getName().replace("MinePoSCommand: ", ""));
                Package pk = g.StringToPackage(e.getCurrentItem().getItemMeta().getDisplayName());
                ChatHandler ch = new ChatHandler();
                ConfigHandler conf = new ConfigHandler();
                ch.sendMessage(p, Messages.linkwait);
                ch.sendString(p, conf.getMessage(Messages.link).replace("%link%",Main.getInstance().API.makeLink(p.getName(), pk.getId())));
            }
        }
    }
}
