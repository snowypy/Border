package xyz.pikzstudio;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Worlds implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            ChatColor error = ChatColor.DARK_RED;
            sender.sendMessage(error + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        createSmallWorld(player);
        return true;
    }

    private void createSmallWorld(Player player) {
        String worldName = player.getName() + "_world";
        WorldCreator worldCreator = new WorldCreator(worldName);
        World smallWorld = Bukkit.createWorld(worldCreator);

         double borderSize = 15;
         smallWorld.getWorldBorder().setSize(borderSize);

        ChatColor gray = ChatColor.DARK_GRAY;
        ChatColor green = ChatColor.GREEN;
        ChatColor white = ChatColor.WHITE;

        player.sendMessage(white + "");
        player.sendMessage(green + "" + ChatColor.BOLD + "WORLD CREATED");
        player.sendMessage(white + "");
        player.sendMessage(gray + "• " + white + "Manage World" + green + " [/world manage]");
        player.sendMessage(gray + "• " + white + "Visit Worlds" + green + " [/world visit]");
        player.sendMessage(gray + "• " + white + "Teleport to World" + green + " [/world teleport]");
        player.sendMessage(white + "");
        player.sendMessage("You have been teleported to your world automatically. Return to spawn using" + green + " /spawn");
        player.sendMessage(white + "");
        player.teleport(smallWorld.getSpawnLocation());


    }
}
