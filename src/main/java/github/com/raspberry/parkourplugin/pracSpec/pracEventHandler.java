package github.com.raspberry.parkourplugin.pracSpec;

import github.com.raspberry.parkourplugin.GUIInventories.ItemStackHashRegister;
import github.com.raspberry.parkourplugin.playerItemsManager.PlayerCapabilityController;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class pracEventHandler implements Listener {
    pracManager pracSystem;
    PlayerCapabilityController capabCtrl;
    ItemStackHashRegister items;
    public pracEventHandler(pracManager prac, PlayerCapabilityController capabilitycontroller, ItemStackHashRegister itemstack) {
        pracSystem = prac;
        capabCtrl = capabilitycontroller;
        items = itemstack;
    }
    @EventHandler
    public void togglePrac(PlayerInteractEvent event) {
        //TODO: add isonground
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) &&event.getItem().getItemMeta().equals(items.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC).getItemMeta())) {
            if (pracSystem.playerCheckpointLocation.get(event.getPlayer().getUniqueId()) == null) {
                capabCtrl.setCapabilities(event.getPlayer(), PlayerCapabilityController.playerState.INPRAC);
                pracSystem.enterPrac(event.getPlayer());
            } else {
                pracSystem.leavePrac(event.getPlayer());
                capabCtrl.setCapabilities(event.getPlayer(), PlayerCapabilityController.playerState.INPARKOUR);

            }
        }
    }

    @EventHandler
    public void toggleFly(PlayerInteractEvent event) {
        if ( event.getAction() != null && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getItemMeta().equals(items.SpecialItems.get(ItemStackHashRegister.ITMES.FLY).getItemMeta())) {
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
