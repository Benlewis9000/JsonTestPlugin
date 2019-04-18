package github.benlewis9000.jsontestplugin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;

    private File messagesFile;
    public File getMessagesFile() {
        return messagesFile;
    }
    private YamlConfiguration messagesConfig;
    public YamlConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public MessageManager messageManager;

    public static Main getInstance(){

        return instance;

    }

    public Main(){

        instance = this;

        messagesConfig = loadMessagesConfig();

        messageManager = new MessageManager();

    }

    @Override
    public void onEnable(){

        CommandHandler commandHandler = new CommandHandler();

        this.getCommand("newmessage").setExecutor(commandHandler);
        this.getCommand("sendmessages").setExecutor(commandHandler);

    }

    @Override
    public void onDisable(){



    }


    public YamlConfiguration loadMessagesConfig(){

        // Create instance of File to load from
        messagesFile = new File(this.getDataFolder(), "messages.yml");

        // Check for the file
        if (!messagesFile.exists()){

            // If not found, create parent directory and File itself
            messagesFile.getParentFile().mkdirs();
            System.out.println(ChatColor.RED + "Parent file path: " + messagesFile.getParentFile().getPath());
            Main.getInstance().saveResource("messages.yml", false);

        }

        // Instantiate messagesConfig as a YAML config
        messagesConfig = new YamlConfiguration();

        try {

            // Try to load the .yml File into the YAML config instance
            messagesConfig.load(messagesFile);

        }
        catch (Exception e){ // Catch IO/FNF/InvalidConfig exceptions

            System.out.println("ERROR: Failed to load Message's from \"messages.yml\"!");
            e.printStackTrace();

            // Disable plugin as unsafe to continue
            this.getServer().getPluginManager().disablePlugin(this);

        }

        return messagesConfig;

    }

    /*
    // Irrelevant code - preserved for reference

    public YamlConfiguration getArenasConfig(){

        // Create instance of File to use
        messagesFile = new File(this.getDataFolder(), "arenas.yml");

        // Check for the file
        if (!messagesFile.exists()){

            // If not found, create parent directory and File itself
            messagesFile.getParentFile().mkdirs();
            saveResource("arenas.yml", false);

        }

        // Create new YAML config instance
        messagesConfig = new YamlConfiguration();

        try {

            // Try to load the .yml File into the YAML config instance
            messagesConfig.load(messagesFile);

        }
        catch (Exception e){

            e.printStackTrace();

        }

        return messagesConfig;

    }
    */

}










