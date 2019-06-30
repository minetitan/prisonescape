package maiky1304.dev.commands;

import maiky1304.dev.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNaamkleur implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            if (sender.hasPermission("minetitan.moderator")){
                if (args.length == 0){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cGebruik /setnaamkleur <speler> <kleur>"));
                    return true;
                }else if (args.length == 1){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cGebruik /setnaamkleur " + args[0] + " <kleur>"));
                    return true;
                }else if (args.length == 2){
                    String player = args[0];
                    String kleur = args[1];

                    OfflinePlayer of = Bukkit.getOfflinePlayer(player);
                    PlayerManager pm = new PlayerManager(of.getUniqueId());
                    if (!pm.existsInDatabase()){
                        p.sendMessage(ChatColor.RED + "Deze speler bestaat niet in de database hij is nog nooit Prison Escape gejoined!");
                        return true;
                    }

                    pm.setNaamkleur(kleur);
                    p.sendMessage(ChatColor.RED + "Je hebt succesvol de naamkleur van " + player + " gewijzigd naar §" + kleur + "Kleur" + ChatColor.RED +  ".");
                    return true;
                }else{
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cError, je hebt teveel argumenten gebruikt (" + args.length + " max is 2)"));
                    return true;
                }
            }
        }
        return false;
    }
}
