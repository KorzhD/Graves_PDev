package org.example.dmytrok.dkgraveplugin.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
public class SuperKeyCreator {

    public static ItemStack createItem() {
        ItemStack superKey = new ItemStack(Material.TOTEM, 1);
        ItemMeta itemMeta = superKey.getItemMeta();

        List<String> list = new ArrayList<>();
        list.add("§5Can open any grave");

        if (itemMeta != null) {
            itemMeta.setDisplayName("Super Key");
            itemMeta.setLore(list);
            itemMeta.setUnbreakable(true);
            superKey.setItemMeta(itemMeta);
        }
        return superKey;

    }
}
