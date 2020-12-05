
package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

/**
 * @description Provides methods for extracting data from the DB.
 *
 * @author MindFusion LLC
 * @version 1.0 $Date: 11/09/2016
 */
public class DBMetaData {

    //static variables
    static Connection connection = null;
    static DatabaseMetaData metadata = null;

    // Static block for initialization
    static {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("There was an error getting the connection: "
                    + e.getMessage());
        }

        try {
            metadata = connection.getMetaData();
        } catch (SQLException e) {
            System.err.println("There was an error getting the metadata: "
                    + e.getMessage());
        }
    }


    /**
     *
     * @return Arraylist with the names of the DB tables
     * * @throws SQLException
     */
    public static ArrayList<String> getTablesMetadata() throws SQLException {
        String table[] = {"TABLE"};
        ResultSet rs = null;
        ArrayList<String> tables = new ArrayList<String>();
        // reqest the table names
        rs = metadata.getTables(null, null, null, table);
    	System.out.println("rs"+rs.getString("TABLE_NAME"));

        //record the names
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }


    /**
     * Gets a list with all relationships in the DB.
     * @param tableNames A list with the tables in the DB.
     * @return Arraylist
     * * @throws SQLException
     */
    public static ArrayList<DBRelation> getRelationsMetadata(ArrayList<String> tableNames)
            throws SQLException {

        ArrayList<DBRelation> result = new ArrayList<DBRelation>();

        for (String tableName : tableNames) {
            //get the foreign keys
            ResultSet foreignKeys = metadata.getImportedKeys(connection.getCatalog(), null, tableName);
            while (foreignKeys.next()) {
                String fkTableName = foreignKeys.getString("FKTABLE_NAME");
                String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                String pkTableName = foreignKeys.getString("PKTABLE_NAME");
                String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");

                DBRelation relation = new DBRelation(pkColumnName, pkTableName, fkColumnName, fkTableName);

                result.add(relation);
            }
        }

        return result;

    }

    /**
     * Returns a list with DBColumn objects, one for each column
     * in the table provided as a parameter.
     *
     * @param tableName
     * @return List with the columns in the table.
     * @throws SQLException
     */
    public static ArrayList<DBColumn> getColumnsMetadata(String tableName)
            throws SQLException {
        ResultSet rs = null;

        rs = metadata.getPrimaryKeys(null, null, tableName);
    	System.out.println("rs"+rs.getString("COLUMN_NAME"));

        //get primary key
        ArrayList<String> primaryKeys = new ArrayList<String>();
        while (rs.next()) {
        	System.out.println("rs"+rs.getString("COLUMN_NAME"));

            primaryKeys.add(rs.getString("COLUMN_NAME"));
        }

        // gets the columns
        ArrayList<DBColumn> result = new ArrayList<DBColumn>();
        rs = metadata.getColumns(null, null, tableName, null);

        //records the data for each column
        while (rs.next()) {
        	System.out.println("rs"+rs.getString("COLUMN_NAME"));

            DBColumn column = new DBColumn(rs.getString("COLUMN_NAME"), rs.getString("TYPE_NAME"),
                    rs.getString("COLUMN_SIZE"));

            //checks if this column is the primary key
            for(String primaryKey : primaryKeys) {
                if (primaryKey.equals(column.name))
                    column.primaryKey = true;
            }

            result.add(column);
        }

        return result;
    }

}
