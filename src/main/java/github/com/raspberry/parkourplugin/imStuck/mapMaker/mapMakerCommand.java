package github.com.raspberry.parkourplugin.imStuck.mapMaker;


import github.com.raspberry.parkourplugin.imStuck.Helper.SqlInterface;
import github.com.raspberry.parkourplugin.imStuck.Helper.configFileManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.*;
//TODO:finish this feature where checkpoints are ordered instead of just tp plate.
public class mapMakerCommand implements CommandExecutor, SqlInterface {
    ParkourIdManager pkmgr;
    configFileManager conffileman;
    public mapMakerCommand(ParkourIdManager pk, configFileManager configman) {
        pkmgr = pk;
        conffileman = configman;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (Objects.equals(strings[0], "create") && strings.length ==1) {
            long id = pkmgr.updateIdNumber();
            initializeParkourInYml(player.getWorld().getUID(),strings[1], player.getDisplayName(),LocalDateTime.now());
            try {
                Parkour newpk = new Parkour(id);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            WorldCreator wc = new WorldCreator(String.valueOf(id));
            wc.type(WorldType.FLAT);
            wc.generatorSettings("2;0;1;"); //void world
            wc.createWorld();
            player.teleport(new Location(Bukkit.getWorld(String.valueOf(id)), 0.5, 64.0, 0.5));
            player.sendMessage(ChatColor.YELLOW + "PARKOUR CREATED! ID: " + id);
        } else if (Objects.equals(strings[0], "addcp")) {
            addcp(player);
            player.sendMessage(ChatColor.YELLOW + "Added a checkpoint");
        } else if (Objects.equals(strings[0], "deletecp")) {
            deletecp(player);
        }
        return false;
    }

    private void deletecp(Player player) {

    }

    private void addcp(Player player) {
        if (player.getLocation().getBlock().getType().equals(Material.GOLD_PLATE)) {
            FileConfiguration config = conffileman.getParkourConfigFile();

            ArrayList<Location> checkpointList = new ArrayList<>();
            checkpointList.add(player.getLocation());


            conffileman.saveParkourConfigFile(config);

        }
    }

    private void initializeParkourInYml(UUID uuid, String displayName, String author, LocalDateTime date) {
        FileConfiguration config = conffileman.getParkourConfigFile();
        config.createSection("UUID");
        config.createSection("name");
        config.createSection("author");
        config.createSection("date");
        config.set("UUID",uuid);
        config.set("name",displayName);
        config.set("author", author);
        config.set("date",date);
        conffileman.saveParkourConfigFile(config);
    }

}
