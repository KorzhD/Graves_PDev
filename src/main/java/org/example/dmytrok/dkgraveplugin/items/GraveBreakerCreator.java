package org.example.dmytrok.dkgraveplugin.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GraveBreakerCreator {
    public static ItemStack createItem() {
        ItemStack graveBreaker = new ItemStack(Material.GOLD_AXE, 1);
        ItemMeta itemMeta = graveBreaker.getItemMeta();

        List<String> list = new ArrayList<>();
        list.add("§4Nightmare of Graveyards....");

        if (itemMeta != null) {
            itemMeta.setDisplayName("Grave Breaker");
            itemMeta.setLore(list);
            itemMeta.setUnbreakable(true);
            graveBreaker.setItemMeta(itemMeta);
        }
        return graveBreaker;

    }
}

