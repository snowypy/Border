package xyz.pikzstudio.MissionCore;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

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
}
