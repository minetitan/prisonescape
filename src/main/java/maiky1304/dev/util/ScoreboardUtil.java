package maiky1304.dev.util;

import maiky1304.dev.PrisonEscape;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScoreboardUtil {

    private static final PrisonEscape plugin = PrisonEscape.getPlugin(PrisonEscape.class);

    private Player p;

    /**
     * Player that is used in the class.
     * @param p
     */

    public ScoreboardUtil(Player p){
        this.p = p;
    }

    public void show(){
        if (plugin.getConfig().getStringList("settings.scoreboard-lines") == null){
            return;
        }

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        /**
         * Dummy = Name of scoreboard
         * @param dummy
         * @param minecraft
         * Minecraft = Type of scoreboard
         */

        Objective objective = board.registerNewObjective("dummy", "minecraft");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("settings.scoreboard-title")));

        /**
         * Get amount of lines in the scoreboard
         */
        int size = plugin.getConfig().getStringList("settings.scoreboard-lines").size();


        /**
         * Put the lines into the scoreboard by using the objectives
         */
        for (String line : plugin.getConfig().getStringList("settings.scoreboard-lines")){
            String newLine = line;

            PlayerManager pm = new PlayerManager(p.getUniqueId());

            String group = PrisonEscape.getPermissions().getPrimaryGroup(p);

            if (group.equalsIgnoreCase("eig")){
                group = "§4Eigenaar";
            }
            if (group.equalsIgnoreCase("admin")){
                group = "§cAdmin";
            }
            if (group.equalsIgnoreCase("builder")){
                group = "§aBouwteam";
            }
            if (group.equalsIgnoreCase("default")){
                group = "Geen rank";
            }

            Locale locale = new Locale("de", "DE");

            NumberFormat nf = NumberFormat.getCurrencyInstance(locale);

            String balance = nf.format(PrisonEscape.getEconomy().getBalance(p));
            balance = balance.replaceFirst(" €", "");

            newLine = newLine.replaceAll("\\{user}", p.getName());
            newLine = newLine.replaceAll("\\{rank}", group);
            newLine = newLine.replaceAll("\\{onlinetime}", pm.getDays() + "d, " + pm.getHours() + "u, " + pm.getMinutes() + "min");
            newLine = newLine.replaceAll("\\{money}", "€" + balance);
            newLine = newLine.replaceAll("\\{regionname}", pm.getCurrentRegion());
            newLine = newLine.replaceAll("\\{job}", pm.getJob());

            objective.getScore(ChatColor.translateAlternateColorCodes('&', newLine)).setScore(size);

            size -= 1;
        }

        List<String> colors = new ArrayList<>();

        for (char c : ChatColor.ALL_CODES.toCharArray()){
            colors.add(String.valueOf(c));
        }

        if (!board.getTeams().contains(colors.get(0))) {
            for (String color : colors){
                board.registerNewTeam(color);
                board.getTeam(color).setPrefix("§" + color);
                Bukkit.getConsoleSender().sendMessage("Created team " + color);
            }
        }

        PlayerManager pm = new PlayerManager(p.getUniqueId());
        board.getTeam(plugin.getUsers().getConfig().getString(p.getUniqueId().toString() + ".naamkleur")).addEntry(p.getName());


        /**
         * Set the player his/her's scoreboard to the scoreboard that was made here.
         */
        p.setScoreboard(board);
    }

}
