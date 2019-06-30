package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){ Player p = (Player)commandSender;
            if (args.length == 0){
                PlayerManager pm = new PlayerManager(p.getUniqueId());

                String dagenWord = pm.getDays() == 1 ? "dag" : "dagen";
                String urenWord = pm.getHours() == 1 ? "uur" : "uren";
                String minWord = pm.getMinutes() == 1 ? "minuut" : "minuten";
                String secWord = pm.getSeconds() == 1 ? "seconde" : "seconden";

                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe bent online geweest voor &c" + pm.getDays() + " &f" + dagenWord + ", &c" + pm.getHours() + " &f" + urenWord + ", &c" + pm.getMinutes() + "&f " + minWord + ", &c" + pm.getSeconds() + " &f" + secWord + "."));
                return true;
            }
            if (args.length > 0 && args.length < 3){
                if (args[0].equalsIgnoreCase("info")){
                    if (args.length == 2){
                        String playerToCheck = args[1];

                        OfflinePlayer of = Bukkit.getOfflinePlayer(playerToCheck);
                        if (!PrisonEscape.getUsers().getConfig().contains(of.getUniqueId().toString())){
                            p.sendMessage(ChatColor.RED + "Deze speler is niet gevonden in de offline of online player database.");
                            return true;
                        }

                        PlayerManager playerManager = new PlayerManager(of.getUniqueId());

                        String dagenWord = playerManager.getDays() == 1 ? "dag" : "dagen";
                        String urenWord = playerManager.getHours() == 1 ? "uur" : "uren";
                        String minWord = playerManager.getMinutes() == 1 ? "minuut" : "minuten";
                        String secWord = playerManager.getSeconds() == 1 ? "seconde" : "seconden";

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&fDe speler &c" + of.getName() + " &fis online geweest voor &c" + playerManager.getDays() + " &f" + dagenWord + ", &c" + playerManager.getHours() + " &f" + urenWord + ", &c" + playerManager.getMinutes() + "&f " + minWord + ", &c" + playerManager.getSeconds() + " &f" + secWord + "."));
                        return true;
                    }
                }else{
                    p.performCommand("time");
                    return true;
                }
            }else{
                p.performCommand("time");
                return true;
            }
        }
        return false;
    }
}
