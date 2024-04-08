package xyz.pikzstudio.Worlds;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class WorldTeleport {

    static void TeleportWorld(Player player) {

        String worldName = player + "_world";
        World world = player.getServer().getWorld(worldName);
        if (world != null) {
            player.teleport(world.getSpawnLocation());
        } else {
            player.sendMessage("§4Error: &fYou do not have a world. Create one using §a/world");
        }

    }

}
