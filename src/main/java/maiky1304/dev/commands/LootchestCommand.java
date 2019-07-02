package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LootchestCommand implements CommandExecutor {

    /**
     * @param sender The sender is the player who executed the command.
     * @param command The command is command that was used to execute.
     * @param s The s is the label that was used as command.
     * @param args The args is the arguments after the @param s
     * @return false
     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player){ Player p = (Player)sender;
            if (args.length == 0){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe moet het commando als volgt gebruiken &c/" + s + " add <normal/rare/epic> &fdit voegt het item dat je in je hand hebt toe aan de gekozen loto chest rarity."));
                return true;
            }

            if (args.length == 1){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe moet het commando als volgt gebruiken &c/" + s + " add <normal/rare/epic> &fdit voegt het item dat je in je hand hebt toe aan de gekozen loto chest rarity."));
                return true;
            }

            if (args.length == 2){
                if (args[0].equalsIgnoreCase("add")){
                    String type = args[1];


                    if (!PrisonEscape.getLootchests().getConfig().contains("loot-type." + type)){
                        p.sendMessage(ChatColor.RED + "Dit is geen geldig lootchest type.");
                        return true;
                    }

                    ItemStack item = p.getItemInHand();
                    int next = PrisonEscape.getLootchests().getConfig().getConfigurationSection("loot-type." + type).getKeys(false).size() + 1;

                    PrisonEscape.getLootchests().getConfig().set("loot-type." + type + "." + next, item);
                    PrisonEscape.getLootchests().saveConfig();

                    p.sendMessage(ChatColor.RED + "Het item in je hand is §lsuccesvol §r§copgeslagen!");
                    return true;
                }else if (args[0].equalsIgnoreCase("clear")) {
                    String type = args[1];

                    PrisonEscape.getLootchests().getConfig().set("loot-type." + type, null);
                    PrisonEscape.getLootchests().saveConfig();

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&cJe hebt succesvol de &citems &fvan &c" + type + " &fgereset!"));
                    return true;
                }else{
                    p.sendMessage(ChatColor.RED + "Ongeldig sub-commando probeer het later nog eens!");
                    return true;
                }
            }

            if (args.length > 2){
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe moet het commando als volgt gebruiken &c/" + s + " add <normal/rare/epic> &fdit voegt het item dat je in je hand hebt toe aan de gekozen loto chest rarity."));
                return true;
            }
        }
        return false;
    }
}
