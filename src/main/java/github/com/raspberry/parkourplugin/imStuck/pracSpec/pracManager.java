package github.com.raspberry.parkourplugin.imStuck.pracSpec;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class pracManager {
    public HashMap<UUID, Location> playerCheckpointLocation;

    public pracManager() {
        playerCheckpointLocation = new HashMap<UUID, Location>();
    }

    public void enterPrac(Player player) {
        playerCheckpointLocation.put(player.getUniqueId(),player.getLocation());
        player.sendMessage(ChatColor.YELLOW + "Entering Practice Mode");
        //TODO take away permissions like pressure plates
    }

    public void leavePrac(Player player) {
        player.teleport(playerCheckpointLocation.get(player.getUniqueId()));
        playerCheckpointLocation.remove(player.getUniqueId());
        player.sendMessage(ChatColor.YELLOW + "Leaving Practice Mode");
        //TODO give back permissions like pressure plates
    }

    public void teleportToPracLoc(Player player) {
        player.teleport(playerCheckpointLocation.get(player.getUniqueId()));
    }

}
