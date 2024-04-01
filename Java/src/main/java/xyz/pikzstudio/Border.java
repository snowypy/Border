package xyz.pikzstudio;

import xyz.pikzstudio.MissionCore.MissionsCommand;
import xyz.pikzstudio.MissionCore.MissionListener;
import xyz.pikzstudio.MissionCore.MissionsData;
import xyz.pikzstudio.Utils.SessionListener;
import xyz.pikzstudio.Utils.Trash;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Border extends JavaPlugin {

    private static Plugin plugin;

    public void onEnable() {


        /* Feature List

          Tags
          Chat Filter
          Database Support
          Config ymls [To be loaded into the Config/* directory]

            Config/Branding/brand.yml -> Sk
            Config/World/config.yml -> Java
            Config/Missions/config.yml -> Java
            Config/Basic/config.yml -> Java

          World Manage [Manage Visting rights, Manage Visiting location, TP On join, Border Color]
          World Creation
          World Visting


         */

        plugin = this;

        log(ChatColor.GREEN + "You loaded Border v1.2. This is a custom plugin made by " + ChatColor.LIGHT_PURPLE + "SnowyJS" + ChatColor.GREEN + " for You!");

        // Mission Data Register

        MissionsData missionsData = new MissionsData();

        MissionListener missionListener = new MissionListener(missionsData);
        MissionsCommand missionCommand = new MissionsCommand(missionsData);

        // Listeners

        registerEvents(this, new Trash());
        registerEvents(this, missionListener);
        registerEvents(this, new SessionListener());

        // Command Register

        getCommand("missions").setExecutor(missionCommand);

        getCommand("world").setExecutor(new Worlds());

        getCommand("trash").setExecutor(new Trash());


    }

    public void onDisable() {

        plugin = null;

    }

    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {

        for (Listener listener : listeners) {

            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);

        }
    }


    public static Plugin getPlugin() {

        return plugin;

    }

    public static void log(String message) {

        System.out.println(message);

    }

}
