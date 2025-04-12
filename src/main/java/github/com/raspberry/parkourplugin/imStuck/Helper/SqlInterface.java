package github.com.raspberry.parkourplugin.imStuck.Helper;

import java.sql.*;
import java.util.ArrayList;

//TODO-REFACTOR: why is this a interface, not a regular class?
public interface SqlInterface {
    String url = "jdbc:sqlite:C:/sqlite/dataBase/Parkour.db";

    static void AddNoDuplicateString(String table, String targetColumn, String keyColumn, String keyNew, String newData) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String sql = "INSERT OR IGNORE INTO " + table + "(" +  keyColumn + ", " + targetColumn + ") VALUES(?,?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyNew);
            stmt.setString(2,newData);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    static void insert(String table, String column,String data) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String sql = "INSERT INTO " + table + "(" + column + ") VALUES(?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setString(1, data);
             stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    static ArrayList<String> query(String table, String row) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String sql = "SELECT " + row + " FROM " + table;
        ArrayList<String> foundValues = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                foundValues.add(rs.getString(row));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return foundValues;
    }

    static void makeTable(String sqlCode) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute(sqlCode);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void removeTable(String tableName) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        String sql = "DROP TABLE IF EXISTS " + tableName;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Table deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error while deleting the table: " + e.getMessage());
        }
    }

}
