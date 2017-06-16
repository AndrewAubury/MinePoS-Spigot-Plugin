package net.MinePoS;

import net.MinePoS.API.WebCalls;
import net.MinePoS.Commands.MinePoSCommand;
import net.MinePoS.GUI.Events;
import net.MinePoS.GUI.ShopInv;
import net.MinePoS.Handlers.ChatHandler;
import net.MinePoS.Handlers.ConfigHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Andrew on 06/06/2017.
 */
public class Main extends JavaPlugin {
    private static Main myinstance;
    public WebCalls API;
    public ShopInv shopinv;

    public static Main getInstance(){
        return myinstance;
    }

    public void onEnable() {
        createConfig();
        getConfig();
        saveConfig();
        shopinv = new ShopInv();
        myinstance = this;
        ConfigHandler conf = new ConfigHandler();
        API = new WebCalls(conf.getAPILink(),conf.getAPIKey());
        API.getGroups();
        getServer().getLogger().info("There are " + ShopInv.getInstance().catInvs.size() + " Groups on the MinePoSCommand Store");
        getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("minepos").setExecutor(new MinePoSCommand());

    }

    private void startPolling() {
        getServer().getScheduler().cancelTasks(this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Main.getInstance().API.getWaiting();
            }
        }, (20 * 60) * getConfig().getInt("settings.pollingrate"), (20 * 60) * getConfig().getInt("settings.pollingrate"));
    }
    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                saveDefaultConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void command(String cmd){
        getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
    }
    public void command(String Order,String cmd){
        getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("buy")){
            if(sender instanceof Player){
                if(args.length == 1 && args[0].equalsIgnoreCase("update")) {
                    Player p = (Player) sender;
                    new ChatHandler().sendString(p,"&cNow updating the orders");
                    API.getWaiting();
                    return true;
                }
                Player p = (Player) sender;
                ShopInv.getInstance().open(p);
                new ChatHandler().sendMessage(p,Messages.open);
            }else{
                sender.sendMessage("You must be a player to do this");
            }
        }
        return true;
    }
}
