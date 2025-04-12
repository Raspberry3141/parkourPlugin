package github.com.raspberry.parkourplugin.imStuck.GUIInventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryList {
    public HashMap<Inventories,Inventory> InventoryLookUp= new HashMap<Inventories,Inventory>();
    private ItemStackHashRegister specialItems;

    enum Inventories{
        LOBBY,
        MAPMAKER,
    }

    public InventoryList(ItemStackHashRegister items) {
        specialItems = items;
    }

    public void initializeInventories() {
        Inventory inv = Bukkit.createInventory(null,27,"Select a Game");
        inv.setItem(11,specialItems.SpecialItems.get(ItemStackHashRegister.ITMES.RANKUP));
        inv.setItem(13,specialItems.SpecialItems.get(ItemStackHashRegister.ITMES.COURSES));
        inv.setItem(15,specialItems.SpecialItems.get(ItemStackHashRegister.ITMES.COMMUNITY));
        inv.setItem(22,specialItems.SpecialItems.get(ItemStackHashRegister.ITMES.HUB));

        InventoryLookUp.put(Inventories.LOBBY,inv);

        inv = Bukkit.createInventory(null,54,"");
        //TODO add after making map manager
        InventoryLookUp.put(Inventories.MAPMAKER,inv);
    }

}
