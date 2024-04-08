package xyz.pikzstudio.MissionCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;
import java.util.Arrays;

public class MissionsCommand implements CommandExecutor {

    private final MissionsData missionsData;

    public MissionsCommand(MissionsData missionsData) {
        this.missionsData = missionsData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            displayActiveMissions(player);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            startRandomMission(player);
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
            openMissionGUI(player);
            return true;
        }

        return false;
    }

    private void openMissionGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 45, "Missions Manager");

        String activeMission = missionsData.getPlayerMission(player);
        if (activeMission != null) {
            MissionsData.MissionData missionData = missionsData.getMissionData(player);
            if (missionData != null) {
                ItemStack missionPaper = new ItemStack(Material.PAPER);
                ItemMeta meta = missionPaper.getItemMeta();
                meta.setDisplayName("§a§lMISSION TRACKER");
                meta.setLore(Arrays.asList(
                        "",
                        "§fTracking Mission: §a" + ChatColor.GREEN + missionData.getMissionName(),
                        "§fMission Progress: §a" + ChatColor.GREEN + missionData.getProgress() + " / " + missionData.getTarget(),
                        "§fTime remaining: §a§n" + ChatColor.GREEN + formatTime(missionData.getTimeLeft()),
                        ""
                ));
                missionPaper.setItemMeta(meta);

                gui.setItem(22, missionPaper);
            } else {
                player.sendMessage(ChatColor.RED + "Error: Could not retrieve mission data.");
            }
        } else {
            ItemStack barrier = new ItemStack(Material.BARRIER);
            ItemMeta meta = barrier.getItemMeta();
            meta.setDisplayName("§c§lMISSION TRACKER");
            meta.setLore(Arrays.asList(
                    "",
                    "§cYou are not tracking any missions. To do so,",
                    "§cclick §f§nHere§f!",
                    ""
            ));
            barrier.setItemMeta(meta);
            gui.setItem(22, barrier);
        }

        player.openInventory(gui);
    }

    private void startRandomMission(Player player) {
        missionsData.startRandomMission(player);
        MissionsData.MissionData missionData = missionsData.getMissionData(player);
        player.sendMessage(ChatColor.GREEN + "Started a random mission!");
        player.sendMessage("");
        player.sendMessage("§a§lMISSIONS");
        player.sendMessage(" §fYou have have started the following mission:");
        player.sendMessage(" §a§n" + missionData.getMissionName() + "§r §fYou have §a§n1 Hour§r §fto complete it.");
        player.sendMessage("");
    }

    private void displayActiveMissions(Player player) {
        String activeMission = missionsData.getPlayerMission(player);
        if (activeMission != null) {
            MissionsData.MissionData missionData = missionsData.getMissionData(player);
            if (missionData != null) {
                ChatColor green = ChatColor.GREEN;
                player.sendMessage("Active Mission: " + green + missionData.getMissionName());
                player.sendMessage("Progress: " + green + missionData.getProgress() + " / " + missionData.getTarget());
                player.sendMessage("Time Taken: " + green + formatTime(missionData.getTimeTaken()));
                player.sendMessage("Time Left: " + green + formatTime(missionData.getTimeLeft()));
            } else {
                player.sendMessage(ChatColor.RED + "Error: Could not retrieve mission data.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have any active missions.");
        }
    }

    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        minutes %= 60;
        seconds %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
