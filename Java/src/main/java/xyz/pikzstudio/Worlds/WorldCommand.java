package xyz.pikzstudio.Worlds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            WorldCreation.createSmallWorld(player);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("teleport")) {
            WorldTeleport.TeleportWorld(player);
            return true;
        } else if (args.length == 3 && args[0].equalsIgnoreCase("manage") && args[1].equalsIgnoreCase("setvisitpoint")) {
            String location = args[2];
            WorldManage.setVisitLocation(player, location);
            return true;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("manage") && args[1].equalsIgnoreCase("togglevisiting")) {
            toggleVisiting(player);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("manage")) {
            player.sendMessage("§4Usage: /world manage <setvisitpoint|togglevisiting>");
            return true;
        }

        return true;
    }

    private static boolean visitingEnabled = false; // Variable to track visiting state

    private void toggleVisiting(Player player) {
        visitingEnabled = !visitingEnabled; // Toggle visiting state
        if (visitingEnabled) {
            WorldManage.enableVisiting(player);
        } else {
            WorldManage.disableVisiting(player);
        }
    }


}
