package github.com.raspberry.parkourplugin;

import github.com.raspberry.parkourplugin.imStuck.DiskManager.playerEvents;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.InventoryList;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.ItemStackHashRegister;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.MenuCommand;
import github.com.raspberry.parkourplugin.imStuck.GUIInventories.RightCLickItemInMenuListener;
import github.com.raspberry.parkourplugin.imStuck.mapMaker.ParkourIdManager;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.eventHandlerMenu;
import github.com.raspberry.parkourplugin.imStuck.mapMaker.mapMakerCommand;
import github.com.raspberry.parkourplugin.imStuck.playerItemsManager.PlayerCapabilityController;
import github.com.raspberry.parkourplugin.imStuck.checkpoint.CommandPcp;
import github.com.raspberry.parkourplugin.imStuck.pracSpec.pracEventHandler;
import github.com.raspberry.parkourplugin.imStuck.pracSpec.pracManager;
import github.com.raspberry.parkourplugin.tests.runTest;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class ParkourPlugin extends JavaPlugin {
    public File customConfigFile;
    //private File customConfigFile2;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {

        ParkourIdManager pkmgr = new ParkourIdManager();
        pracManager pracsystem = new pracManager();
        //TODO: the itemhash is only craeted after /runTest is run. rmove th initialization somewhere after this method, but before usages.
        ItemStackHashRegister itemhash = new ItemStackHashRegister();
        PlayerCapabilityController capabilityController = new PlayerCapabilityController(itemhash);
        InventoryList invlist = new InventoryList(itemhash);

        this.getCommand("parkour").setExecutor(new mapMakerCommand(pkmgr));

        getServer().getPluginManager().registerEvents(new pracEventHandler(pracsystem,capabilityController, itemhash),this);

        getServer().getPluginManager().registerEvents(new eventHandlerMenu(pracsystem),this);

        this.getCommand("pcp").setExecutor(new CommandPcp(capabilityController));

        this.getCommand("menu").setExecutor(new MenuCommand(invlist));

        getServer().getPluginManager().registerEvents(new RightCLickItemInMenuListener(invlist,itemhash),this);
        this.getCommand("runtest").setExecutor(new runTest(this,itemhash,invlist));

        getServer().getPluginManager().registerEvents(new playerEvents(),this);

        createCustomConfig();
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "custom.yml");
        //customConfigFile2 = new File(getDataFolder(), "secondcustom.yml");

        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("custom.yml", false);
        }
        //if (!customConfigFile2.exists()) {
        //    customConfigFile2.getParentFile().mkdirs();
        //    saveResource("secondcustom.yml", false);
        //}


        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
            //customConfig.load(customConfigFile2);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
