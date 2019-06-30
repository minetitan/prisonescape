package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.Timer;
import maiky1304.dev.util.PlayerManager;
import maiky1304.dev.util.ScoreboardUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerConnectionEvent implements Listener {

    private static List<Player> list = new ArrayList<>();
    private static Timer timer = new Timer(PrisonEscape.getPlugin(PrisonEscape.class));

    public static Timer getTimer() {
        return timer;
    }

    private String scoreboardTitle = "Test";

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PlayerManager pm = new PlayerManager(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = (Player)event.getPlayer();
        PlayerManager pm = new PlayerManager(p.getUniqueId());
        // @param startLevel : Level is 1
        if (!pm.existsInDatabase()) {
            list.add(p);
        }

        if (!pm.existsInDatabase()){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Welkom op &fPrison Escape&7!"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Je starter data wordt nu opgeslagen in de database..."));
            pm.createUser();
        }else{
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Welkom op terug &fPrison Escape&7!"));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Je data wordt nu ingeladen in de database..."));
        }

        PrisonEscape.getTeamAssigner().addPlayer(p);

        ScoreboardUtil util = new ScoreboardUtil(p);
        util.show();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        if (list.contains(e.getPlayer())){
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "Je kunt niet lopen of interacten met je inventory zolang je aan het inladen bent!");
        }
    }

    public static List<Player> getList() {
        return list;
    }
}
