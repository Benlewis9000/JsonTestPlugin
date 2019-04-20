package github.benlewis9000.jsontestplugin;

import com.mysql.fabric.xmlrpc.base.Array;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

import static org.bukkit.Bukkit.getLogger;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for (Player p : Bukkit.getOnlinePlayers()){
            getLogger().info(p.getUniqueId().toString());
        }

        System.out.println("CommandHandler.onCommand(..) called");

        if (label.equalsIgnoreCase("newmessage")){

            System.out.println("Command detected as /newmessage");

            if (args != null){

                System.out.println("args not null");

                StringBuilder sb = new StringBuilder();

                ArrayList<String> argsAL = new ArrayList<>(Arrays.asList(args));

                argsAL.remove(0);

                for (String string : argsAL){

                    System.out.println("..argAL stringbuilder cycle.. " + string);
                    sb.append(string).append(" ");

                }

                String text = sb.toString();

                Message message = new Message(Integer.valueOf(args[0]));

                message.setMessage(text);

                // Todo; remove test code below
                message.addPlayer("121c13e1-4bc7-452c-b0c9-04053e7d5128");
                message.addPlayer("069a79f4-44e9-4726-a5be-fca90e38aaf5");
                message.addPlayer("e98de08b-f46c-4ee9-a5b2-57938976642f");

                Main.getInstance().messageManager.addMessage(message);

                Main.getInstance().messageManager.saveMessages();

            }

        }
        else if (label.equalsIgnoreCase("sendmessages")){

            System.out.println("Command detected as /sendmessage");

            for (Message message : Main.getInstance().messageManager.getMessages()){

                getLogger().info("Found message " + message.getId() + ": " + message.getMessage());
                getLogger().info("Found UUID's: " + message.getPlayerUUIDs());
                message.sendMessage();

            }

        }

        // todo: poor practice? (yes)
        return true;

    }

}
