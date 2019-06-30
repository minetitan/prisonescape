package maiky1304.dev.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * This code as been written by Maiky1304 you are not allowed to use this code in your plugins
 * Package name maiky1304.dev.util
 */

public class Item {

    private Material material;
    private int amount;
    private short damage;
    private String displayName;
    private List<String> lore;
    private boolean hideEnchants,fakeGlow;

    public Item(Material material, int amount, short damage, String displayName, List<String> lore, boolean hideEnchants, boolean fakeGlow){
        this.material = material;
        this.amount = amount;
        this.damage = damage;
        this.displayName = displayName;
        this.lore = lore;
        this.hideEnchants = hideEnchants;
        this.fakeGlow = fakeGlow;
    }

    public ItemStack create(){
        ItemStack item = new ItemStack(this.material, this.amount, this.damage);
        ItemMeta m = item.getItemMeta();
        m.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));

        List<String> newLore = new ArrayList<>();
        for (String s : this.lore){
            newLore.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        if (hideEnchants){
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        if (fakeGlow){
            m.addEnchant(Enchantment.DURABILITY, 1, true);
            m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        m.setLore(newLore);
        item.setItemMeta(m);
        return item;
    }

}
