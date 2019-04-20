package github.benlewis9000.jsontestplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.util.UUID;

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

        getLogger().info("-- NEW Main (JavaPlugin) instance --");

    }

    @Override
    public void onEnable(){

        getLogger().info("n-- NEW MessageManager --");
        messageManager = new MessageManager();

        CommandHandler commandHandler = new CommandHandler();

        this.getCommand("newmessage").setExecutor(commandHandler);
        this.getCommand("sendmessages").setExecutor(commandHandler);

        Bukkit.getPlayer("121c13e1-4bc7-452c-b0c9-04053e7d5128").sendMessage("onEnable message to Anarchist String uuid");

        Bukkit.getPlayer(UUID.fromString("121c13e1-4bc7-452c-b0c9-04053e7d5128")).sendMessage("onEnable to Anarchist UUID uuid");

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

            getLogger().severe("ERROR: Failed to load Message's from \"messages.yml\"!");
            getLogger().severe("JsonTestPlugin is now disabling itself...");
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










