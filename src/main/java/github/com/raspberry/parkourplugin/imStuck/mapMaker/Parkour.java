package github.com.raspberry.parkourplugin.imStuck.mapMaker;

import github.com.raspberry.parkourplugin.imStuck.Helper.SqlInterface;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;

public class Parkour implements SqlInterface {
    ParkourIdManager pkmgr;
    long id;
    LocalDateTime publishedDate;
    String author;
    String verifier;
    boolean published = false;

    public Parkour(Long pkid) throws ClassNotFoundException {
        id = pkid;
        SqlInterface.makeTable(
                "CREATE TABLE " + id +"_parkour_checkpoint (\n" +
                        "    checkpoint_number INTEGER UNIQUE\n" +
                        "                              PRIMARY KEY,\n" +
                        "    blockpos_x        INTEGER,\n" +
                        "    blockpos_y        INTEGER,\n" +
                        "    blockpos_z        INTEGER\n" +
                        ");\n"
        );
        SqlInterface.makeTable("CREATE TABLE " + id + "_parkour (\n" +
                "    player_UUID         TEXT    UNIQUE\n" +
                "                                PRIMARY KEY,\n" +
                "    pos_x_double        NUMERIC,\n" +
                "    pos_y_double        NUMERIC,\n" +
                "    pos_z_double        NUMERIC,\n" +
                "    last_checkpoint     INTEGER,\n" +
                "    player_display_name TEXT\n" +
                ");\n");
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