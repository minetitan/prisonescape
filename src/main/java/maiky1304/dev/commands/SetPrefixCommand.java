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

public class SetPrefixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender se, Command command, String label, String[] args) {
        if (se instanceof Player) {
            Player p = (Player) se;

            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "Gebruik /" + label + " <speler> <prefix>");
                return true;
            }

            if (args.length == 1){
                p.sendMessage(ChatColor.RED + "Gebruik /" + label + " <speler> <prefix>");
                return true;
            }

            if (args.length == 2){
                String speler = args[0];
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < args.length; i++){
                    sb.append(args[i]);
                    if (i != args.length){
                        sb.append(" ");
                    }
                }

                String prefix = sb.toString();

                OfflinePlayer of = Bukkit.getOfflinePlayer(speler);
                if (!PrisonEscape.getUsers().getConfig().contains(of.getUniqueId().toString())){
                    p.sendMessage(ChatColor.RED + "De speler " + speler + " is niet gevonden in de database.");
                    return true;
                }

                PlayerManager pm = new PlayerManager(of.getUniqueId());
                pm.setJob(prefix);

                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe hebt succesvol de prefix van &c" + speler + " &fgewijzigd naar &c" + prefix + "&f."));
                return true;
            }
        }
        return false;
    }
}
