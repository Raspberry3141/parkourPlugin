package github.com.raspberry.parkourplugin.GUIInventories;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCommand implements CommandExecutor {
    InventoryList invlist;

    public MenuCommand(InventoryList inls) {
        invlist = inls;
    }
    //used by NPCs
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        player.openInventory(invlist.InventoryLookUp.get(InventoryList.Inventories.LOBBY));
        return  true;
    }
}
