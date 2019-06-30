package maiky1304.dev.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

public class RenameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;
            if (!p.hasPermission("prisonescape.admin")){
                p.sendMessage(ChatColor.RED + "Je hebt onvoldoende rechten om deze acite uit te voeren!");
                return true;
            }

            if (args.length == 0){
                p.sendMessage(ChatColor.RED + "[ADMIN] Je moet wel een naam invullen voor je item!");
                return true;
            }

            if (args.length > 0){
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++){
                    builder.append(args[i]);
                    builder.append(" ");
                }

                String output = builder.toString();
                output = output.substring(0, output.length()-1);

                if (p.getItemInHand().getType() == Material.valueOf("AIR")){
                    p.sendMessage(ChatColor.RED + "[ADMIN] Je kunt lucht geen naam geven.");
                    return true;
                }

                ItemMeta current = p.getItemInHand().getItemMeta();
                current.setDisplayName(ChatColor.translateAlternateColorCodes('&', output));
                p.getItemInHand().setItemMeta(current);

                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&c[ADMIN] Je hebt je " + p.getItemInHand().getType().toString() + " de naam '" + output + "' gegeven!"));
                return true;
            }
        }
        return false;
    }
}
