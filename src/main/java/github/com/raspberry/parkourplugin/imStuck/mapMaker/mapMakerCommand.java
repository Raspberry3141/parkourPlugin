package github.com.raspberry.parkourplugin.imStuck.mapMaker;


import github.com.raspberry.parkourplugin.imStuck.Helper.SqlInterface;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class mapMakerCommand implements CommandExecutor, SqlInterface {
    ParkourIdManager pkmgr;
    public mapMakerCommand(ParkourIdManager pk) {
        pkmgr = pk;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        if (Objects.equals(strings[0], "create")) {
            long id = pkmgr.updateIdNumber();
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

        }
        return false;
    }
}
