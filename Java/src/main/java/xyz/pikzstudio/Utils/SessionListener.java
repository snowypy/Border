package xyz.pikzstudio.Utils;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.pikzstudio.Border;

public class SessionListener implements Listener {

    private final Border plugin;

    public SessionListener(Border plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        String joinMessage = plugin.getPluginConfig().getString("server.game.messages.joinMessage", "Debug: §7[§a+&7] §a{name} §7has joined");

        joinMessage = joinMessage.replace("<name>", playerName);
        joinMessage = joinMessage.replace("&", "§");

        event.setJoinMessage(joinMessage);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        String quitMessage = plugin.getPluginConfig().getString("server.game.messages.quitMessage", "Debug: §7[§c-§7] §c{name} §7has disconnected");

        quitMessage = quitMessage.replace("<name>", playerName);
        quitMessage = quitMessage.replace("&", "§");

        event.setQuitMessage(quitMessage);
    }
}
