package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.events.DoorSystemEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AdminsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> currentAdmins = DoorSystemEvents.getOverrideNames();
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "/" + label + " add <speler>");
            sender.sendMessage(ChatColor.RED + "/" + label + " remove <speler>");
            sender.sendMessage(ChatColor.RED + "/" + label + " list");
            return true;
        }

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("add")){
                sender.sendMessage(ChatColor.RED + "/" + label + " add <speler>");
                return true;
            }else if (args[0].equalsIgnoreCase("remove")){
                sender.sendMessage(ChatColor.RED + "/" + label + " remove <speler>");
                return true;
            }else if (args[0].equalsIgnoreCase("list")){

                sender.sendMessage(ChatColor.RED + "- De huidige admins zijn -");

                for (String name : currentAdmins){
                    sender.sendMessage(ChatColor.WHITE + "- §c" + name);
                }

                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Het sub commando '" + args[0] + "' bestaat niet.");
                return true;
            }
        }else if (args.length == 2){
            if (args[0].equalsIgnoreCase("add")){
                String speler = args[1];

                List<String> list = currentAdmins;
                list.add(speler);

                PrisonEscape.getPlugin(PrisonEscape.class).getConfig().set("admins", list);
                PrisonEscape.getPlugin(PrisonEscape.class).saveConfig();

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe hebt succesvol &c" + speler + " &ftoegevoegd aan de admin lijst."));
                return true;
            }else if (args[0].equalsIgnoreCase("remove")){
                String speler = args[1];

                List<String> list = currentAdmins;

                if (!list.contains(speler)){
                    sender.sendMessage(ChatColor.RED + "Deze speler is geen admin!");
                    return true;
                }

                list.remove(speler);

                PrisonEscape.getPlugin(PrisonEscape.class).getConfig().set("admins", list);
                PrisonEscape.getPlugin(PrisonEscape.class).saveConfig();

                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe hebt succesvol &c" + speler + " &fverwijderd uit de admin lijst."));
                return true;
            }else{
                sender.sendMessage(ChatColor.RED + "Het sub commando '" + args[0] + "' bestaat niet.");
                return true;
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Je gebruikt teveel argumenten! (Max 2; Jij gebruikte er " + args.length + ")");
            return true;
        }
    }
}
