package github.com.raspberry.parkourplugin.tests;

import github.com.raspberry.parkourplugin.Helper.SqlInterface;

import java.sql.SQLException;
import java.util.Objects;

public class SQLiteInterfaceTest implements SqlInterface {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println( "query IP: " + Objects.equals( SqlInterface.query("InGameName", "IP").get(0), "50.47.152.188"));
        SqlInterface.AddNoDuplicateString("InGameName","InGameName","UUID","hi","2ndRaspberry5");
        SqlInterface.AddNoDuplicateString("InGameName","InGameName","UUID","hello","2ndTheTakenCookies");
        System.out.println( "write then read: "+  Objects.equals(SqlInterface.query("InGameName", "InGameName").get(0), "Raspberry5"));
        SqlInterface.insert("InGameName", "IP","3.14");
        SqlInterface.makeTable("TEST_TABLE_REMOVE_THIS_TABLE","CREATE TABLE IF NOT EXISTS warehouses ("
                + "	id INTEGER PRIMARY KEY,"
                + "	name text NOT NULL,"
                + "	capacity REAL"
                + ");");
        SqlInterface.makeTable("REMOVE_TABLE_FUNCTION_FAILED","CREATE TABLE IF NOT EXISTS warehouses ("
                + "	id INTEGER PRIMARY KEY,"
                 + "	name text NOT NULL,"
                + "	capacity REAL"
                + ");");
        SqlInterface.removeTable("warehouses");
    }
}
