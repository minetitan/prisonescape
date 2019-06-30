package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.util.ItemGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * This code as been written by Maiky1304 you are not allowed to use this code in your plugins
 * Package name maiky1304.dev.commands
 */

public class ItemDatabase implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player){
            Player p =(Player)commandSender;

            if (args.length == 0){
                ItemGUI gui = new ItemGUI(p);
                gui.openCategories();
            }else if (args.length > 0){
                if (args[0].equalsIgnoreCase("add")){
                    if (args[1].equalsIgnoreCase("pickaxe")){
                        ItemStack item = p.getItemInHand();

                        int nextInt = PrisonEscape.getPickaxes().getConfig().getConfigurationSection("pickaxes").getKeys(false).size() + 1;

                        PrisonEscape.getPickaxes().getConfig().set("pickaxes." + nextInt + ".itemStack", item);
                        PrisonEscape.getPickaxes().saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fJe hebt &a" + item.getItemMeta().getDisplayName() + " &ftoegevoegd aan de pickaxes."));
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("kleding")){
                        ItemStack item = p.getItemInHand();

                        int nextInt = PrisonEscape.getKleding().getConfig().getConfigurationSection("kleding").getKeys(false).size() + 1;

                        PrisonEscape.getKleding().getConfig().set("kleding." + nextInt + ".itemStack", item);
                        PrisonEscape.getKleding().saveConfig();
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fJe hebt &a" + item.getItemMeta().getDisplayName() + " &ftoegevoegd aan de kleding."));
                        return true;
                    }
                }else if (args[0].equalsIgnoreCase("reload")){
                    PrisonEscape.getPickaxes().loadConfig();
                    PrisonEscape.getKleding().loadConfig();
                    PrisonEscape.getWapens().loadConfig();
                    p.sendMessage(ChatColor.GREEN + "De config is herladen!");
                    return true;
                }
            }
            return true;
        }
        return false;
    }
}
