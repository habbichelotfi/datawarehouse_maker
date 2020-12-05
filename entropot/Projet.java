package entropot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Projet {
	public static void main(String[] args) throws SQLException {
	Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dw", "postgres",
			"linux12");
	Statement statement = connection.createStatement();
	System.out.println("Connected to database");
	}
	
}
