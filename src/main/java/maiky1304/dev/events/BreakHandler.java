package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class BreakHandler implements Listener {

    short damage = 1;

    public short getDamage() {
        return damage;
    }

    public void setDamage(short damage) {
        this.damage = damage;
    }

    @EventHandler
    public void onBlock(BlockBreakEvent e){
        ItemMeta metaData = e.getPlayer().getItemInHand().getItemMeta();
        if (e.getBlock().getType() == Material.getMaterial(101)){
            if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lTang")){
                e.setCancelled(true);
                //      - Â§7Je kunt deze Â§6tang nog Â§64 Â§7keer gebruiken.

                ItemMeta meta = e.getPlayer().getItemInHand().getItemMeta();

                String replacement =  ChatColor.stripColor(meta.getLore().get(1));
                replacement = replacement.replace("Je kunt deze tang nog ", "");
                replacement = replacement.replace(" keer gebruiken.", "");

                int uses = 0;

                try {
                    uses = Integer.parseInt(replacement);
                } catch (NumberFormatException ex){
                    uses = 0;
                    e.getPlayer().sendMessage(ChatColor.RED + "Something wen't wrong while parsing " + replacement);
                }

                if (e.getBlock().getType() != Material.getMaterial(101)){
                    e.setExpToDrop(0);
                    e.setCancelled(true);
                }else{
                    meta.setLore(
                            Arrays.asList(
                                    meta.getLore().get(0),
                                    meta.getLore().get(1).replace(String.valueOf(uses), String.valueOf(uses-1))
                            )
                    );

                    e.getPlayer().getItemInHand().setItemMeta(meta);

                    e.getPlayer().sendMessage(ChatColor.GREEN + "Je hebt tralies gebroken dit blok spawnt weer terug in 10 minuten.");

                    if ((uses - 1) <= 0){
                        e.getPlayer().sendMessage(ChatColor.RED + "Oh nee! Je tool is gebroken koop een nieuwe of vind een nieuwe!");
                        e.getPlayer().setItemInHand(null);
                    }

                    String worldName = e.getPlayer().getWorld().getName();
                    Bukkit.getWorld(worldName).getBlockAt(e.getBlock().getLocation()).setType(Material.AIR);

                    Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.getWorld(worldName).getBlockAt(e.getBlock().getLocation()).setType(Material.getMaterial(101));
                        }
                    }, 20L*3); // 600L
                }

                // meta.setLore(Arrays.asList(meta.getLore().get(0), meta.getLore().get(1).replace("Â§7Je kunt deze Â§6tang nog Â§64 Â§7keer gebruiken.", "")));
            }
        }
    }

}
