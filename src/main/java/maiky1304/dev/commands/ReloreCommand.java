package maiky1304.dev.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReloreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.RED + "Gebruik /" + s + " <string/integer> [string]");
            return true;
        }

        if (args.length > 0){
            Player p = (Player)sender;

            StringBuilder sb = new StringBuilder();

            String lore = null;

            if (args.length > 1){
                for (int i = 1; i != args.length; i++) {
                    sb.append(args[i] + " ");
                }

                lore = sb.toString().substring(0, sb.toString().length() - 1);
            }

            boolean valid = false;
            String input = args[0];
            int tryInt;


            try {
                tryInt = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e){
                tryInt = 0;
            }

            if (valid){
                List<String> oldLore = p.getItemInHand().getItemMeta().getLore();
                if (tryInt > oldLore.size()-1){
                    for (int i = 0; i < oldLore.size(); i++){
                        oldLore.add(" ");
                    }
                }
                oldLore.set(tryInt, ChatColor.translateAlternateColorCodes('&', lore));

                ItemMeta meta = p.getItemInHand().getItemMeta();
                meta.setLore(oldLore);

                p.getItemInHand().setItemMeta(meta);

                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cJe hebt succesvol de lore veranderd van het item in je hand."));
                return true;
            }else{
                if (p.getItemInHand().getType() != Material.AIR || p.getItemInHand() != null) {
                    ItemMeta meta = p.getItemInHand().getItemMeta();
                    meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', lore)));

                    p.getItemInHand().setItemMeta(meta);

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cJe hebt succesvol de lore veranderd van het item in je hand."));
                    return true;
                }
            }
        }
        return false;
    }
}
