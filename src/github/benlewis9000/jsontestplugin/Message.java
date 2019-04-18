package github.benlewis9000.jsontestplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class Message {

    private String message;
    private ArrayList<String> playerUUIDs = new ArrayList<>();

    public Message(String message){

        this.message = message;

    }

    public ArrayList<String> getPlayerUUIDs() {

        return playerUUIDs;

    }

    public void addPlayer(Player player){

        this.playerUUIDs.add(player.getUniqueId().toString());

    }

    public void addPlayer(String uuid){

        this.playerUUIDs.add(uuid);

    }

    public void sendMessage(){

        for (String uuid : playerUUIDs){

            Player player = (Player) Bukkit.getServer().getPlayer(uuid);

            player.sendMessage(this.message);

        }

    }

    @Override
    public String toString(){

        return message;

    }

}
