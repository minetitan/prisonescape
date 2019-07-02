package maiky1304.dev.events;

import maiky1304.dev.PrisonEscape;
import maiky1304.dev.util.PlayerManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AdminDoors implements Listener {

    List<String> admins = DoorSystemEvents.getOverrideNames();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (e.getClickedBlock().getType().toString().contains("DOOR")){
                org.bukkit.Location blockUnder = new Location(e.getClickedBlock().getWorld(), e.getClickedBlock().getX(), e.getClickedBlock().getY()-1, e.getClickedBlock().getZ());
                Block block = e.getClickedBlock().getWorld().getBlockAt(blockUnder);


                if (block.getType() == Material.IRON_DOOR_BLOCK){
                    BlockState state = block.getState();

                    Door door = (Door)state.getData();

                    PlayerManager pm = new PlayerManager(p.getUniqueId());

                    if (admins.contains(p.getName().toLowerCase())) {
                        if (door.isOpen()) {
                            ((Openable) door).setOpen(false);
                            state.update();

                            e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 3.0F, 0.5F);
                        } else {
                            ((Openable) door).setOpen(true);
                            state.update();

                            e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 3.0F, 0.5F);

                            Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
                                @Override
                                public void run() {

                                    BlockState state2 = block.getState();

                                    Door door2 = (Door) state2.getData();

                                    if (door2.isOpen()) {
                                        ((Openable) door2).setOpen(false);
                                        state2.update();

                                        e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 3.0F, 0.5F);
                                    }
                                    this.cancel();
                                }
                            }, 20L * 3L);
                        }
                    }
                }else {
                    if (e.getClickedBlock().getType() == Material.IRON_DOOR_BLOCK) {
                        BlockState state = e.getClickedBlock().getState();

                        Door door = (Door) state.getData();

                        PlayerManager pm = new PlayerManager(p.getUniqueId());

                        if (admins.contains(p.getName().toLowerCase())) {
                            if (door.isOpen()) {
                                ((Openable) door).setOpen(false);
                                state.update();

                                p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgesloten§f.");
                                e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 3.0F, 0.5F);
                            } else {
                                ((Openable) door).setOpen(true);
                                state.update();

                                e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 3.0F, 0.5F);

                                p.sendMessage(ChatColor.WHITE + "Je hebt deze deur zojuist §cgeopend§f.");
                                p.sendMessage(ChatColor.WHITE + "De deur wordt over §c5 §fseconden automatisch gesloten!");

                                Bukkit.getScheduler().runTaskLater(PrisonEscape.getPlugin(PrisonEscape.class), new BukkitRunnable() {
                                    @Override
                                    public void run() {

                                        BlockState state2 = e.getClickedBlock().getState();

                                        Door door2 = (Door) state2.getData();

                                        if (door2.isOpen()) {
                                            ((Openable) door2).setOpen(false);
                                            state2.update();

                                            e.getClickedBlock().getWorld().playSound(e.getClickedBlock().getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 3.0F, 0.5F);

                                            p.sendMessage(ChatColor.WHITE + "De deur is zojuist automatisch §cgesloten§f.");
                                        }
                                        this.cancel();
                                    }
                                }, 20L * 3L);
                            }
                        }
                    }
                }
            }
        }
    }

}
