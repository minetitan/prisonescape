package maiky1304.dev.util;

import maiky1304.dev.PrisonEscape;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class TeamAssigner {

    private PrisonEscape plugin;
    private final Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

    public TeamAssigner(PrisonEscape plugin){
        this.plugin = plugin;
    }

    public final void registerTeams(){
        List<String> colors = new ArrayList<>();

        for (char c : ChatColor.ALL_CODES.toCharArray()){
            colors.add(String.valueOf(c));
        }

        for (String color : colors){
            board.registerNewTeam(color);
            board.getTeam(color).setPrefix("§" + color);
            Bukkit.getConsoleSender().sendMessage("Created team " + color);
        }
    }

    public final void unregisterTeams(){
        List<String> colors = new ArrayList<>();

        for (char c : ChatColor.ALL_CODES.toCharArray()){
            colors.remove(String.valueOf(c));
        }

        for (String color : colors){
            board.getTeam(color).unregister();
        }
    }

    public void addPlayer(Player p){
        PlayerManager pm = new PlayerManager(p.getUniqueId());
        board.getTeam(plugin.getUsers().getConfig().getString(p.getUniqueId().toString() + ".naamkleur")).addEntry(p.getName());
        p.setScoreboard(board);
    }

    public Scoreboard getBoard(){
        return board;
    }
}
