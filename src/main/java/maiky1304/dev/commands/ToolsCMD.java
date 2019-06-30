package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.events.HookedWithToolsEvents;
import maiky1304.dev.storage.ConfigurationHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ToolsCMD implements CommandExecutor {

    private PrisonEscape plugin;

    /**
     *
     * @param plugin
     */

    public ToolsCMD(PrisonEscape plugin){
        setPlugin(plugin);
    }

    public PrisonEscape getPlugin() {
        return this.plugin;
    }

    /**
     *
     * @param plugin
     */

    public void setPlugin(PrisonEscape plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        List<java.lang.String> commandList = new ArrayList<>();

        commandList.add("list");
        commandList.add("get");
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Je kunt dit command niet gebruiken vanuit console.");
            return true;
        }

        Player p = (Player)sender;

        if (args.length == 0){
            p.sendMessage(ChatColor.RED + "Mogelijke sub-commando's voor /" + label);

            for (String command : commandList){
                p.sendMessage(ChatColor.RED + "/" + label + " " + command);
            }

            return true;
        }

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("list")){
                p.sendMessage(ChatColor.RED + "Tools die gesaved zijn in de configuratie:");

                if (PrisonEscape.getTools().getConfig().getKeys(false).size() == 0){
                    p.sendMessage(ChatColor.RED + "- Geen voeg deze toe aan tools.yml");
                    return true;
                }

                for (String tool : PrisonEscape.getTools().getConfig().getKeys(false)){
                    p.sendMessage(ChatColor.RED + "- " + tool);
                }
                return true;
            }else if (args[0].equalsIgnoreCase("get")){
                p.sendMessage(ChatColor.RED + "Typ nu de naam van de tool die je wilt in de chat.");
                HookedWithToolsEvents.list.put(p, p.getUniqueId());
                return true;
            }else{
                p.sendMessage(ChatColor.RED + "Onbekend sub-commando gebruik /" + label);
            }
        }else{
            p.sendMessage(ChatColor.RED + "Onbekend sub-commando gebruik /" + label);
        }
        return false;
    }
}
