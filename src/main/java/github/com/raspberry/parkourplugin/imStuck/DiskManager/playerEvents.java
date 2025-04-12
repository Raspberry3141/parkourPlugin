package github.com.raspberry.parkourplugin.imStuck.DiskManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.*;

public class playerEvents implements Listener {

    @EventHandler
    private void onLogin(PlayerLoginEvent event) throws ClassNotFoundException {
        //set up a con
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:C:/sqlite/dataBase/Players.db";
        logInfo(event.getPlayer(),url);
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) throws ClassNotFoundException {
        //set up a con
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:C:/sqlite/dataBase/Players.db";
        logIP(event.getPlayer(),url);
    }

    private void logInfo(Player player, String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            String UUID = player.getUniqueId().toString();
            String InGameName = player.getName();


            String sqlCheck = "SELECT COUNT(*) FROM InGameName WHERE UUID = ?";
            try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                pstmtCheck.setString(1, UUID);
                ResultSet rs = pstmtCheck.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Player data already exists. Skipping insert.");
                    return;
                }

                String sqlInsert = "INSERT INTO InGameName(UUID, InGameName) VALUES(?, ?)";
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    pstmtInsert.setString(1, UUID);
                    pstmtInsert.setString(2, InGameName);
                    pstmtInsert.executeUpdate();
                    System.out.println("Insert success");
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO-BUG:IP not being written when a duplicate exists
    private void logIP(Player player, String url) {
        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            String IP = player.getAddress().getAddress().getHostAddress();
            String UUID = player.getUniqueId().toString();


            String sqlCheck = "SELECT COUNT(*) FROM InGameName WHERE IP = ?";
            try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
                pstmtCheck.setString(1, IP);
                ResultSet rs = pstmtCheck.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    String sqlUpdate = "UPDATE InGameName SET IP = ? WHERE UUID = ?";
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        pstmtUpdate.setString(1, IP);
                        pstmtUpdate.executeUpdate();
                        System.out.println("IP updated successfully for UUID: " + UUID);
                    } catch (SQLException e) {
                        System.err.println("Error updating IP: " + e.getMessage());
                    }
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
