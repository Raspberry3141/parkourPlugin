package github.com.raspberry.parkourplugin.imStuck.pracSpec;

import github.com.raspberry.parkourplugin.imStuck.GUIInventories.ItemStackHashRegister;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.checkpointManager;
import github.com.raspberry.parkourplugin.imStuck.playerItemsManager.PlayerCapabilityController;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class pracEventHandler implements Listener {
    pracManager pracSystem;
    PlayerCapabilityController capabCtrl;
    ItemStackHashRegister items;
    checkpointManager cpmgr;
    public pracEventHandler(pracManager prac, PlayerCapabilityController capabilitycontroller, ItemStackHashRegister itemstack, checkpointManager cpmg) {
        pracSystem = prac;
        capabCtrl = capabilitycontroller;
        items = itemstack;
        cpmgr = cpmg;
    }
    @EventHandler
    public void togglePrac(PlayerInteractEvent event) {
        //TODO: add isonground
        if (event.getItem() !=null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) &&event.getItem().getItemMeta().equals(items.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC).getItemMeta())) {
            if (pracSystem.playerCheckpointLocation.get(event.getPlayer().getUniqueId()) == null) {
                capabCtrl.setCapabilities(event.getPlayer(), PlayerCapabilityController.playerState.INPRAC);
                pracSystem.enterPrac(event.getPlayer());
                cpmgr.updateLastPos(event.getPlayer());
            } else {
                pracSystem.leavePrac(event.getPlayer());
                capabCtrl.setCapabilities(event.getPlayer(), PlayerCapabilityController.playerState.INPARKOUR);

            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void toggleFly(PlayerInteractEvent event) {
        if ( event.getAction() != null && event.getItem() !=null&& (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getItemMeta().equals(items.SpecialItems.get(ItemStackHashRegister.ITMES.FLY).getItemMeta())) {
            if(event.getPlayer().getAllowFlight()) {
                event.getPlayer().setAllowFlight(false);
                event.getPlayer().sendMessage(ChatColor.YELLOW + "Flight Deactivated");
            } else {
                event.getPlayer().setAllowFlight(true);
                event.getPlayer().sendMessage(ChatColor.YELLOW + "Flight Activated");
            }
        }
    }



}
