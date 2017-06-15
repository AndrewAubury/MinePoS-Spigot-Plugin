package net.MinePoS.Handlers;

import net.MinePoS.Main;
import net.MinePoS.Messages;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Andrew on 10/06/2017.
 */
public class ConfigHandler {
    public String getMessage(Messages m){
        FileConfiguration config = Main.getInstance().getConfig();
        return config.getString("messages." + m.toString());
    }
    public String getAPILink(){
        FileConfiguration config = Main.getInstance().getConfig();
        return config.getString("API.Link");
    }
    public String getAPIKey(){
        FileConfiguration config = Main.getInstance().getConfig();
        return config.getString("API.Key");
    }
}
