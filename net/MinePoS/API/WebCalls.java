package net.MinePoS.API;

import net.MinePoS.GUI.ShopInv;
import net.MinePoS.Main;
import net.MinePoS.Objects.Group;
import net.MinePoS.Objects.Items;
import net.MinePoS.Objects.Order;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Andrew on 06/06/2017.
 */
public class WebCalls {

    private String api_link = "";
    private String api_key = "";

    public WebCalls(String link, String key){
        api_link = link;
        api_key = key;

        String con = canConnect();
        if(con == null){
            Main.getInstance().getLogger().severe("Error While Connecting to the API! Will now disable Plugin");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }else{
            Main.getInstance().getLogger().info("Connected to the API as the server: "+con);
        }
    }

public void getGroups(){
    Items.getInstance().reset();
    String res = makeCall("action=getcategories");
    //Main.getInstance().getLogger().info("Response: "+res);
    JSONObject jo = new JSONObject(res);
    JSONArray groups = jo.getJSONArray("categories");
    for (int i = 0; i < groups.length(); i++) {
        JSONObject row = groups.getJSONObject(i);
        int id = row.getInt("ID");
        String name = row.getString("Name");
        String itemid = row.getString("ItemID");
        String itemdata = row.getString("ItemData");
        String smalldesc = row.getString("Desc");
        Group g = new Group(id, name, itemid, itemdata,smalldesc);
        Items.getInstance().storeGroup(g);
        Main.getInstance().shopinv.catInvs = new HashMap<String, Group>();
        Main.getInstance().shopinv.catInvs.put(g.getName(),g);
    }
    Main.getInstance().shopinv.makeInventory();
}
public String canConnect(){
    String res = makeCall("");
    if(res == null){
        return null;
    }

    return new JSONObject(res).getString("server");
}

public String makeCall(String args){
    return executePost(api_link+"&apikey="+api_key+"&"+args);
}
public String makeLink(String PlayerName, Integer id){
    String res = makeCall("action=createlink&playername="+PlayerName+"&PackageID="+id);
    //Main.getInstance().getLogger().info("Result: "+res);
    JSONObject resjo = new JSONObject(res);
    String link = resjo.getString("link");
    if(link != null){
        return link;
    }
    return "ERROR";
}
public void getWaiting(){
    String res = makeCall("action=getwaiting");
    //Main.getInstance().getLogger().info("Result: "+res);
    JSONObject jo = new JSONObject(res);
    JSONArray orders = jo.getJSONArray("waiting");
    for (int i = 0; i < orders.length(); i++) {
        JSONObject row = orders.getJSONObject(i);
        int ID = row.getInt("ID");
        int OrderID = row.getInt("OrderID");
        String commands = row.getString("Commands");
        new Order(ID,commands,OrderID);
    }

    for(Order cur : Items.getInstance().getOrders()){
        if(cur != null){
            cur.run();
        }
    }
    Items.getInstance().clearWaiting();
}
public void setOrderComplete(int id){
    makeCall("action=setcomplete&id="+id);
}
private String executePost(String targetURL) {
    //Main.getInstance().getLogger().info("Calling: "+targetURL);
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", "Mozilla/4.76");
            System.setProperty("http.agent", "Chrome");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
