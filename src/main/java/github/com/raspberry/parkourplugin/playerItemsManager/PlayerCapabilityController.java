package github.com.raspberry.parkourplugin.playerItemsManager;

import github.com.raspberry.parkourplugin.GUIInventories.ItemStackHashRegister;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerCapabilityController {
    ItemStackHashRegister items;
    public PlayerCapabilityController(ItemStackHashRegister itemhash) {
        items = itemhash;
    }
    public enum playerState{
        INLOBBY,
        INPARKOUR,
        ISBUILDING,
        INPRAC
    }

    private HashMap<UUID,playerState> playerStates = new HashMap<UUID,playerState>();

    public void setCapabilities(Player player,playerState state) {
        if (player != null) {
            playerStates.put(player.getUniqueId(),state);
            setItems(player);
        }
    }

    public playerState getCapabilities(Player player) {
        return playerStates.get(player.getUniqueId());
    }

    public void setItems(Player player) {
        if (playerStates.get(player.getUniqueId()) == playerState.INLOBBY) {
            //TODO: add lobby item
            player.getInventory().setItem(0,items.SpecialItems.get(ItemStackHashRegister.ITMES.HUB));
            player.getInventory().setItem(7,items.SpecialItems.get(ItemStackHashRegister.ITMES.OCCUPIED_SLOT));
            player.getInventory().setItem(8,items.SpecialItems.get(ItemStackHashRegister.ITMES.OCCUPIED_SLOT));
        } else if (playerStates.get(player.getUniqueId()) == playerState.INPARKOUR) {
            player.getInventory().setItem(0,items.SpecialItems.get(ItemStackHashRegister.ITMES.PCP));
            player.getInventory().setItem(7,items.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC));
            player.getInventory().setItem(8,items.SpecialItems.get(ItemStackHashRegister.ITMES.OCCUPIED_SLOT));
        } else if (playerStates.get(player.getUniqueId()) == playerState.INPRAC) {
            player.getInventory().setItem(0,items.SpecialItems.get(ItemStackHashRegister.ITMES.PCP));
            player.getInventory().setItem(7,items.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC));
            player.getInventory().setItem(8,items.SpecialItems.get(ItemStackHashRegister.ITMES.FLY));
        } else if(playerStates.get(player.getUniqueId()) == playerState.ISBUILDING) {
            player.getInventory().clear();
        }
    }


}
