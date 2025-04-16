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
        if (Objects.equals(strings[0], "create") ) {
            long id = pkmgr.updateIdNumber();
            WorldCreator wc = new WorldCreator(String.valueOf(id));
            wc.type(WorldType.FLAT);
            wc.generatorSettings("2;0;1;"); //void world
            wc.createWorld();
            player.teleport(new Location(Bukkit.getWorld(String.valueOf(id)), 0.5, 64.0, 0.5));

            initializeParkourInYml(player.getWorld().getUID().toString(),strings[1], player.getDisplayName(),LocalDateTime.now().toString());
            try {
                Parkour newpk = new Parkour(id);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            player.sendMessage(ChatColor.YELLOW + "Parkour created! ID: " + id);
            return true;
        } else if (Objects.equals(strings[0], "addcp")) {
            //addcp(player);
            //player.sendMessage(ChatColor.YELLOW + "Added a checkpoint");
            player.sendMessage(ChatColor.YELLOW + "To be implemented, sorry!");
            return true;
        } else if (Objects.equals(strings[0], "deletecp")) {
            //deletecp(player);
            player.sendMessage(ChatColor.YELLOW + "To be implemented, sorry!");
            return true;
        } else if (Objects.equals(strings[0], "setstart")) {
            setStartLoc(player);
            return true;
        } else if (Objects.equals(strings[0],"setend")) {
            setEndLoc(player);
            return true;

        }
        return false;
    }

    private void deleteCp(Player player) {


    }

    private void setEndLoc(Player player) {
        if (player.getLocation().getBlock().getType().equals(Material.GOLD_PLATE)) {
            FileConfiguration config = conffileman.getParkourConfigFile();
            String uuidKey = player.getWorld().getUID().toString();

            List<HashMap<Character ,Integer>> currentCps= (List<HashMap<Character, Integer>>) config.getList(uuidKey+".checkpoints");
            HashMap<Character,Integer> locxyz = new HashMap<>();
            locxyz.put('x',player.getLocation().getBlockX());
            locxyz.put('y',player.getLocation().getBlockY());
            locxyz.put('z',player.getLocation().getBlockZ());
            currentCps.add(locxyz);

            config.createSection(uuidKey+".checkpoints");
            config.set(uuidKey+".checkpoints",currentCps);
            conffileman.saveParkourConfigFile(config);
        }
    }

    private void setStartLoc(Player player) {
        if (player.getLocation().getBlock().getType().equals(Material.GOLD_PLATE)) {
            FileConfiguration config = conffileman.getParkourConfigFile();
            String uuidKey = player.getWorld().getUID().toString();
            ArrayList<HashMap<Character,Integer>> cpLocxyz = new ArrayList<>();
            HashMap<Character,Integer> locxyz = new HashMap<>();
            locxyz.put('x',player.getLocation().getBlockX());
            locxyz.put('y',player.getLocation().getBlockY());
            locxyz.put('z',player.getLocation().getBlockZ());
            cpLocxyz.add(locxyz);

            config.createSection(uuidKey+".checkpoints");
            config.set(uuidKey+".checkpoints",cpLocxyz);
            conffileman.saveParkourConfigFile(config);
        } else {
            player.sendMessage(ChatColor.YELLOW + "Please stand on a golden pressure plate and try again!");
        }
    }

    private void initializeParkourInYml(String uuid, String displayName, String author, String date) {
        FileConfiguration config = conffileman.getParkourConfigFile();
        config.createSection(uuid);
        config.createSection(".name");
        config.createSection(uuid+".author");
        config.createSection(uuid+".date");
        config.createSection(uuid+".checkpoints");
        config.createSection(uuid+".players");
        config.set(uuid+".name",displayName);
        config.set(uuid+".author", author);
        config.set(uuid+".date",date);
        conffileman.saveParkourConfigFile(config);
    }

}
