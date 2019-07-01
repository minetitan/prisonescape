package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.util.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;
import org.bukkit.block.BlockState;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

import static sun.plugin.javascript.navig.JSType.Location;

public class DoorSystemEvents implements Listener {

    private static final Material keyMaterial = Material.TRIPWIRE_HOOK;

    public static ItemStack getKey(String regionName){
        ItemStack item = new ItemStack(keyMaterial, 1, (short)0);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§fSleutel");
        meta.setLore(Arrays.asList(regionName));

        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK){
            if (e.getClickedBlock().getType().toString().contains("DOOR")){
                org.bukkit.Location blockUnder = new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getX(), e.getClickedBlock().getY()-1, e.getClickedBlock().getZ());
                Block block = e.getClickedBlock().getWorld().getBlockAt(blockUnder);


                if (block.getType() == Material.IRON_DOOR_BLOCK){
                    BlockState state = block.getState();

                    Door door = (Door)state.getData();

                    PlayerManager pm = new PlayerManager(p.getUniqueId());

                    if (p.getItemInHand().getType() == this.keyMaterial){
                        if (p.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase(pm.getCurrentRegion()) || p.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase("loper")){
                            if (door.isOpen()){
                                ((Openable) door).setOpen(false);
                                state.update();

                                p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgesloten§f.");
                            }else{
                                ((Openable) door).setOpen(true);
                                state.update();

                                p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgeopend§f.");
                                p.sendMessage(ChatColor.WHITE + "De deur wordt over §c5 §fseconden automatisch gesloten!");

                                Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        BlockState state2 = block.getState();

                                        Door door2 = (Door)state2.getData();

                                        if (door2.isOpen()){
                                            ((Openable) door2).setOpen(false);
                                            state2.update();

                                            p.sendMessage(ChatColor.WHITE + "De deur is zojuist automatisch §cgesloten§f.");
                                        }
                                        this.cancel();
                                    }
                                },20L*3L);
                            }
                        }else{
                            e.setCancelled(true);
                            p.sendMessage(ChatColor.RED + "Deze sleutel is niet bestemd voor deze deur!");
                            return;
                        }
                    }else{
                        p.sendMessage(ChatColor.RED + "Deze deur is op slot, gebruik een sleutel om deze deur te openen.");
                    }
                }else {
                    if (e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
                        BlockState state = e.getClickedBlock().getState();

                        Door door = (Door)state.getData();

                        PlayerManager pm = new PlayerManager(p.getUniqueId());

                        if (p.getItemInHand().getType() == this.keyMaterial){
                            if (p.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase(pm.getCurrentRegion()) || p.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase("loper")){
                                if (door.isOpen()){
                                    ((Openable) door).setOpen(false);
                                    state.update();

                                    p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgesloten§f.");
                                }else{
                                    ((Openable) door).setOpen(true);
                                    state.update();

                                    p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgeopend§f.");
                                    p.sendMessage(ChatColor.WHITE + "De deur wordt over §c5 §fseconden automatisch gesloten!");

                                    Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
                                        @Override
                                        public void run() {

                                            BlockState state2 = e.getClickedBlock().getState();

                                            Door door2 = (Door)state2.getData();

                                            if (door2.isOpen()){
                                                ((Openable) door2).setOpen(false);
                                                state2.update();

                                                p.sendMessage(ChatColor.WHITE + "De deur is zojuist automatisch §cgesloten§f.");
                                            }
                                            this.cancel();
                                        }
                                    },20L*5L);
                                }
                            }else{
                                e.setCancelled(true);
                                p.sendMessage(ChatColor.RED + "Deze sleutel is niet bestemd voor deze deur!");
                                return;
                            }
                        }else{
                            p.sendMessage(ChatColor.RED + "Deze deur is op slot, gebruik een sleutel om deze deur te openen.");
                        }
                    }
                }
            }
        }
    }


}
