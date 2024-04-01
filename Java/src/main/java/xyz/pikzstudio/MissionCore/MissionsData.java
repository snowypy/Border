package xyz.pikzstudio.MissionCore;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MissionsData {

    private Map<Player, MissionData> activeMissions = new HashMap<>();
    private String[] availableMissions = {"Kill 1 Player", "Kill 5 Monsters", "Kill 15 Animals", "Break 5 Logs", "Mine 10 Stone"};

    public void handleEntityDeath(Player player, EntityType entityType) {
        if (activeMissions.containsKey(player)) {
            MissionData missionData = activeMissions.get(player);
            missionData.handleEntityDeath(entityType);
        }
    }

    public void handleBlockBreak(Player player, Material blockType) {
        if (activeMissions.containsKey(player)) {
            MissionData missionData = activeMissions.get(player);
            missionData.handleBlockBreak(blockType);
        }
    }

    public void startRandomMission(Player player) {
        Random random = new Random();
        int index = random.nextInt(availableMissions.length);
        String missionName = availableMissions[index];
        int target = 0;
        switch (missionName) {
            case "Kill 1 Player":
                target = 1;
                break;
            case "Kill 5 Monsters":
                target = 5;
                break;
            case "Kill 15 Animals":
                target = 15;
                break;
            case "Break 5 Logs":
                target = 5;
                break;
            case "Mine 10 Stone":
                target = 10;
                break;
        }
        MissionData missionData = new MissionData(missionName, target);
        activeMissions.put(player, missionData);
    }

    public MissionData getMissionData(Player player) {
        return activeMissions.get(player);
    }

    public String getPlayerMission(Player player) {
        if (activeMissions.containsKey(player)) {
            return activeMissions.get(player).getMissionName();
        }
        return null;
    }

    public static class MissionData {
        private final String missionName;
        private final int target;
        private int progress;
        private long startTime;

        public MissionData(String missionName, int target) {
            this.missionName = missionName;
            this.target = target;
            this.progress = 0;
            this.startTime = System.currentTimeMillis();
        }

        public String getMissionName() {
            return missionName;
        }

        public int getProgress() {
            return progress;
        }

        public int getTarget() {
            return target;
        }

        public long getTimeTaken() {
            return System.currentTimeMillis() - startTime;
        }

        public long getTimeLeft() {
            long duration = 3600000; // 1 hour in milliseconds, modify as needed
            return Math.max(duration - getTimeTaken(), 0);
        }

        public void incrementProgress() {
            progress++;
        }

        public void handleEntityDeath(EntityType entityType) {
            switch (missionName) {
                case "Kill 5 Monsters":
                    if (entityType == EntityType.ZOMBIE || entityType == EntityType.SKELETON || entityType == EntityType.CREEPER || entityType == EntityType.SPIDER || entityType == EntityType.ENDERMAN) {
                        incrementProgress();
                    }
                    break;
                case "Kill 15 Animals":
                    if (entityType == EntityType.COW || entityType == EntityType.PIG || entityType == EntityType.SHEEP || entityType == EntityType.CHICKEN || entityType == EntityType.RABBIT) {
                        incrementProgress();
                    }
                    break;
                default:
                    break;
            }
        }

        public void handleBlockBreak(Material blockType) {
            switch (missionName) {
                case "Break 5 Logs":
                    if (blockType == Material.OAK_LOG || blockType == Material.BIRCH_LOG || blockType == Material.SPRUCE_LOG || blockType == Material.JUNGLE_LOG || blockType == Material.ACACIA_LOG || blockType == Material.DARK_OAK_LOG) {
                        incrementProgress();
                    }
                    break;
                case "Mine 10 Stone":
                    if (blockType == Material.STONE || blockType == Material.GRANITE || blockType == Material.DIORITE || blockType == Material.ANDESITE) {
                        incrementProgress();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
