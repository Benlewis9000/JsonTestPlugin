package github.benlewis9000.jsontestplugin;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.bukkit.Bukkit.getLogger;

public class MessageManager {

    private ArrayList<Message> messages;

    public MessageManager(){

        getLogger().info("new MessageManager()");
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

        getLogger().info("MessageManager.loadMessages()");

        // Initialise new ArrayList to store Message objects
        ArrayList<Message> loadedMessages = new ArrayList<>();

        // Get messages config instance
        YamlConfiguration messagesConfig = Main.getInstance().getMessagesConfig();

        // Null check
        if (messagesConfig.contains("messages")) {

            getLogger().info("contains \"messages\"");

            // Get "messages" section of config
            ConfigurationSection messagesSect = messagesConfig.getConfigurationSection("messages");

            // Get keys (IDs) listed in messages section
            Set<String> messageIDs = messagesSect.getKeys(false);

            // For every message ID in data...
            for (String messageID : messageIDs) {

                getLogger().info("cycling");

                try {

                    int id = Integer.valueOf(messageID);

                    getLogger().info("Attempting to load message " + id + " to manager.");

                    // ..create new Message instance
                    Message message = new Message(id);

                    message.setMessage(messagesConfig.getString("messages." + id + ".message"));

                    // .. and assign it's players
                    for (String uuid : messagesConfig.getStringList("messages." + id + ".players")) {

                        getLogger().info("Adding UUID: " + uuid);

                        message.addPlayer(uuid);

                    }

                    loadedMessages.add(message);

                }
                catch(NumberFormatException e){
                    getLogger().warning("Error: failed to parse message ID \"" + messageID + "\" to int.");
                }

            }

        }

        return loadedMessages;

    }

    /**
     * Add a Message object to the manager
     * @param message to be added
     */
    public void addMessage(Message message){

        messages.add(message);

    }

    /**
     * Save all associated Message's to file
     */
    public void saveMessages(){



        /*

            messages:
                - message1:
                    - p1
                    - p2
                    - p3
                - message2:
                    - p1
                    - p2
                - message3:
                - message4:
                    - p1
                    - p2
                    - p3
                    - p4

            ArrayList<String> playersOfMessage


         */


        System.out.println("MessageManager.saveMessages()");

        // Get instance of message config
        YamlConfiguration messagesConfig = Main.getInstance().getMessagesConfig();

        // For each message, attempt to save to config
        for (Message message : messages){

            System.out.println("Message: " + message.toString());

            messagesConfig.set("messages." + message.getId() + ".message", message.getMessage());
            messagesConfig.set("messages." + message.getId() + ".players", message.getPlayerUUIDs());

            /*
            // Todo: FIX - sets messages to the one given string each time, need a way of taking [messages[players]]
            messagesConfig.set("messages", message.toString() + "." + message.getPlayerUUIDs());

            for (String uuid : message.getPlayerUUIDs()){

                System.out.println("    UUID: " + uuid);

                messagesConfig.set("messages." + message.toString(), uuid);

            }
            */

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
