package github.benlewis9000.jsontestplugin;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MessageManager {

    private ArrayList<Message> messages;

    public MessageManager(){

        messages = loadMessages();

    }

    public ArrayList<Message> getMessages() {

        return messages;

    }

    /**
     * Load Message objects from YAML configuration file
     * @return the Message's loaded
     */
    public ArrayList<Message> loadMessages(){

        // Initialise new ArrayList to store Message objects
        ArrayList<Message> loadedMessages = new ArrayList<>();

        // Get messages config instance
        YamlConfiguration messagesConfig = Main.getInstance().getMessagesConfig();

        // Null check
        if (messagesConfig.contains("messages")) {

            // For every message in data...
            for (String string : messagesConfig.getStringList("messages")) {

                // ..create new Message instance
                Message message = new Message(string);

                // .. and assign it's players
                for (String uuid : messagesConfig.getStringList("messages." + string)) {

                    message.addPlayer(uuid);

                }

                loadedMessages.add(message);

            }

        }

        return loadedMessages;

    }

    public void addMessage(Message message){

        messages.add(message);

    }

    public void saveMessages(){

        System.out.println("MessageManager.saveMessages()");

        YamlConfiguration messagesConfig = Main.getInstance().getMessagesConfig();

        for (Message message : messages){

            System.out.println("Message: " + message.toString());

            // Todo: FIX - sets messages to the one given string each time, need a way of taking [messages[players]]
            messagesConfig.set("messages", message.toString() + "." + message.getPlayerUUIDs());

            for (String uuid : message.getPlayerUUIDs()){

                System.out.println("    UUID: " + uuid);

                messagesConfig.set("messages." + message.toString(), uuid);

            }

        }

        try {

            messagesConfig.save(Main.getInstance().getMessagesFile());

        }
        catch (Exception e){

            System.out.println("ERROR: Failed to save \"messages.yml\" to file!");
            e.printStackTrace();

        }

    }

}
