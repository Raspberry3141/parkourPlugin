package github.com.raspberry.parkourplugin.imStuck.checkpoint;

import github.com.raspberry.parkourplugin.imStuck.Helper.configFileManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class checkpointManager {
    configFileManager conffilemgr;

    public checkpointManager(configFileManager conf) {
        conffilemgr = conf;
    }

    HashMap<UUID, Location> playerCheckpointLocation = new HashMap<UUID, Location>();

    public void setCheckpointLoc(Location loc, Player player) {
        Location oldPlayerCheckpointLoc = playerCheckpointLocation.get(player.getUniqueId());
        if (oldPlayerCheckpointLoc==null || !oldPlayerCheckpointLoc.equals(loc)) {
            playerCheckpointLocation.put(player.getUniqueId(),loc);
            updateCheckpoint(player);
            updateLastPos(player);
            player.sendMessage(ChatColor.YELLOW + "Checkpoint reached");
        }
    }

    public void teleportToCheckpoint(Player player) {
        Location checkpointLocation = playerCheckpointLocation.get(player.getUniqueId());
        if (!(checkpointLocation == null)) {
            player.teleport(checkpointLocation);
            player.sendMessage(ChatColor.YELLOW + "Teleporting to Checkpoint..");
        }
    }

    private void updateCheckpoint(Player player) {
        FileConfiguration config = conffilemgr.getParkourConfigFile();
        String uuidKey = player.getWorld().getUID().toString();

        HashMap<String,String> playerInfo = new HashMap<>();
        playerInfo.put("displayName",player.getDisplayName());
        playerInfo.put("lastCpLoc",player.getLocation().getBlockX() + "/" +
                player.getLocation().getBlockY() + "/" +
                player.getLocation().getBlockZ());

        config.createSection(uuidKey+".players."+player.getUniqueId());
        config.set(uuidKey+".players."+player.getUniqueId(),playerInfo);
        conffilemgr.saveParkourConfigFile(config);
    }

    public void updateLastPos(Player player) {
        String uuidKey = player.getWorld().getUID().toString();
        FileConfiguration config = conffilemgr.getParkourConfigFile();
        HashMap<String,String> playerInfo = new HashMap<>();
        playerInfo.put("lastLoc", player.getLocation().getX()+"/"+
                player.getLocation().getY()+"/"+
                player.getLocation().getZ()+"/"+
                player.getLocation().getYaw()+"/"+
                player.getLocation().getPitch());
        config.createSection(uuidKey+".players."+player.getUniqueId());
        config.set(uuidKey+".players."+player.getUniqueId(),playerInfo);
        conffilemgr.saveParkourConfigFile(config);
    }
}
