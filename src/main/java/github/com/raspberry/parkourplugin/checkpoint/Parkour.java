package github.com.raspberry.parkourplugin.checkpoint;

import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class Parkour {
    ParkourIdManager pkmgr;
    long id;
    LocalDateTime publishedDate;
    String author;
    String verifier;
    boolean published = false;

    public Parkour(ParkourIdManager pkm) {
        pkmgr = pkm;
        id = pkmgr.updateIdNumber();
        //TODO: create a database
    }

    private boolean publish(Player player) {
        if (true) {
            publishedDate = LocalDateTime.now();
            author = player.getDisplayName();
            published = true;
            return true;
        }
        return false;
    }
}