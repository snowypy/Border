package xyz.pikzstudio.Worlds;

import org.bukkit.entity.Player;

public class WorldManage {

    public static void setVisitLocation(Player player, String location) {
        String playerName = player.getName();
        SetVisitLocation visitLocationManager = new SetVisitLocation(playerName);
        visitLocationManager.setVisitLocation(playerName + "_world", location);
    }

    public static void enableVisiting(Player player) {
        String playerName = player.getName();
        SetVisitLocation visitLocationManager = new SetVisitLocation(playerName);
        // Set visit location to null to disable visiting
        visitLocationManager.setVisitLocation(playerName + "_world", null);
        player.sendMessage("§aVisiting to your world has been enabled.");
    }

    public static void disableVisiting(Player player) {
        String playerName = player.getName();
        SetVisitLocation visitLocationManager = new SetVisitLocation(playerName);
        // Set visit location to null to disable visiting
        visitLocationManager.setVisitLocation(playerName + "_world", null);
        player.sendMessage("§cVisiting to your world has been disabled.");
    }

    // You can add more methods here to manage other world settings

}