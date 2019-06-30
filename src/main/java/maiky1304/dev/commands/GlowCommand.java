package maiky1304.dev.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player)sender;

            if (p.getItemInHand().getEnchantments().size() != 0){
                if (p.getItemInHand().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_ENCHANTS)){
                    if (!p.hasPermission("prisonescape.admin")){
                        p.sendMessage(ChatColor.RED + "Je hebt onvoldoende rechten om deze acite uit te voeren!");
                        return true;
                    }

                    p.getItemInHand().removeEnchantment(Enchantment.DURABILITY);

                    ItemMeta meta = p.getItemInHand().getItemMeta();
                    meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    p.getItemInHand().setItemMeta(meta);

                    p.sendMessage(ChatColor.RED + "[ADMIN] Je hebt de glow van je item afgehaald!");
                    return true;
                }
            }

            if (!p.hasPermission("prisonescape.admin")){
                p.sendMessage(ChatColor.RED + "Je hebt onvoldoende rechten om deze acite uit te voeren!");
                return true;
            }

            if (p.getItemInHand().getType() == Material.valueOf("AIR")){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[ADMIN] Je kunt geen lucht glow geven."));
                return true;
            }

            p.getItemInHand().addUnsafeEnchantment(Enchantment.DURABILITY, 1);

            ItemMeta meta = p.getItemInHand().getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            p.getItemInHand().setItemMeta(meta);

            p.sendMessage(ChatColor.RED + "[ADMIN] Je item heeft nu een glow deze kun je weer weghalen doormiddel van nog een keer /" + label +" te doen!");
            return true;
        }
        return false;
    }
}
