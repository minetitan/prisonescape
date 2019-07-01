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

public class SetBaanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;

            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "Gebruik /" + label + " <speler> <job>");
                return true;
            }

            if (args.length == 1){
                p.sendMessage(ChatColor.RED + "Gebruik /" + label + " <speler> <job>");
                return true;
            }

            if (args.length > 1){
                String player = args[0];
                String job = "";

                for(int i = 1; i != args.length; i++){
                    job += args[i] + " ";
                }

                job = job.substring(0, job.length()-1);

                OfflinePlayer of = Bukkit.getOfflinePlayer(player);
                if (!PrisonEscape.getUsers().getConfig().contains(of.getUniqueId().toString())){
                    p.sendMessage(ChatColor.RED + "De speler " + player + " is niet gevonden in de database.");
                    return true;
                }

                PlayerManager pm = new PlayerManager(of.getUniqueId());
                pm.setJob(job);

                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe hebt succesvol de job van &c" + player + " &fgewijzigd naar &c" + player + "&f."));
                return true;
            }
        }
        return false;
    }

}
