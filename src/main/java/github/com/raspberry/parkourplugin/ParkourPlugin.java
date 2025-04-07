package github.com.raspberry.parkourplugin;

import github.com.raspberry.parkourplugin.DiskManager.playerEvents;
import github.com.raspberry.parkourplugin.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.GUIInventories.ItemStackHashRegister;
import github.com.raspberry.parkourplugin.GUIInventories.MenuCommand;
import github.com.raspberry.parkourplugin.GUIInventories.RightCLickItemInMenuListener;
import github.com.raspberry.parkourplugin.checkpoint.eventHandlerMenu;
import github.com.raspberry.parkourplugin.playerItemsManager.PlayerCapabilityController;
import github.com.raspberry.parkourplugin.checkpoint.CommandPcp;
import github.com.raspberry.parkourplugin.pracSpec.pracEventHandler;
import github.com.raspberry.parkourplugin.pracSpec.pracManager;
import github.com.raspberry.parkourplugin.tests.runTest;
import org.bukkit.plugin.java.JavaPlugin;

public final class ParkourPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        pracManager pracsystem = new pracManager();
        //TODO: the itemhash is only craeted after /runTest is run. rmove th initialization somewhere after this method, but before usages.
        ItemStackHashRegister itemhash = new ItemStackHashRegister();
        PlayerCapabilityController capabilityController = new PlayerCapabilityController(itemhash);
        InventoryList invlist = new InventoryList(itemhash);

        getServer().getPluginManager().registerEvents(new pracEventHandler(pracsystem,capabilityController, itemhash),this);

        getServer().getPluginManager().registerEvents(new eventHandlerMenu(pracsystem),this);

        this.getCommand("pcp").setExecutor(new CommandPcp(capabilityController));

        this.getCommand("menu").setExecutor(new MenuCommand(invlist));

        getServer().getPluginManager().registerEvents(new RightCLickItemInMenuListener(invlist,itemhash),this);
        this.getCommand("runtest").setExecutor(new runTest(itemhash,invlist));

        getServer().getPluginManager().registerEvents(new playerEvents(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
