package github.com.raspberry.parkourplugin.tests;

import github.com.raspberry.parkourplugin.imStuck.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.ItemStackHashRegister;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class runTest implements CommandExecutor {
    ItemStackHashRegister factory;
    InventoryList invlst;
    public runTest(ItemStackHashRegister itemfactory, InventoryList invls) {
        factory = itemfactory;
        invlst =invls;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        factory.createList();
        invlst.initializeInventories();
        Player player = (Player) commandSender;
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.HUB));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.PRAC));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.PCP));
        player.getInventory().addItem(factory.SpecialItems.get(ItemStackHashRegister.ITMES.FLY));

        return true;
    }
}
