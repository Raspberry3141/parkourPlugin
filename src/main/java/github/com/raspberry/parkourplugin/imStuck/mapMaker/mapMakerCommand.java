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
import java.util.HashMap;
import java.util.Objects;

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
            initializeParkourInYml(id,strings[1], player.getDisplayName(),LocalDateTime.now());
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
        }
        return false;
    }

    private void addcp(Player player) {
        if (player.getLocation().getBlock().getType().equals(Material.GOLD_PLATE)) {
            FileConfiguration config = conffileman.getParkourConfigFile();
            HashMap<String, Integer> checkpointList = new HashMap<>();
            checkpointList.put("x",player.getLocation().getBlockX());
            checkpointList.put("y",player.getLocation().getBlockY());
            checkpointList.put("Z",player.getLocation().getBlockZ());
            config.createSection("checkpoint",checkpointList);
            conffileman.saveParkourConfigFile(config);

        }
    }

    private void initializeParkourInYml(long id, String displayName, String author, LocalDateTime date) {
        FileConfiguration config = conffileman.getParkourConfigFile();
        config.createSection("UUID");
        config.createSection("name");
        config.createSection("author");
        config.createSection("date");
        config.set("UUID",id);
        config.set("name",displayName);
        config.set("author", author);
        config.set("date",date);
        conffileman.saveParkourConfigFile(config);
    }
}
