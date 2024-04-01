package xyz.pikzstudio.MissionCore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        // Implement GUI to show missions and allow selection
    }

    private void startRandomMission(Player player) {
        missionsData.startRandomMission(player);
        player.sendMessage(ChatColor.GREEN + "Started a random mission!");
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
