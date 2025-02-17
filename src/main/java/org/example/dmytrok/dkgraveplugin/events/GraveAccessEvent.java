package org.example.dmytrok.dkgraveplugin.events;

import org.bukkit.block.Block;
import org.bukkit.block.DoubleChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class GraveAccessEvent implements Listener {

    @EventHandler
    public void onChestOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getPlayer();

        if (!(event.getInventory().getHolder() instanceof Chest)) {
            return;
        }
        Chest chest = (Chest) event.getInventory().getHolder();

        if (!canAccessGrave(chest, player)) {
            event.setCancelled(true);
            player.sendMessage("§cYou don't have the key to open this grave!");
        }
    }

    @EventHandler
    public void onChestInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getType() != Material.CHEST) {
            return;
        }

        Block block = event.getClickedBlock();
        if (!(block.getState() instanceof Chest)) {
            return;
        }

        Chest chest = (Chest) block.getState();
        Player player = event.getPlayer();

        if (!canAccessGrave(chest, player)) {
            event.setCancelled(true);
            player.sendMessage("§cYou don't have the key to open this grave!");
        }
    }


    @EventHandler
    public void onChestBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() != Material.CHEST) {
            return;
        }

        Chest chest = (Chest) block.getState();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();


        if (isGraveBreaker(itemInHand)) {
            event.setCancelled(true);
            chest.getInventory().clear();
            block.setType(Material.AIR);
            player.sendMessage("§cYou have completely destroyed this grave!");
            return;
        }


        if (!canAccessGrave(chest, player)) {
            event.setCancelled(true);
            player.sendMessage("§cYou can't break this grave without the key or a Grave Breaker!");
        }
    }

    @EventHandler
    public void onChestClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (!(inventory.getHolder() instanceof Chest || inventory.getHolder() instanceof DoubleChest)) {
            return;
        }

        Chest chest = null;
        Chest secondChest = null;

        if (inventory.getHolder() instanceof DoubleChest) {
            DoubleChest doubleChest = (DoubleChest) inventory.getHolder();
            chest = (Chest) doubleChest.getLeftSide();
            secondChest = (Chest) doubleChest.getRightSide();
        } else {
            chest = (Chest) inventory.getHolder();
        }

        if (!isGraveChest(chest)) {
            return;
        }

        if (isChestEmpty(chest) && (secondChest == null || isChestEmpty(secondChest))) {
            chest.getBlock().setType(Material.AIR);
            if (secondChest != null) {
                secondChest.getBlock().setType(Material.AIR);
            }
        }
    }

    private boolean isGraveChest(Chest chest) {
        return chest != null && chest.getCustomName() != null && chest.getCustomName().startsWith("Grave of ");
    }

    private boolean isChestEmpty(Chest chest) {
        if (chest == null) return true;
        for (ItemStack item : chest.getInventory().getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }
        return true;
    }


    private boolean canAccessGrave(Chest chest, Player player) {
        if (chest == null) return false;

        String chestName = chest.getCustomName();
        if (chestName == null || !chestName.startsWith("Grave of ")) return true;

        if (hasGraveKey(player, chestName)) return true;

        return false;
    }

    private boolean hasGraveKey(Player player, String chestName) {
        ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getType() == Material.TRIPWIRE_HOOK) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null && meta.hasDisplayName()) {
                    int secretKeyCheck = chestName.indexOf("|");
                    String secretKey = chestName.substring(secretKeyCheck + 2, chestName.length());
                    if (meta.getDisplayName().replace("Grave Key ", "")
                            .equals(secretKey)) {
                        return true;
                    }
                }
            }
        return false;
    }

    private boolean isGraveBreaker(ItemStack item) {
        if (item == null || item.getType() != Material.GOLD_AXE || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        return meta.hasDisplayName() && meta.getDisplayName().equals("Grave Breaker");
    }

}