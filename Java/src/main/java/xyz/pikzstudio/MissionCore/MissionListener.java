package xyz.pikzstudio.MissionCore;

import net.kyori.adventure.platform.facet.Facet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MissionListener implements Listener {

    private final MissionsData missionsData;

    public MissionListener(MissionsData missionsData) {
        this.missionsData = missionsData;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            missionsData.handleEntityDeath(player, event.getEntityType());
        }
    }



    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        missionsData.handleBlockBreak(event.getPlayer(), event.getBlock().getType());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && ChatColor.stripColor(event.getView().getTitle()).equals("Missions")) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("Missions Manager")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.BARRIER) {
                player.performCommand("missions start");
                player.performCommand("missions gui");
            }
        }
    }

}
