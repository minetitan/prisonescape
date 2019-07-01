package maiky1304.dev.events;

import maiky1304.dev.util.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerAsyncChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();

        PlayerManager pm = new PlayerManager(p.getUniqueId());

        String job = pm.getJob();
        String kleur = pm.getNaamkleur();
        String chatkleur = "&7";

        String format = "";

        if (pm.getPrefix().equalsIgnoreCase("none")){
            format = format + "&8[&7" + job + "&8]";
        }else{
            format = format + "&8[&7" + pm.getPrefix() + "&8] " + "&8[&7" + job + "&8]";
        }

        if (kleur == "7"){
            format = format + " &" + kleur + p.getName() + ": %s";
        }else{
            format = format + " &" + kleur + p.getName() + ":&f %s";
        }

        format = String.format(format, e.getMessage());

        e.setFormat(ChatColor.translateAlternateColorCodes('&', format));
    }

}
