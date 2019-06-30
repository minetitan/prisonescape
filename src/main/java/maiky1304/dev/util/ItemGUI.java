package maiky1304.dev.util;

import maiky1304.dev.PrisonEscape;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This code as been written by Maiky1304 you are not allowed to use this code in your plugins
 * Package name maiky1304.dev.util
 */

public class ItemGUI {

    private Player p;

    public ItemGUI(Player p){
        this.p = p;
    }

    public void openCategories(){
        Inventory inventory = Bukkit.createInventory(null, 3*9, "§aKies een categorie");

        inventory.setItem(11, new Item(Material.GOLD_PICKAXE,1, (short)0, "&aPickaxes", Arrays.asList(" ", "&7(&eTier I&7) &7t/m &7(&4Tier V&7)"), false, false).create());
        inventory.setItem(13, new Item(Material.IRON_HELMET, 1, (short)0, "&eArmor / Kledij", Arrays.asList(" ", "&7(&4Alle tier soorten&7)"), false, false).create());
        inventory.setItem(15, new Item(Material.INK_SACK, 1, (short)9, "&9Overig", Arrays.asList(" ", "&7(&eSpullen zoals Food, Explosieven, Wapens&7)"), false, false).create());

        ItemStack glass = new Item(Material.STAINED_GLASS_PANE, 1, (short)8, " ", Arrays.asList(""), false, true).create();

        for (int i = 0; i < 9; i++){
            inventory.setItem(i, glass);
        }

        for (int i = 8; i < 11; i++){
            inventory.setItem(i, glass);
        }

        for (int i = 16; i < 18; i++){
            inventory.setItem(i, glass);
        }

        for (int i = 18; i < 27; i++){
            inventory.setItem(i, glass);
        }

        inventory.setItem(12, glass);
        inventory.setItem(14, glass);

        p.openInventory(inventory);
    }

    public void openPickaxes(){
        Inventory inventory = Bukkit.createInventory(null, 5*9, "§aPickaxes §7- §fKlik op een item!");

        int counter = 0;

        if (PrisonEscape.getPickaxes().getConfig().getConfigurationSection("pickaxes").getKeys(false).size() == 0 || PrisonEscape.getPickaxes().getConfig().getConfigurationSection("pickaxes") == null){
            inventory.addItem(new Item(Material.PAPER, 1, (short)0, "&oVoeg pickaxes toe via de config!", Arrays.asList("In pickaxes.yml"), false, false).create());
        }else{
            for (String s : PrisonEscape.getPickaxes().getConfig().getConfigurationSection("pickaxes").getKeys(false)){
                inventory.addItem(PrisonEscape.getPickaxes().getConfig().getItemStack("pickaxes." + s + ".itemStack"));
                counter += 1;
            }
        }

        int num = inventory.getContents().length - counter;

        for (int i = counter; i < inventory.getContents().length; i++){
            inventory.setItem(i, new Item(Material.STAINED_GLASS_PANE,1 , (short)8, " ", Arrays.asList(" "), false, true).create());
        }

        p.openInventory(inventory);
    }

    public void openKleding(){
        Inventory inventory = Bukkit.createInventory(null, 5*9, "§aKleding §7- §fKlik op een item!");

        int counter = 0;

        if (PrisonEscape.getKleding().getConfig().getConfigurationSection("kleding").getKeys(false).size() == 0 || PrisonEscape.getKleding().getConfig().getConfigurationSection("kleding") == null){
            inventory.addItem(new Item(Material.PAPER, 1, (short)0, "&oVoeg kleding toe via de config!", Arrays.asList("In kleding.yml"), false, false).create());
        }else{
            for (String s : PrisonEscape.getKleding().getConfig().getConfigurationSection("kleding").getKeys(false)){
                inventory.addItem(PrisonEscape.getKleding().getConfig().getItemStack("kleding." + s + ".itemStack"));
                counter += 1;
            }
        }

        int num = inventory.getContents().length - counter;

        for (int i = counter; i < inventory.getContents().length; i++){
            inventory.setItem(i, new Item(Material.STAINED_GLASS_PANE,1 , (short)8, " ", Arrays.asList(" "), false, true).create());
        }

        p.openInventory(inventory);
    }

    public void close(){
        p.closeInventory();
    }

}
