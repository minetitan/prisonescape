package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class HookedWithToolsEvents implements Listener {

    static public HashMap<Player,UUID> list = new HashMap<Player, UUID>();


    @EventHandler
    public void toolHandler(AsyncPlayerChatEvent e){
        if (!list.containsValue(e.getPlayer().getUniqueId())){
            e.setCancelled(false);
        }else{
            e.setCancelled(true);

            if (e.getMessage().equalsIgnoreCase("cancel")){
                return;
            }

            if (PrisonEscape.getTools().getConfig().contains(e.getMessage())){
                e.getPlayer().sendMessage(ChatColor.GREEN + "Je hebt zojuist de tool " + e.getMessage() + " ontvangen in je inventory.");

                ItemStack giveItem = PrisonEscape.getTools().getConfig().getItemStack(e.getMessage() + ".itemstack");
                e.getPlayer().getInventory().addItem(giveItem);
                list.remove(e.getPlayer());
            }else{
                e.getPlayer().sendMessage(ChatColor.RED + "Deze tool bestaat niet!");
                list.remove(e.getPlayer());
            }
        }
    }

}
