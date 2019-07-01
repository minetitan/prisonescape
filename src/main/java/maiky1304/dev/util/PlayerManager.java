package maiky1304.dev.util;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import maiky1304.dev.PrisonEscape;
import maiky1304.dev.events.PlayerConnectionEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    /**
     * @param plugin
     * Plugin is the instance of the Main class
     */
    private PrisonEscape plugin = PrisonEscape.getPlugin(PrisonEscape.class);

    private static UUID uuid;
    /**
     * @param uuid
     * UUID is the Unique Identifier of a user that is being used to save player data in the MySQL database
     */

    private Player p;

    public PlayerManager(UUID uuid){
        this.uuid = uuid;
        this.p = Bukkit.getPlayer(uuid);

        //setupScoreboard();
    }


    /**
     * @apiNote
     * TODO: Create user asynchrounsly
     */
    public void createUser(){
        plugin.getUsers().getConfig().set(uuid.toString() + ".job", "Gevangene");
        plugin.getUsers().getConfig().set(uuid.toString() + ".prefix", "none");
        plugin.getUsers().getConfig().set(uuid.toString() + ".naamkleur", "7");
        plugin.getUsers().getConfig().set(uuid.toString() + ".username", Bukkit.getPlayer(uuid).getName());
        plugin.getUsers().getConfig().set(uuid.toString() + ".time.days", 0);
        plugin.getUsers().getConfig().set(uuid.toString() + ".time.hours", 0);
        plugin.getUsers().getConfig().set(uuid.toString() + ".time.minutes", 0);
        plugin.getUsers().getConfig().set(uuid.toString() + ".time.seconds", 0);
        plugin.getUsers().saveConfig();

        Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Je data is opgeslagen je kunt nu jouw avontuur beginnen in de grote Prison!"));
        Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&a&lMine&2&lTitan&8] &7Lukt het jou om de ontsnappen?"));
        PlayerConnectionEvent.getList().remove(Bukkit.getPlayer(uuid));
    }

    public static UUID getUUID() {
        return uuid;
    }


    public boolean existsInDatabase() {
        if (plugin.getUsers().getConfig().contains(uuid.toString())){
            return true;
        }else{
            return false;
        }
    }

    public int getDays(){
        return plugin.getUsers().getConfig().getInt(uuid.toString() + ".time.days");
    }

    public int getHours(){
        return plugin.getUsers().getConfig().getInt(uuid.toString() + ".time.hours");
    }

    public String getCurrentRegion(){
        LocalPlayer localPlayer = PrisonEscape.getWorldGuard().wrapPlayer(Bukkit.getPlayer(uuid));
        Vector playerVector = localPlayer.getPosition();
        RegionManager regionManager = PrisonEscape.getWorldGuard().getRegionManager(Bukkit.getPlayer(uuid).getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);

        for (ProtectedRegion region : applicableRegionSet){
            if (applicableRegionSet == null){
                return "Geen";
            }

            if (region == null){
                return "Geen";
            }

            String str = region.getId();

            char ch[] = str.toCharArray();
            for (int i = 0; i < str.length(); i++) {

                // If first character of a word is found
                if (i == 0 && ch[i] != ' ' ||
                        ch[i] != ' ' && ch[i - 1] == ' ') {

                    // If it is in lower-case
                    if (ch[i] >= 'a' && ch[i] <= 'z') {

                        // Convert into Upper-case
                        ch[i] = (char)(ch[i] - 'a' + 'A');
                    }
                }

                // If apart from first character
                // Any one is in Upper-case
                else if (ch[i] >= 'A' && ch[i] <= 'Z')

                    // Convert into Lower-Case
                    ch[i] = (char)(ch[i] + 'a' - 'A');
            }

            // Convert the char array to equivalent String
            String st = new String(ch);

            return st;
        }
        return "Geen";
    }

    public int getMinutes(){
        if (!existsInDatabase()){
            return 0;
        }
        return plugin.getUsers().getConfig().getInt(uuid.toString() + ".time.minutes");
    }

    public int getSeconds(){
        if (!existsInDatabase()){
            return 0;
        }
        return plugin.getUsers().getConfig().getInt(uuid.toString() + ".time.seconds");
    }

    /**
     * This is the amount of minutes
     * @param i
     */

    public void setMinutes(int i){
        if (!existsInDatabase()){
            return;
        }

        plugin.getUsers().getConfig().set(uuid.toString() + ".time.minutes", i);
        plugin.getUsers().saveConfig();
    }

    public void setDays(int i){
        if (!existsInDatabase()){
            return;
        }

        plugin.getUsers().getConfig().set(uuid.toString() + ".time.days", i);
        plugin.getUsers().saveConfig();
    }

    public void setHours(int i){
        if (!existsInDatabase()){
            return;
        }

        plugin.getUsers().getConfig().set(uuid.toString() + ".time.hours", i);
        plugin.getUsers().saveConfig();
    }

    public void setSeconds(int i){
        if (!existsInDatabase()){
            return;
        }

        plugin.getUsers().getConfig().set(uuid.toString() + ".time.seconds", i);
        plugin.getUsers().saveConfig();
    }

    public void setJob(String job) {
        if (!existsInDatabase()){
            return;
        }
        plugin.getUsers().getConfig().set(uuid.toString() + ".job", job);
        plugin.getUsers().saveConfig();
    }

    public String getJob(){
        if (!existsInDatabase()){
            return "?";
        }
        return plugin.getUsers().getConfig().getString(uuid.toString() + ".job");
    }

    public void setPrefix(String prefix) {
        if (!existsInDatabase()){
            return;
        }
        plugin.getUsers().getConfig().set(uuid.toString() + ".prefix", prefix);
        plugin.getUsers().saveConfig();
    }

    public String getPrefix(){
        if (!existsInDatabase()){
            return "?";
        }
        return plugin.getUsers().getConfig().getString(uuid.toString() + ".prefix");
    }

    public void setNaamkleur(String job) {
        if (!existsInDatabase()){
            return;
        }

        plugin.getUsers().getConfig().set(uuid.toString() + ".naamkleur", job);
        plugin.getUsers().saveConfig();
    }

    public String getNaamkleur(){
        if (!existsInDatabase()){
            return "7";
        }
       return plugin.getUsers().getConfig().getString(uuid.toString() + ".naamkleur");
    }

    public boolean takeMoney(double amount){
        if (!PrisonEscape.getEconomy().has(Bukkit.getPlayer(uuid), amount)){
            return false;
        }

        PrisonEscape.getEconomy().withdrawPlayer(Bukkit.getPlayer(uuid), amount);
        return true;
    }

    public void depositMoney(double amount){
        PrisonEscape.getEconomy().depositPlayer(Bukkit.getPlayer(uuid), amount);
    }

    public double getBalance(){
        return PrisonEscape.getEconomy().getBalance(Bukkit.getPlayer(uuid));
    }

}
