package github.com.raspberry.parkourplugin.imStuck.GUIInventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RightCLickItemInMenuListener implements Listener {
    HashMap<ItemStackHashRegister.ITMES, ItemStack> items;
    InventoryList invlist;

    public RightCLickItemInMenuListener(InventoryList invlt, ItemStackHashRegister itemhash) {
        items = itemhash.SpecialItems;
        invlist = invlt;
    }

    @EventHandler
    public void RightClickOnItem(PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && event.getItem().getItemMeta().equals(items.get(ItemStackHashRegister.ITMES.HUB).getItemMeta())) {
            event.getPlayer().openInventory(invlist.InventoryLookUp.get(InventoryList.Inventories.LOBBY));
        }
    }


    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        if ((event.isRightClick() || event.isLeftClick()) && !event.getCurrentItem().getType().equals(Material.AIR)) {
            if (event.getCurrentItem().getItemMeta().equals(items.get(ItemStackHashRegister.ITMES.RANKUP).getItemMeta())) {
                event.getWhoClicked().teleport(new Location(Bukkit.getWorld("purple"),242.5, 63, 1348.5));
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Teleporting..");
                event.setCancelled(true);

            } else if (event.getCurrentItem().getItemMeta().equals(items.get(ItemStackHashRegister.ITMES.COURSES).getItemMeta())) {
                event.getWhoClicked().teleport(new Location(Bukkit.getWorld("simple"),191.5, 38, 195.5));
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Teleporting..");
                event.setCancelled(true);

            } else if (event.getCurrentItem().getItemMeta().equals(items.get(ItemStackHashRegister.ITMES.COMMUNITY).getItemMeta())) {
                event.getWhoClicked().teleport(new Location(Bukkit.getWorld("cecemels"),0.5, 51, 0.5));
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Teleporting..");
                event.setCancelled(true);
            } else if (event.getCurrentItem().getItemMeta().equals(items.get(ItemStackHashRegister.ITMES.HUB).getItemMeta())) {
                event.getWhoClicked().teleport(new Location(Bukkit.getWorld("small"),3.5, 142, 33.5));
                event.getWhoClicked().sendMessage(ChatColor.YELLOW + "Teleporting..");
                event.setCancelled(true);
            }
        }
    }

}
