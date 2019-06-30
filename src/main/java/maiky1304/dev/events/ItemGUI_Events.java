package maiky1304.dev.events;

import maiky1304.dev.util.ItemGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * This code as been written by Maiky1304 you are not allowed to use this code in your plugins
 * Package name maiky1304.dev.events
 */

public class ItemGUI_Events implements Listener {

    // Inventory Click Events van Categories

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if (e.getInventory().getName().equalsIgnoreCase("§aKies een categorie")){
            if (e.getCurrentItem().getType() == Material.GOLD_PICKAXE){
                ItemGUI gui = new ItemGUI((Player)e.getWhoClicked());
                gui.openPickaxes();
            }

            if (e.getCurrentItem().getType() == Material.IRON_HELMET){
                ItemGUI gui = new ItemGUI((Player)e.getWhoClicked());
                gui.openKleding();
            }
            e.setCancelled(true);
        }
    }

    // Inventory Click Events van Pickaxe cat.
    @EventHandler
    public void onInvClick2(InventoryClickEvent e){
        Player p = (Player)e.getWhoClicked();

        if (e.getInventory().getName().contains("Klik op een item!")){
            e.setCancelled(true);

            if (e.getRawSlot() > 44){
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().getType() != Material.PAPER && e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE){
                p.getInventory().addItem(e.getCurrentItem());

                String string = e.getCurrentItem().getItemMeta().getDisplayName() == null ? e.getCurrentItem().getType().toString() : e.getCurrentItem().getItemMeta().getDisplayName();

                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fEr is een item toegevoegd aan je inventory &a" + string + " " + e.getCurrentItem().getAmount() + "x&f."));
            }
        }
    }

}
