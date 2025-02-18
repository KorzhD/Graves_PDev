package org.example.dmytrok.dkgraveplugin.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GraveSpawnEvent implements Listener {

    public String key;
    public static Location graveLocation;
    public static Location secondGraveLocation;


    @EventHandler
    public void onPlayerDeath(EntityDeathEvent event) {
        if(event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();

            List<ItemStack> itemStacks = new ArrayList<>(event.getDrops());
            if(itemStacks.size() == 0) {
                return;
            }

            player.sendMessage("§8Your grave is spawned at: "
                    + (int) player.getLocation().getX() +
                    " " + (int) player.getLocation().getY() +
                    " " + (int) player.getLocation().getZ());


            event.getDrops().clear();
            List<ItemStack> droppedChestKeys = new ArrayList<>();
            for (ItemStack item : itemStacks) {
                if(item.getType().equals(Material.TRIPWIRE_HOOK) && item.getItemMeta().hasDisplayName()
                        && item.getItemMeta().getDisplayName().startsWith("Grave Key ")) {
                    droppedChestKeys.add(item);
                }
            }
            for(ItemStack item : droppedChestKeys) {
                event.getDrops().add(item);
            }


            Location chestLoc = new Location(player.getWorld(), player.getLocation().getX(),
                    player.getLocation().getY(), player.getLocation().getZ());
            Location secondChestLoc = new Location(player.getWorld(), player.getLocation().getX() + 1,
                    player.getLocation().getY(), player.getLocation().getZ());
            graveLocation = chestLoc;
            secondGraveLocation = secondChestLoc;

            chestLoc.getBlock().setType(Material.CHEST);
            secondChestLoc.getBlock().setType(Material.CHEST);


            Chest chest = (Chest) chestLoc.getBlock().getState();
            Chest secondChest = (Chest) secondChestLoc.getBlock().getState();

            String firstChestName = "Grave of " + player.getName() + " | " + getChestSecretKey();
            String secondChestName = "Grave of " + player.getName() + " | " + key;

            chest.setCustomName(firstChestName);
            secondChest.setCustomName(secondChestName);


            chest.update();
            secondChest.update();

            Inventory chestInv = chest.getInventory();
            Inventory secondChestInv = secondChest.getInventory();


            for(int i = 0; i <= itemStacks.size(); i++) {
                chestInv.addItem(itemStacks.get(i));
                if (chestInv.getSize() < itemStacks.size()) {
                    secondChestInv.addItem(itemStacks.get(i));
                }
            }
        }
    }

    public String getChestSecretKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(11);

        for (int i = 0; i < 11; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        String chestSecretKey = sb.toString();
        key = chestSecretKey;
        GraveKeyAfterRebornEvent.secretKey = chestSecretKey;
        return chestSecretKey;
    }
}