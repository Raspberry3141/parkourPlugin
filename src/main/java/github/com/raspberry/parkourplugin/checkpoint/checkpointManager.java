package github.com.raspberry.parkourplugin.checkpoint;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

//TODO make this class not a singleton, and instead instantiate it in onEnable() and pass this into arguments to where needed.
public class checkpointManager {
    private static checkpointManager singleton;

    private checkpointManager() {}

    public static checkpointManager getInstance() {
        if (singleton == null) {
            singleton = new checkpointManager();
        }
        return singleton;
    }

    HashMap<UUID, Location> playerCheckpointLocation = new HashMap<UUID, Location>();

    public void setCheckpointLoc(Location loc, Player player) {
        Location oldPlayerCheckpointLoc = playerCheckpointLocation.get(player.getUniqueId());
        if (oldPlayerCheckpointLoc==null || !oldPlayerCheckpointLoc.equals(loc)) {
            playerCheckpointLocation.put(player.getUniqueId(),loc);
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
}
