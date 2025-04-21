package github.com.raspberry.parkourplugin.imStuck.checkpoint;

import github.com.raspberry.parkourplugin.imStuck.Helper.configFileManager;
import github.com.raspberry.parkourplugin.imStuck.playerItemsManager.PlayerCapabilityController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Arrays;

public class parkourLoaderEvent implements Listener {
    configFileManager configFileMan;
    checkpointManager cpmgr;
    PlayerCapabilityController capabilityController;
    public parkourLoaderEvent(configFileManager dotfile,checkpointManager cpmg, PlayerCapabilityController capabctrl) {
        configFileMan =dotfile;
        cpmgr = cpmg;
        capabilityController = capabctrl;
    }

    @EventHandler
    private void onPlayerJoin(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = configFileMan.getParkourConfigFile();
        String uuidKey = player.getWorld().getUID().toString();
        String playerUUID = player.getUniqueId().toString();
        if (config.getString(uuidKey+".players."+playerUUID+".lastLoc")!=null) {
            String[] getLastLoc =parseStringLoc(config.getString(uuidKey+".players."+playerUUID+".lastLoc"));
            System.out.println(Arrays.toString(getLastLoc));
            Location readPlayerLoc = new Location(event.getPlayer().getWorld(),Double.parseDouble(getLastLoc[0]),Double.parseDouble(getLastLoc[1]),Double.parseDouble(getLastLoc[2]));
            readPlayerLoc.setYaw(Float.parseFloat(getLastLoc[3]));
            readPlayerLoc.setPitch(Float.parseFloat(getLastLoc[4]));
            player.teleport(readPlayerLoc);
        }
        if (config.getString(uuidKey+".players."+playerUUID+".lastCpLoc")!=null) {
            String[] getLastCp=parseStringLoc(config.getString(uuidKey+".players."+playerUUID+".lastCpLoc"));
            cpmgr.setCheckpointLoc(new Location(player.getWorld(),Double.parseDouble(getLastCp[0]),Double.parseDouble(getLastCp[1]),Double.parseDouble(getLastCp[2])),player);
        }
    }

    @EventHandler
    private void onPlayerTeleport(PlayerTeleportEvent event) {
        FileConfiguration config = configFileMan.getParkourConfigFile();
        if ((capabilityController.getCapabilities(event.getPlayer())== PlayerCapabilityController.playerState.ISBUILDING || capabilityController.getCapabilities(event.getPlayer())== PlayerCapabilityController.playerState.INPARKOUR) && !event.getFrom().equals(event.getPlayer().getWorld())) {

            String worldUuid = event.getFrom().getWorld().getUID().toString();
            String playerUuid = event.getPlayer().getUniqueId().toString();
            Location playerLastLoc = event.getFrom();
            String newLastLocInfo =
                    playerLastLoc.getX()+"/"+
                    playerLastLoc.getY()+"/"+
                    playerLastLoc.getZ()+"/"+
                    playerLastLoc.getYaw()+"/"+
                    playerLastLoc.getPitch();
            if (config.getString(worldUuid+".players."+playerUuid)!=null) {
                config.set(worldUuid+".players."+playerUuid+".lastLoc",newLastLocInfo);
                configFileMan.saveParkourConfigFile(config);
            } else {
                Bukkit.getLogger().info("parkourLoaderEvent:player has no lastLoc value for the world!");
            }
        }
    }



    private String[] parseStringLoc(String string) {
        return string.split("/");
    }
}