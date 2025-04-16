package github.com.raspberry.parkourplugin.imStuck.mapMaker;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class pressurePlateManEvent implements Listener {
    @EventHandler
    private void placeCpPlate(BlockPlaceEvent event) {
        if (!event.getBlock().getType().equals(Material.AIR) && event.getBlock().getType().equals(Material.GOLD_PLATE)) {
            Player player = event.getPlayer();
        }
    }
}
