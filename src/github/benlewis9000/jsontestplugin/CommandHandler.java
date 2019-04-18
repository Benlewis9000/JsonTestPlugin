package github.benlewis9000.jsontestplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        System.out.println("CommandHandler.onCommand(..) called");

        if (label.equalsIgnoreCase("newmessage")){

            System.out.println("Command detected as /newmessage");

            if (args != null){

                System.out.println("args not null");

                StringBuilder sb = new StringBuilder();

                for (String string : args){

                    System.out.println("..arg stringbuilder cycle..");
                    sb.append(string).append(" ");

                }

                String text = sb.toString();

                Message message = new Message(text);

                Main.getInstance().messageManager.addMessage(message);

                Main.getInstance().messageManager.saveMessages();

            }

        }
        else if (label.equalsIgnoreCase("sendmessages")){

            System.out.println("Command detected as /sendmessage");

            for (Message message : Main.getInstance().messageManager.getMessages()){

                message.sendMessage();

            }

        }

        return false;

    }

}
