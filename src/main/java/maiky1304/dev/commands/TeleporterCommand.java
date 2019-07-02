package maiky1304.dev.commands;

import maiky1304.dev.PrisonEscape;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.DecimalFormat;

public class TeleporterCommand implements CommandExecutor, Listener {

    String teleporterName = "&c&lTeleportatie";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " create <naam>");
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " delete <naam>");
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " setwarp <naam>");
                return true;
            } else if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " create <naam>");
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " delete <naam>");
                sender.sendMessage(ChatColor.RED + "Gebruik /" + label + " setwarp <naam>");
                return true;
            } else if (args.length == 2) {
                String name = args[1].toLowerCase();
                if (args[0].equalsIgnoreCase("create")) {
                    if (PrisonEscape.getTeleporters().getConfig().contains(name.toLowerCase())) {
                        p.sendMessage(ChatColor.RED + "Deze teleporter bestaat al!");
                        return true;
                    }

                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".x", 0.0);
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".y", 0.0);
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".z", 0.0);
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".yaw", 0.0);
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".pitch", 0.0);
                    PrisonEscape.getTeleporters().saveConfig();

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fJe hebt een teleporter genaamd &c" + name.toLowerCase() + " &caangemaakt!"));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fJe hoeft alleen nog &c/" + label + " setwarp " + name.toLowerCase() + " &fte doen op de gewenste spawn point!"));
                    return true;
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (!PrisonEscape.getTeleporters().getConfig().contains(name.toLowerCase())) {
                        p.sendMessage(ChatColor.RED + "Deze teleporter bestaat niet!");
                        return true;
                    }

                    PrisonEscape.getTeleporters().getConfig().set(name, null);
                    PrisonEscape.getTeleporters().saveConfig();

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fJe hebt de teleporter &c" + name.toLowerCase() + " &fverwijderd!"));
                    return true;
                } else if (args[0].equalsIgnoreCase("setwarp")) {
                    if (!PrisonEscape.getTeleporters().getConfig().contains(name.toLowerCase())) {
                        p.sendMessage(ChatColor.RED + "Deze teleporter bestaat nog niet!");
                        return true;
                    }

                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".x", p.getLocation().getX());
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".y", p.getLocation().getY());
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".z", p.getLocation().getZ());
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".yaw", p.getLocation().getYaw());
                    PrisonEscape.getTeleporters().getConfig().set(name.toLowerCase() + ".pitch", p.getLocation().getPitch());
                    PrisonEscape.getTeleporters().saveConfig();

                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fJe hebt de warp voor de teleporter &c" + name.toLowerCase() + " &fgezet!"));
                    return true;
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            "&fJe hebt een &congeldig &fargument opgegeven!"));
                    return true;
                }
            } else {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&fJe hebt een &congeldig &fargument opgegeven!"));
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onSign(SignChangeEvent e){
        if (e.getLine(0).equalsIgnoreCase("[Teleporter]")){
            String warpName = e.getLine(1);

            if (!PrisonEscape.getTeleporters().getConfig().contains(warpName)) {
                e.setCancelled(true);
                e.getBlock().breakNaturally();
            }

            DecimalFormat df = new DecimalFormat("###,###,##0.00");

            double x,y,z;
            x = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".x");
            y = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".y");
            z = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".z");

            e.setLine(0, ChatColor.translateAlternateColorCodes('&', teleporterName));
            e.setLine(1, ChatColor.translateAlternateColorCodes('&', warpName.toUpperCase()));
            e.setLine(2, df.format(x) + ";"  + df.format(y));
            e.setLine(3, df.format(z) + "");

            Block under = e.getBlock().getRelative(BlockFace.DOWN);
            under.setType(Material.IRON_PLATE);


            e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&cJe hebt een &fteleporter &csign aangemaakt!"));
        }
    }

    @EventHandler
    public void onInteractWithPressurePlate(PlayerInteractEvent e){
        if (e.getAction() == Action.PHYSICAL){
            if (e.getClickedBlock().getType() == Material.IRON_PLATE){
                Block blockAbove = e.getClickedBlock().getRelative(BlockFace.UP);
                if (blockAbove.getType() == Material.SIGN_POST || blockAbove.getType() == Material.WALL_SIGN){
                    Sign s = (Sign)blockAbove.getState();

                    if (s.getLine(0).contains("Teleportatie")){
                        String warpName = s.getLine(1).toLowerCase();

                        double x,y,z;
                        float yaw,pitch;
                        x = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".x");
                        y = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".y");
                        z = PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".z");
                        yaw = (float)PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".yaw");
                        pitch = (float)PrisonEscape.getTeleporters().getConfig().getDouble(warpName + ".pitch");

                        if (x == 0.0 && y == 0.0 && z == 0.0 && yaw == 0.0 && pitch == 0.0){
                            e.getPlayer().sendMessage(ChatColor.DARK_RED + "Deze teleporter is niet goed ingesteld meld dit bij een staflid het wordt zo snel mogelijk opgelost!");
                            return;
                        }

                        e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), x, y, z, yaw, pitch));
                        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&fJe bent geteleporteerd naar &c" + warpName + "&f."));
                    }
                }
            }
        }
    }
}
