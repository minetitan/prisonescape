package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.util.PlayerManager;
import maiky1304.dev.util.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.Locale;

public class PayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player)sender;
        if (args.length == 0){
            p.sendMessage(ChatColor.RED + "Gebruik /" + s + " <naam> <bedrag>");
            return true;
        }

        if (args.length == 1){
            p.sendMessage(ChatColor.RED + "Gebruik /" + s + " <naam> <bedrag>");
            return true;
        }

        if (args.length == 2){
            String speler = args[0];
            String bedrag = args[1];

            double amount;

            try {
                amount = Double.parseDouble(bedrag);
            } catch (NumberFormatException nf){
                p.sendMessage(ChatColor.RED + "Dit is geen geldig getal!");
                return true;
            }

            OfflinePlayer player = Bukkit.getOfflinePlayer(speler);
            if (!player.isOnline()){
                p.sendMessage(ChatColor.RED + "De speler " + speler + " is niet online!");
                return true;
            }

            Player onlinePlayer = Bukkit.getPlayer(speler);

            if (p.getName().equalsIgnoreCase(speler)){
                p.sendMessage(ChatColor.RED + "Je kunt jezelf geen geld overmaken!");
                return true;
            }

            Locale locale = new Locale("de", "DE");

            NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

            String balance = nf.format(amount);

            balance = balance.replaceFirst(" €", "");

            if (!PrisonEscape.getEconomy().has(p, amount)){
                p.sendMessage(ChatColor.RED + "Jij hebt geen §f€" + balance + "§c.");
                return true;
            }

            p.sendMessage(ChatColor.WHITE + "Je hebt §c€" + balance + " §fovergemaakt naar §c" + player.getName() + "§f.");
            Player temp = Bukkit.getPlayer(speler);
            temp.sendMessage(ChatColor.WHITE + "Je hebt §c€" + balance + " §fontvangen van §c" + p.getName() + "§f.");

            PrisonEscape.getEconomy().withdrawPlayer(p, amount);
            PrisonEscape.getEconomy().depositPlayer(onlinePlayer, amount);

            ScoreboardUtil util = new ScoreboardUtil(p);
            util.show();

            ScoreboardUtil util2 = new ScoreboardUtil(onlinePlayer);
            util2.show();
            return true;
        }

        if (args.length > 2){
            p.sendMessage(ChatColor.RED + "Gebruik /" + s + " <naam> <bedrag>");
            return true;
        }
        return false;
    }
}
