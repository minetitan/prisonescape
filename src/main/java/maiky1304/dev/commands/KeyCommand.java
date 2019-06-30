package maiky1304.dev.commands;

import maiky1304.dev.events.DoorSystemEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KeyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player p = (Player)commandSender;

            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "Je moet wel een region name invoeren.");
                return true;
            }
            if (args.length == 1){
                String region = args[0];

                p.getInventory().addItem(DoorSystemEvents.getKey(region));
                p.sendMessage(ChatColor.WHITE + "Er is een sleutel toegevoegd aan je inventory voor de region §c" + region + "§f.");
                return true;
            }
            if (args.length > 1){
                p.sendMessage(ChatColor.RED + "Je moet alleen de naam invullen van de region!");
                return true;
            }
        }else{
            commandSender.sendMessage(ChatColor.RED + "Console is niet toegestaan voor dit command.");
        }
        return false;
    }
}
