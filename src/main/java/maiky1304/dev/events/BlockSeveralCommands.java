package maiky1304.dev.events;

import org.bukkit.command.TabCompleter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.TabCompleteEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockSeveralCommands implements Listener {

    String returnMessage = "§cJe hebt onvoldoende rechten om deze actie uit te voeren.";

    List<String> blocked = Arrays.asList("/about",
            "/plugins",
            "/pl",
            "$/plugman",
            "/?",
            "/tell",
            "/me",
            "/about");

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e){
        for (String c : blocked){
            if (getFirstWord(e.getMessage()).equalsIgnoreCase(c) || e.getMessage().startsWith("/minecraft:")){
                e.setCancelled(true);
                e.getPlayer().sendMessage(this.returnMessage);
            }
        }
    }

    @EventHandler
    public void onTab(PlayerChatTabCompleteEvent e){
        if (e.getChatMessage().equalsIgnoreCase("/none")){
            e.getTabCompletions().clear();
            e.getPlayer().sendMessage(this.returnMessage);
        }
    }

    private String getFirstWord(String text) {
        int index = text.indexOf(' ');
        if (index > -1) { // Check if there is more than one word.
            return text.substring(0, index); // Extract first word.
        } else {
            return text; // Text is the first word itself.
        }
    }

}
