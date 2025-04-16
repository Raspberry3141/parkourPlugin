package github.com.raspberry.parkourplugin.imStuck.checkpoint;

import github.com.raspberry.parkourplugin.imStuck.Helper.configFileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Arrays;
import java.util.HashMap;

public class parkourLoaderEvent implements Listener {
    configFileManager configFileMan;
    checkpointManager cpmgr;
    public parkourLoaderEvent(configFileManager dotfile,checkpointManager cpmg) {
        configFileMan =dotfile;
        cpmgr = cpmg;
    }

    @EventHandler
    private void onPlayerJoin(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = configFileMan.getParkourConfigFile();
        String uuidKey = player.getWorld().getUID().toString();
        String playerUUID = player.getUniqueId().toString();
        if (config.getString(uuidKey+".players."+playerUUID+".lastCpLoc")!=null && config.getString(uuidKey+".players."+playerUUID+".lastCpLoc")!=null) {
            String[] getLastLoc =parseStringLoc(config.getString(uuidKey+".players."+playerUUID+".lastLoc"));
            String[] getLastCp=parseStringLoc(config.getString(uuidKey+".players."+playerUUID+".lastCpLoc"));
            System.out.println((player.teleport(new Location(event.getPlayer().getWorld(),Double.parseDouble(getLastLoc[0]),Double.parseDouble(getLastLoc[1]),Double.parseDouble(getLastLoc[2])))));
            cpmgr.setCheckpointLoc(new Location(player.getWorld(),Double.parseDouble(getLastCp[0]),Double.parseDouble(getLastCp[1]),Double.parseDouble(getLastCp[2])),player);
        }
    }

    private String[] parseStringLoc(String string) {
        return string.split("/");
    }
}