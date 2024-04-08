package xyz.pikzstudio.Worlds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class WorldCreation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        createSmallWorld(player);
        return true;
    }

    static void createSmallWorld(Player player) {
        String worldName = player.getName() + "_world";

        // Specify the path to the world's folder
        File worldFolder = new File("Worlds/", worldName);

        // Create the WorldCreator instance
        WorldCreator worldCreator = new WorldCreator(worldName);

        // Set the world folder location
        worldCreator.generateStructures(false);

        World smallWorld = Bukkit.createWorld(worldCreator);

        double borderSize = 15;
        smallWorld.getWorldBorder().setSize(borderSize);

        player.sendMessage("");
        player.sendMessage("§a§lWORLD CREATED");
        player.sendMessage("");
        player.sendMessage("§8• §fManage World §a[/world manage]");
        player.sendMessage("§8• §fVisit Worlds §a[/world visit]");
        player.sendMessage("§8• §fTeleport to World §a[/world teleport]");
        player.sendMessage("");
        player.sendMessage("§fYou have been teleported to your world automatically. Return to spawn using §a/spawn");
        player.sendMessage("");
        player.teleport(smallWorld.getSpawnLocation());
    }
}
