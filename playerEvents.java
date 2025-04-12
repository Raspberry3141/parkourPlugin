package github.com.raspberry.parkourplugin.imStuck.DiskManager;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.sql.*;

public class playerEvents implements Listener {

    @EventHandler
    private void onJoin(PlayerLoginEvent event) throws ClassNotFoundException {
        Bukkit.getLogger().info("LOGIN EVENT FIRED AS EXPECTED--------------------------");

        //set up a con
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:C:/sqlite/db/chinook.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
            if (conn != null) {
                //create a table
                String sqlCreate = "CREATE TABLE IF NOT EXISTS warehouses ("
                        + "	id INTEGER PRIMARY KEY,"
                        + "	name text NOT NULL,"
                        + "	capacity REAL"
                        + ");";

                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                Statement stmt = conn.createStatement();
                stmt.execute(sqlCreate);

                //insert data to a table
                String[] names = new String[] {event.get};
                int[] capacities = new int[] {3000,4000,5000};
                String sqlInsert = "INSERT INTO warehouses(names,capatties) VALUES(?,?)";

                try (Connection connInsert = DriverManager.getConnection(url);
                     PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {

                    for(int i = 0; i < 3; i++){
                        pstmt.setString(1, names[i]);
                        pstmt.setDouble(2, capacities[i]);
                        pstmt.executeUpdate();
                    }
                    System.out.println("Insert success");
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

                //Quesry from a table
                String sql = "SELECT id, name, capacity FROM warehouses";

                try (Connection connQuesry = DriverManager.getConnection(url);
                     ResultSet rs = stmt.executeQuery(sql)) {

                    while (rs.next()) {
                        System.out.printf("%-5s%-25s%-10s%n",
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("capacity")
                        );
                    }
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
