package net.MinePoS.API;

import net.MinePoS.Main;
import net.MinePoS.Objects.Group;
import net.MinePoS.Objects.Items;
import net.MinePoS.Objects.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;


/**
 * Created by Andrew on 06/06/2017.
 * For calling the MinePoS API
 */
public class WebCalls {

    private String api_link = "";
    private String api_key = "";
    private String name = "";
    private String error = "";

    public WebCalls(String link, String key){
        api_link = link;
        api_key = key;


        if (canConnect()) {
            Main.getInstance().getLogger().severe("Error While Connecting to the API!: " + error);
            Main.getInstance().getLogger().severe("Now Disabling");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }else{
            Main.getInstance().getLogger().info("Connected to the API as the server: " + name);
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
        Main.getInstance().shopinv.catInvs = new HashMap<>();
        Main.getInstance().shopinv.catInvs.put(g.getName(),g);
    }
    Main.getInstance().shopinv.makeInventory();
}

    private Boolean canConnect() {
    String res = makeCall("");
    if(res == null){
        error = "Empty reply from api";
        return false;
    }
        JSONObject jo;
        try {
            jo = new JSONObject(res);
        } catch (JSONException e) {
            error = "Invalid JSON reply from api";
            return false;
        }
        if (jo.getString("error").equalsIgnoreCase("No Action Given")) {
            name = jo.getString("server");
            return true;
        } else {
            error = jo.getString("error");
            return false;
        }
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
