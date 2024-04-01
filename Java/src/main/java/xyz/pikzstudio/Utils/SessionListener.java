package xyz.pikzstudio.Utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
public class SessionListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        String playername = event.getPlayer().getName();

        // Join Message
        event.setJoinMessage("§7[§a+§7] §a" + playername + " §7has joined");

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        String playername = event.getPlayer().getName();

        // Quit Message
        event.setQuitMessage("§7[§c-§7] §c" + playername + " §7has disconnected");

    }

}
