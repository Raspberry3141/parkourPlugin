package github.com.raspberry.parkourplugin.imStuck.Helper;

import github.com.raspberry.parkourplugin.ParkourPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class configFileManager {
    private File playerListFile;
    private File parkourListFile;
    private FileConfiguration playerConfig;
    private FileConfiguration parkourConfig;


    ParkourPlugin thePlugin;

    public configFileManager(ParkourPlugin pkplug) {
        thePlugin = pkplug;
        createCustomConfig();
        Bukkit.getLogger().info("CONFIG MANAGER CREATED AS EXPECTED__________________________");
    }

    public ParkourPlugin getThePlugin() {
        return this.thePlugin;
    }

    private void createCustomConfig() {
        playerListFile = new File(thePlugin.getDataFolder(), "player.yml");
        parkourListFile = new File(thePlugin.getDataFolder(), "parkour.yml");

        if (!playerListFile.exists()) {
            playerListFile.getParentFile().mkdirs();
            thePlugin.saveResource("player.yml", false);
        }
        if (!parkourListFile.exists()) {
            parkourListFile.getParentFile().mkdirs();
            thePlugin.saveResource("parkour.yml", false);
        }

        playerConfig = new YamlConfiguration();
        parkourConfig = new YamlConfiguration();
        try {
            playerConfig.load(playerListFile);
            parkourConfig.load(parkourListFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getPlayerConfigFile() {
        return this.playerConfig;
    }

    public FileConfiguration getParkourConfigFile() {
        return this.parkourConfig;
    }

    public void savePlayerConfigFile(FileConfiguration fileconf) {
        try {
                fileconf.save(playerListFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public void saveParkourConfigFile(FileConfiguration fileconf) {
        try {
            fileconf.save(parkourListFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
