package github.benlewis9000.jsontestplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class Message {

    private int id;
    private String message;
    private ArrayList<UUID> playerUUIDs = new ArrayList<>();

    public Message(int id){

        this.id = id;

    }

    public int getId(){

        return id;

    }
    public ArrayList<UUID> getPlayerUUIDs() {

        return playerUUIDs;

    }
    public String getMessage(){

        return message;

    }
    public void setMessage(String message){

        this.message = message;

    }

    /**
     * Add a player to be a reciever of this message
     * @param player to be added
     */
    public void addPlayer(Player player){

        this.playerUUIDs.add(player.getUniqueId());

    }

    /**
     * Add a player to be a reciever of this message
     * @param uuidString of player to be added
     */
    public void addPlayer(String uuidString){

        UUID uuid = UUID.fromString(uuidString);

        this.playerUUIDs.add(uuid);

    }

    /**
     * Send message to associated players
     */
    public void sendMessage(){

        for (UUID uuid : playerUUIDs){

            getLogger().info(uuid.toString());

            // Todo: What happens when player is offline?

            Player player = Bukkit.getServer().getPlayer(uuid);

            if (player != null) {

                getLogger().info("player is NOT null");
                player.sendMessage(this.getMessage());

            }
            else {

                getLogger().info(uuid + " is NULL");

            }

        }

    }

    @Override
    public String toString(){

        return message;

    }

}
