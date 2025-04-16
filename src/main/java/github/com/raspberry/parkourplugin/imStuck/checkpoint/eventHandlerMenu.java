package github.com.raspberry.parkourplugin.imStuck.checkpoint;

import github.com.raspberry.parkourplugin.imStuck.pracSpec.pracManager;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class eventHandlerMenu implements Listener {
    pracManager pracSystem;
    checkpointManager checkpointmanager;

    public eventHandlerMenu(pracManager prac,checkpointManager cpmg) {
        pracSystem = prac;
        checkpointmanager = cpmg;
    }

    @EventHandler
    public void cancelJumpOnCrops(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getLocation().getBlock().getType() == Material.SOIL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPressurePlate(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL && event.getClickedBlock().getLocation().getBlock().getType() == Material.GOLD_PLATE) {
            double xLoc = event.getClickedBlock().getLocation().getBlockX();
            double yLoc = event.getClickedBlock().getLocation().getBlockY();
            double zLoc = event.getClickedBlock().getLocation().getBlockZ();
            float yaw = event.getPlayer().getLocation().getYaw();
            float pitch = event.getPlayer().getLocation().getPitch();
            World world = Bukkit.getWorld(event.getPlayer().getWorld().getUID());
            checkpointmanager.setCheckpointLoc(new Location(world,xLoc+0.5D,yLoc,zLoc+0.5D,yaw,pitch),event.getPlayer());
        }
    }



    @EventHandler
    public void onCheckpointItem(PlayerInteractEvent event) {
        //TODO:fix the null pointer error when right clicked
        if (checkpointmanager.playerCheckpointLocation.get(event.getPlayer().getUniqueId()).getWorld()!=null
                && event.getPlayer().getWorld().equals(checkpointmanager.playerCheckpointLocation.get(event.getPlayer().getUniqueId()).getWorld())
                && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && event.getItem()!=null
                && event.getItem().getType() == Material.CARROT_STICK) {
            if (pracSystem.playerCheckpointLocation.get(event.getPlayer().getUniqueId()) == null) {
                checkpointmanager.teleportToCheckpoint(event.getPlayer());
            } else {
                pracSystem.teleportToPracLoc(event.getPlayer());
                event.getPlayer().sendMessage(ChatColor.YELLOW + "Teleporting to Checkpoint..");
            }
        }
    }

}
