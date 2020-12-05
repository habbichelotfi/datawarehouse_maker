package entropot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class activite2 {
	public static void main(String[] args) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dw", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");
		String query = "SELECT * FROM Customers";
		ResultSet rs = statement.executeQuery(query);
	      
	      // iterate through the java resultset
	      while (rs.next())
	      {
	        int id = rs.getInt("CustomerID");
	        String firstName = rs.getString("CustomerName");
	        String lastName = rs.getString("Country");
	        String lastName1 = rs.getString("Region");
	      
	        
	        // print the results
	        System.out.format("%s, %s, %s, %s\n", id, firstName, lastName, lastName1);
	      }
	}
}
