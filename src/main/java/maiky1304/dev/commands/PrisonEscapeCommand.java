package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;
import java.util.Map;

public class PrisonEscapeCommand implements CommandExecutor {

    private final PrisonEscape plugin = PrisonEscape.getPlugin(PrisonEscape.class);
    private final PluginDescriptionFile pdf = plugin.getDescription();
    private final String versionTag = pdf.getVersion();
    private final List<String> authors = pdf.getAuthors();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&m-------=[&r &c&lPrison Escape &f&m]=-------"));

            Map<String, Map<String,Object>> map = pdf.getCommands();


            for (String commando : map.keySet()){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/" + commando + " &8- &7" + plugin.getCommand(commando).getDescription()));
            }

            return true;
        }else if (args.length == 1){
            if (args[0].startsWith("ver")){
                executeVersion(sender);
                return true;
            }
        }else{
            executeVersion(sender);
            return true;
        }
        return false;
    }

    public void executeVersion(CommandSender sender){
        StringBuilder sb = new StringBuilder();

        for (String author : authors){
            sb.append(author + ", ");
        }

        String authors = sb.toString().substring(0, sb.toString().length()-2);

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPrisonEscape &fv" + versionTag + "&c."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cDeveloped door: &f" + authors + "&c."));
    }
}
