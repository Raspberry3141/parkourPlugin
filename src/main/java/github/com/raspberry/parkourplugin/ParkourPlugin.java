package github.com.raspberry.parkourplugin;

import github.com.raspberry.parkourplugin.imStuck.DiskManager.playerEvents;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.ItemStackHashRegister;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.MenuCommand;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.RightCLickItemInMenuListener;
import github.com.raspberry.parkourplugin.imStuck.Helper.configFileManager;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.checkpointManager;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.parkourLoaderEvent;
import github.com.raspberry.parkourplugin.imStuck.mapMaker.ParkourIdManager;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.eventHandlerMenu;
import github.com.raspberry.parkourplugin.imStuck.mapMaker.mapMakerCommand;
import github.com.raspberry.parkourplugin.imStuck.playerItemsManager.PlayerCapabilityController;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.CommandPcp;
import github.com.raspberry.parkourplugin.imStuck.pracSpec.pracEventHandler;
import github.com.raspberry.parkourplugin.imStuck.pracSpec.pracManager;
import github.com.raspberry.parkourplugin.tests.runTest;
import org.bukkit.plugin.java.JavaPlugin;


public final class ParkourPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        ParkourIdManager pkmgr = new ParkourIdManager();
        pracManager pracsystem = new pracManager();
        //TODO: the itemhash is only created after /runTest is run. remove th initialization somewhere after this method, but before usages.
        ItemStackHashRegister itemhash = new ItemStackHashRegister();
        PlayerCapabilityController capabilityController = new PlayerCapabilityController(itemhash);
        InventoryList invlist = new InventoryList(itemhash);
        configFileManager configFileManager = new configFileManager(this);
        checkpointManager cpmgr = new checkpointManager(configFileManager);

        this.getCommand("parkour").setExecutor(new mapMakerCommand(pkmgr,configFileManager));

        getServer().getPluginManager().registerEvents(new pracEventHandler(pracsystem,capabilityController, itemhash),this);

        getServer().getPluginManager().registerEvents(new eventHandlerMenu(pracsystem,cpmgr),this);

        getServer().getPluginManager().registerEvents(new parkourLoaderEvent(configFileManager,cpmgr),this);

        this.getCommand("pcp").setExecutor(new CommandPcp(capabilityController,cpmgr));

        this.getCommand("menu").setExecutor(new MenuCommand(invlist));

        getServer().getPluginManager().registerEvents(new RightCLickItemInMenuListener(invlist,itemhash),this);
        this.getCommand("runtest").setExecutor(new runTest(this,itemhash,invlist));

        getServer().getPluginManager().registerEvents(new playerEvents(configFileManager),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
