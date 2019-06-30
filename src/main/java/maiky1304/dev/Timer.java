package maiky1304.dev;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    PrisonEscape plugin;
    private int seconds;
    private int minutes;

    public Timer(PrisonEscape plugin){
        setPlugin(plugin);
    }

    public void runTimer(){
        Bukkit.getScheduler().runTaskTimer(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                seconds += 1;

                if (seconds >= 60){
                    seconds = 0;
                    minutes += 1;
                }

                if (minutes >=60){
                    minutes = 0;
                    seconds = 0;
                }
            }
        },0,20L);
    }

    public String getTimer(){
        String output;

        if (minutes < 10){
            output = "0" + minutes + ":";
        }else{
            output = minutes + ":";
        }

        if (seconds < 10){
            output = output + "0" + seconds;
        }else{
            output = output + seconds;
        }

        return output;
    }

    public void setPlugin(PrisonEscape plugin) {
        this.plugin = plugin;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
