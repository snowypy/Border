package xyz.pikzstudio.Worlds;

import java.util.HashMap;
import java.util.Map;

public class SetVisitLocation {
    private String playerName;
    // Map to store visit locations for each world
    private static Map<String, String> visitLocations = new HashMap<>();

    public SetVisitLocation(String playerName) {
        this.playerName = playerName;
        // Example: Storing visit locations for demonstration
        visitLocations.put("example_player_world", "(100, 50, 200)");
    }

    public void visitLocation() {
        String worldName = playerName + "_world";
        if (worldExists(worldName)) {
            String visitLocation = getVisitLocation(worldName);
            if (visitLocation != null && !visitLocation.isEmpty()) {
                // Teleport the player to the visit location
                teleportPlayer(visitLocation);
            } else {
                System.out.println("Visit location for world " + worldName + " is not configured.");
            }
        } else {
            System.out.println("World " + worldName + " does not exist for player " + playerName + ".");
        }
    }

    public void setVisitLocation(String worldName, String location) {
        visitLocations.put(worldName, location);
        System.out.println("Visit location set for world " + worldName + ": " + location);
    }

    private boolean worldExists(String worldName) {
        // Check if the world exists
        return visitLocations.containsKey(worldName);
    }

    private String getVisitLocation(String worldName) {
        // Retrieve the visit location for the world
        return visitLocations.get(worldName);
    }

    private void teleportPlayer(String location) {
        // Teleport the player to the specified location (Replace with actual implementation)
        System.out.println("Teleporting player to location: " + location); // Placeholder for demonstration
    }

    // Example usage
    public static void main(String[] args) {
        String playerName = "example_player";
        SetVisitLocation visitLocationManager = new SetVisitLocation(playerName);

        // Set visit location for a world
        visitLocationManager.setVisitLocation("example_player_world", "(200, 70, 300)");

        // Visit the location
        visitLocationManager.visitLocation();
    }

}
