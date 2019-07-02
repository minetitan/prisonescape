package maiky1304.dev.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnerInstance {

    private JavaPlugin plugin;
    public RunnerInstance(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void Time_Update_Start(){
        Bukkit.getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    PlayerManager pm = new PlayerManager(p.getUniqueId());

                    int days = pm.getDays();
                    int hours = pm.getHours();
                    int min = pm.getMinutes();
                    int sec = pm.getSeconds();

                    pm.setSeconds(sec + 1);
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "nte player " + p.getName() + " prefix &" + pm.getNaamkleur());

                    if (sec >= 60){
                        pm.setSeconds(0);
                        pm.setMinutes(min + 1);

                        ScoreboardUtil util = new ScoreboardUtil(p);
                        util.show();
                    }

                    if (min >= 60){
                        pm.setMinutes(0);
                        pm.setHours(hours + 1);
                    }

                    if (hours >= 24){
                        pm.setHours(0);
                        pm.setDays(days + 1);
                    }
                }
            }
        },0L, 20L);
    }

}
