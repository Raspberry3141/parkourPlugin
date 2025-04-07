package github.com.raspberry.parkourplugin.DiskManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.UUID;

public class playerEvents implements Listener {
    Yaml yaml = new Yaml();

    @EventHandler
    private void onJoin(PlayerLoginEvent event) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("C:\\Database\\Players.yml");
            HashMap<String, Object> players = yaml.load(inputStream);

            Bukkit.getLogger().info(event.getPlayer().getUniqueId().toString());
            UUID playerUuid = event.getPlayer().getUniqueId();
            Object foundUuid = players.get(playerUuid);

            if (foundUuid != null) {
                // Proceed with the foundUuid
            } else {
                // Handle the case where the UUID is not found
                Bukkit.getLogger().warning("Player UUID " + playerUuid + " not found in the players map.");
            }

            if (foundUuid== null) {
                UUID newPlayerUUID = event.getPlayer().getUniqueId();
                InetSocketAddress newPlayerIP = event.getPlayer().getAddress();

                createNewPlayerData(newPlayerUUID,newPlayerIP,inputStream);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewPlayerData(UUID uuid, InetSocketAddress address,FileInputStream input) {
        HashMap<String,Object> existingData = yaml.load(input);
        HashMap<String,HashMap<String,Object>> newPlayerData = new HashMap<>();
        HashMap<String, Object> newPlayerInfo = new HashMap<>();
        newPlayerInfo.put("IP", address);
        newPlayerData.put(uuid.toString(),newPlayerInfo);

        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yamlWithOptions = new Yaml(options);
        String output = yaml.dump(newPlayerData);
        System.out.println("CREATING NEW PLAYER DATA------------------------------------------------------");
    }
}
