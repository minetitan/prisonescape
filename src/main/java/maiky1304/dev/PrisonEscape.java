package maiky1304.dev;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import maiky1304.dev.commands.*;
import maiky1304.dev.events.*;
import maiky1304.dev.storage.ConfigurationHandler;
import maiky1304.dev.util.RunnerInstance;
import maiky1304.dev.util.ScoreboardUtil;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.logging.Logger;

public final class PrisonEscape extends JavaPlugin {


    private static ConfigurationHandler users;
    private static ConfigurationHandler tools;
    private static ConfigurationHandler kleding;
    private static ConfigurationHandler wapens;
    private static ConfigurationHandler pickaxes;
    private static ConfigurationHandler lootchests;

    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        users = new ConfigurationHandler(this, "users.yml");
        users.loadConfig();

        tools = new ConfigurationHandler(this, "tools.yml");
        tools.loadConfig();

        kleding = new ConfigurationHandler(this, "kleding.yml");
        kleding.loadConfig();

        wapens = new ConfigurationHandler(this, "wapens.yml");
        wapens.loadConfig();

        pickaxes = new ConfigurationHandler(this, "pickaxes.yml");
        pickaxes.loadConfig();

        lootchests = new ConfigurationHandler(this, "lootchests.yml");
        lootchests.loadConfig();

        for (String key : getTools().getConfig().getKeys(false)){
            if (key.equalsIgnoreCase("tang")){
                if (!getTools().getConfig().contains(key + ".itemstack")){
                    ItemStack item = new ItemStack(Material.valueOf(getTools().getConfig().getString(key + ".item").toUpperCase()), 1, (short)0);
                    ItemMeta meta = item.getItemMeta();

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                            "&6&lTang"));

                    meta.setLore(Arrays.asList(
                            "§7",
                            "§7Je kunt deze §6tang nog §64 §7keer gebruiken."
                    ));

                    item.setItemMeta(meta);
                    getTools().getConfig().set(key + ".itemstack", item);
                    getTools().saveConfig();
                }
            }
        }

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

        RunnerInstance ri = new RunnerInstance(this);
        ri.Time_Update_Start();

        for (Player p : Bukkit.getOnlinePlayers()){
           // ri.Scoreboard_Thread_Start(p);

            ScoreboardUtil util = new ScoreboardUtil(p);
            util.show();
        }

        Bukkit.getPluginManager().registerEvents(new PlayerConnectionEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerAsyncChat(), this);
        Bukkit.getPluginManager().registerEvents(new SmartMovingDetection(), this);
        Bukkit.getPluginManager().registerEvents(new HookedWithToolsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new BreakHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ItemGUI_Events(), this);
        Bukkit.getPluginManager().registerEvents(new DoorSystemEvents(), this);
        Bukkit.getPluginManager().registerEvents(new LootChest(), this);

        registerCommand(new GlowCommand(), "glow");
        registerCommand(new RenameCommand(), "rename");
        registerCommand(new SetNaamkleur(), "setnaamkleur");
        registerCommand(new ToolsCMD(this), "tools");
        registerCommand(new ItemDatabase(), "prisonitems");
        registerCommand(new TimeCommand(), "time");
        registerCommand(new KeyCommand(), "sleutel");
        registerCommand(new PayCommand(), "pay");
        registerCommand(new LootchestCommand(), "lootchest");
        registerCommand(new SetPrefixCommand(), "setprefix");
        registerCommand(new SetBaanCommand(), "setbaan");
    }

    @Override
    public void onDisable(){
        getConfig().set("temp.teamCreated", false);
        saveConfig();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static ConfigurationHandler getLootchests() {
        return lootchests;
    }

    public static ConfigurationHandler getTools() {
        return tools;
    }

    public static ConfigurationHandler getUsers() {
        return users;
    }

    public static ConfigurationHandler getKleding() {
        return kleding;
    }

    public static ConfigurationHandler getPickaxes() {
        return pickaxes;
    }

    public static ConfigurationHandler getWapens() {
        return wapens;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldGuardPlugin) plugin;
    }

    public static WorldEditPlugin getWorldEdit() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null; // Maybe you want throw an exception instead
        }

        return (WorldEditPlugin) plugin;
    }


    public void registerCommand(CommandExecutor exe, String commando){
        getCommand(commando).setExecutor(exe);
    }
}
