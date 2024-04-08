package xyz.pikzstudio.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class Trash implements Listener, CommandExecutor {

    private static Map<Player, Inventory> trashInventories;

    public static void openTrashGUI(Player player) {
        Inventory trashInventory = Bukkit.createInventory(player, 54, "Trash");

        trashInventories.put(player, trashInventory);
        player.openInventory(trashInventory);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("trash")) {
            trashInventories = new HashMap<>();
            if (args.length == 0) {
                if (sender instanceof Player) {
                    openTrashGUI((Player) sender);
                    return true;
                } else {
                    sender.sendMessage("§4Only players can use this addon");
                    return true;
                }
            }
        }
        return false;
    }


}
