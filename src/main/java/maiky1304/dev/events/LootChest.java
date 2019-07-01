package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LootChest implements Listener {

    HashMap<Location,Long> lootChestCooldowns = new HashMap<Location,Long>();
    int cooldownInSeconds = 300;

    /*
    was 349
    is nu 390

    nu - oud  >= cooldown
     */

    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if (e.getInventory().getName().equalsIgnoreCase("Lootchest")){
           Block chest = e.getPlayer().getTargetBlock((Set<Material>)null, 200);
           if (chest.getType() != Material.CHEST){
               return;
           }

           Chest c = (Chest)chest.getState();
           c.getBlockInventory().clear();

           chest.setType(Material.AIR);
           chest.getLocation().getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 2003);
           chest.getLocation().getWorld().spawnEntity(chest.getLocation(), EntityType.FIREWORK);

           Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
               @Override
               public void run() {
                   Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "setblock "  + chest.getLocation().getX() + " " + chest.getLocation().getY() + " " + chest.getLocation().getZ() + " minecraft:chest 1 replace {CustomName:\"Lootchest\"}");
               }
           },20L*cooldownInSeconds);
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e){
        if (e.getInventory().getType() == InventoryType.CHEST){
            if (e.getInventory().getName().equalsIgnoreCase("Lootchest")){
                int wrongGrade = 0;
                for (String type : PrisonEscape.getLootchests().getConfig().getConfigurationSection("loot-type").getKeys(false)){
                    if (PrisonEscape.getLootchests().getConfig().getConfigurationSection("loot-type." + type).getKeys(false).size() == 0){
                        e.getPlayer().sendMessage(ChatColor.RED + "Je hebt loot chest type " + type + " nog niet gesetupt in de config doe dit met /lootchest add <normal/rare/epic>");
                        wrongGrade += 1;
                    }
                }

                if (wrongGrade > 0){
                    return;
                }

                int possibilities_Type = PrisonEscape.getLootchests().getConfig().getConfigurationSection("loot-type").getKeys(false).size();

                int choice = randomInt(1, possibilities_Type);

                String choiceString;

                if (choice == 1){
                    choiceString = "normal";
                }else if (choice == 2){
                    choiceString = "rare";
                }else if (choice == 3){
                    choiceString = "epic";
                }else{
                    choiceString = "";
                }

                int choicesOfItems = PrisonEscape.getLootchests().getConfig().getConfigurationSection("loot-type." + choiceString).getKeys(false).size();

                int choice1 = 0,choice2 = 0,choice3 = 0,choice4 = 0,choice5 = 5;

                int next = 1;

                // 5 items willen we
                for (int i = 0; i < 6; i++){
                    if (next == 1){
                        choice1 = randomInt(next, choicesOfItems);
                    }
                    if (next == 2) {
                        choice2 = randomInt(next, choicesOfItems);
                    }
                    if (next == 3){
                        choice3 = randomInt(next, choicesOfItems);
                    }
                    if (next == 4){
                        choice4 = randomInt(next, choicesOfItems);
                    }
                    if (next == 5){
                        choice5 = randomInt(next, choicesOfItems);
                    }
                    next += 1;
                }

                int itemID = 1;
                for (int i = 0; i < 5; i++){
                    ItemStack one = PrisonEscape.getLootchests().getConfig().getItemStack("loot-type." + choiceString + "." + itemID);

                    e.getInventory().addItem(one);

                    if (itemID != 5){
                        itemID += 1;
                    }
                }

            }
        }
    }

    public int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }


}
