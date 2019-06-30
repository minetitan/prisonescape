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
        }
    }

    public void addPlayer(Player p){
        PlayerManager pm = new PlayerManager(p.getUniqueId());
        board.getTeam(pm.getNaamkleur()).addEntry(p.getName());
        p.setScoreboard(board);
    }
}
