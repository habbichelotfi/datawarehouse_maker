package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @description Connects to the DB.
 *
 * @author MindFusion LLC
 * @version 1.0 $Date: 11/09/2016
 */

public class DBConnection {

    private static String DB_URL = "jdbc:postgresql://localhost:5432/finantial";
    private static String DB_USER = "postgres";
    private static String DB_PASSWORD = "linux12";
    private static String db_name="";
    public DBConnection(String n) {
    	this.db_name=n;
    }

    /**
     * Establishes a connection to the database.
     *
     * @return Connection to the database.
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL+db_name, DB_USER,
                DB_PASSWORD);
        System.err.println("The connection is successfully obtained");
        return connection;
    }
}
