package com.border.mc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class Missions extends JavaPlugin implements Listener {
    private String[] availableMissions = {"Kill 1 Player", "Kill 5 Monsters", "Kill 15 Animals", "Break 5 Logs", "Mine 10 Stone"};
    private Map<Player, MissionData> activeMissions = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        ChatColor LogStart = ChatColor.GREEN;
        getLogger().info("");
        getLogger().info(LogStart + "The Border Core has been started!");
        getLogger().info("");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getRawSlot() < event.getView().getTopInventory().getSize()) {
                if (event.getView().getTitle().equals("Mission Progress")) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player) {
            Player player = event.getEntity().getKiller();
            EntityType entityType = event.getEntityType();
            if (activeMissions.containsKey(player)) {
                MissionData missionData = activeMissions.get(player);
                switch (missionData.getMissionName()) {
                    case "Kill 5 Monsters":
                        if (entityType == EntityType.ZOMBIE || entityType == EntityType.SKELETON || entityType == EntityType.CREEPER || entityType == EntityType.SPIDER || entityType == EntityType.ENDERMAN) {
                            missionData.incrementProgress();
                        }
                        break;
                    case "Kill 15 Animals":
                        if (entityType == EntityType.COW || entityType == EntityType.PIG || entityType == EntityType.SHEEP || entityType == EntityType.CHICKEN || entityType == EntityType.RABBIT) {
                            missionData.incrementProgress();
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();
        if (activeMissions.containsKey(player)) {
            MissionData missionData = activeMissions.get(player);
            switch (missionData.getMissionName()) {
                case "Break 5 Logs":
                    if (blockType == Material.OAK_LOG || blockType == Material.BIRCH_LOG || blockType == Material.SPRUCE_LOG || blockType == Material.JUNGLE_LOG || blockType == Material.ACACIA_LOG || blockType == Material.DARK_OAK_LOG) {
                        missionData.incrementProgress();
                    }
                    break;
                case "Mine 10 Stone":
                    if (blockType == Material.STONE || blockType == Material.GRANITE || blockType == Material.DIORITE || blockType == Material.ANDESITE) {
                        missionData.incrementProgress();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDisable() {
        getLogger().severe("");
        getLogger().severe("The Border Core has been disabled, this could cause significant issues!");
        getLogger().severe("");
        getLogger().severe("Need some help? Feel free to contact snowyjs on discord for support.");
        getLogger().severe("");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("missions")) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    displayActiveMissions((Player) sender);
                    return true;
                } else {
                    sender.sendMessage("Only players can check their active missions.");
                    return true;
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
                if (sender instanceof Player) {
                    startRandomMission((Player) sender);
                    return true;
                } else {
                    sender.sendMessage("Only players can start missions.");
                    return true;
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("gui")) {
                if (sender instanceof Player) {
                    openMissionGUI((Player) sender);
                    return true;
                } else {
                    sender.sendMessage("Only players can open the mission GUI.");
                    return true;
                }
            }
        }
        return false;
    }

    private void openMissionGUI(Player player) {
        ChatColor white = ChatColor.WHITE;
        ChatColor green = ChatColor.GREEN;
        Inventory missionGUI = Bukkit.createInventory(player, 9, "Mission Progress");
        if (activeMissions.containsKey(player)) {
            MissionData missionData = activeMissions.get(player);
            ItemStack missionItem = new ItemStack(Material.PAPER);
            ItemMeta meta = missionItem.getItemMeta();
            meta.setDisplayName(white + "Current Mission: " + green + missionData.getMissionName());;
            List<String> lore = new ArrayList<>();
            lore.add(white + "Progress: " + green + missionData.getProgress() + white + " / " + green + missionData.getTarget());
            lore.add(white + "Time Taken: " + green + formatTime(missionData.getTimeTaken()));
            lore.add(white + "Time Left: " + green + formatTime(missionData.getTimeLeft()));
            meta.setLore(lore);
            missionItem.setItemMeta(meta);
            missionGUI.setItem(4, missionItem);
        } else {
            ItemStack noMissionItem = new ItemStack(Material.BARRIER);
            ItemMeta meta = noMissionItem.getItemMeta();
            ChatColor err = ChatColor.DARK_RED;
            meta.setDisplayName(err + "No Active Mission");
            noMissionItem.setItemMeta(meta);
            missionGUI.setItem(4, noMissionItem);
        }
        player.openInventory(missionGUI);
    }

    private void startRandomMission(Player player) {
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
        ChatColor green = ChatColor.GREEN;
        player.sendMessage("Started mission: " + green + missionName);
    }

    private void displayActiveMissions(Player player) {
        if (activeMissions.containsKey(player)) {
            MissionData missionData = activeMissions.get(player);
            ChatColor green = ChatColor.GREEN;
            player.sendMessage("Active Mission: " + green + missionData.getMissionName());
            player.sendMessage("Progress: " + green + missionData.getProgress() + " / " + missionData.getTarget());
            player.sendMessage("Time Taken: " + green + formatTime(missionData.getTimeTaken()));
            player.sendMessage("Time Left: " + green + formatTime(missionData.getTimeLeft()));
        } else {
            ChatColor err = ChatColor.DARK_RED;
            player.sendMessage(err + "You don't have any active missions.");
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

    private static class MissionData {
        private final String missionName;
        private final int target;
        private int progress;
        private long startTime;

        MissionData(String missionName, int target) {
            this.missionName = missionName;
            this.target = target;
            this.progress = 0;
            this.startTime = System.currentTimeMillis();
        }

        String getMissionName() {
            return missionName;
        }

        int getProgress() {
            return progress;
        }

        int getTarget() {
            return target;
        }

        long getTimeTaken() {
            return System.currentTimeMillis() - startTime;
        }

        long getTimeLeft() {
            long duration = 3600000; // 1 hour in milliseconds, modify as needed
            return Math.max(duration - getTimeTaken(), 0);
        }

        void incrementProgress() {
            progress++;
        }
    }
}
