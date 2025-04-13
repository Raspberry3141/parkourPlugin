package github.com.raspberry.parkourplugin.tests;

import github.com.raspberry.parkourplugin.ParkourPlugin;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.ItemStackHashRegister;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;


public class runTest implements CommandExecutor {
    ItemStackHashRegister factory;
    InventoryList invlst;
    ParkourPlugin plug;
    public runTest(ParkourPlugin pkplug,ItemStackHashRegister itemfactory, InventoryList invls) {
        factory = itemfactory;
        invlst =invls;
        plug =pkplug;
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


        plug.getCustomConfig().set("text", "changed from hi");
        plug.getCustomConfig().createSection("newpath.newestpath");

        try {
            plug.getCustomConfig().save(plug.customConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
