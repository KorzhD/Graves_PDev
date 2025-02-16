package org.example.dmytrok.dkgraveplugin.events;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GraveKeyAfterRebornEvent implements Listener {
    public static String secretKey;

    @EventHandler
    public void onPlayerReborn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        ItemStack graveKey = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta graveKeyMeta = graveKey.getItemMeta();

        player.sendTitle("§4You Died", "", 30, 30, 30);

        if (GraveSpawnEvent.graveLocation != null) {
            World world = player.getWorld();
            Block graveBlock = world.getBlockAt(GraveSpawnEvent.graveLocation);
            Block secondGraveBlock = world.getBlockAt(GraveSpawnEvent.secondGraveLocation);

            if (graveBlock.getType() != Material.CHEST && secondGraveBlock.getType() != Material.CHEST) {
                return;
            }

        }

        if (graveKeyMeta != null && secretKey != null) {
            graveKeyMeta.setDisplayName("§6Grave Key " + secretKey);
            graveKey.setItemMeta(graveKeyMeta);
            player.getInventory().addItem(graveKey);
        }



    }
}
