package entropot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class PopulateFromCSV {

	void populateTableAcount(String csvFile, String tableName, String tableSchema) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");
		String line = "";
		String cvsSplitBy = ",";
		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {	
						String[] data=line.split(",");
						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));
								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}

			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}		
			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();
			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	void populateTablecard(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}

							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableClient(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableDisp(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableDistrict(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				System.out.println(data[i].substring(1, data[i].length()-1));
				pstatement.setFloat(i+1, Float.parseFloat(data[i].substring(1, data[i].length()-1)));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableLoan(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableOrder(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			String[] y=tableSchema.split(",");
			int i=0;
			String u="";
			for(int j=0;j<y.length-1;j++) {
				u=u+"?,";
			}
			u=u+"?";
			String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
			PreparedStatement pstatement = connection.prepareStatement(query);
					while ((line = br.readLine()) != null) {
							//String query="";
							// TODO
						String[] data=line.split(",");

						while(i<y.length) {
							if(y[i].contains("INTEGER")) {
								try{
									pstatement.setInt(i+1, Integer.parseInt(data[i]));
								}catch(NumberFormatException e) {
									pstatement.setInt(i+1, data[i].charAt(0));

								}
							}
			if(y[i].contains("VARCHAR")) {
				pstatement.setString(i+1, data[i]);
				
							}
			if(y[i].contains("Date")) {
				pstatement.setDate(i+1, Date.valueOf(data[i]));


			}
			if(y[i].contains("FLOAT")) {
				pstatement.setFloat(i+1, Float.parseFloat(data[i]));

			}
							i++;
						}
				

			pstatement.addBatch();
							System.out.println(line);
							//statement.executeUpdate(query);
						}
						br.close();
						pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	void populateTableTrans(String csvFile, String tableName, String tableSchema) throws SQLException {

		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/finantial", "postgres",
				"linux12");
		Statement statement = connection.createStatement();
		System.out.println("Connected to database");

		String line = "";
		String cvsSplitBy = ",";

		String[] att = tableSchema.split(",");
		List<String> attributesTypes = new ArrayList<String>();
		for (int i = 0; i < att.length; i++) {
			String[] tmp = att[i].trim().split("\\s+");
			if (tmp.length > 1)
				attributesTypes.add(tmp[1]);
			else
				System.out.println("Attribute syntax error: " + tmp[1]);
		}

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableSchema + ");");
		System.out.println("Table " + tableName + " created");

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			br.readLine();
			
String[] y=tableSchema.split(",");
int i=0;
String u="";
for(int j=0;j<y.length-1;j++) {
	u=u+"?,";
}
u=u+"?";
String query = "INSERT INTO "+tableName+" VALUES ("+u+");";
PreparedStatement pstatement = connection.prepareStatement(query);
		while ((line = br.readLine()) != null) {
				//String query="";
				// TODO
			String[] data=line.split(",");

			while(i<y.length) {
				if(y[i].contains("INTEGER")) {
					try{
						pstatement.setInt(i+1, Integer.parseInt(data[i]));
					}catch(NumberFormatException e) {
						pstatement.setInt(i+1, data[i].charAt(0));

					}
				}
if(y[i].contains("VARCHAR")) {
	pstatement.setString(i+1, data[i]);
	
				}
if(y[i].contains("Date")) {
	pstatement.setDate(i+1, Date.valueOf(data[i]));


}
if(y[i].contains("FLOAT")) {
	pstatement.setFloat(i+1, Float.parseFloat(data[i]));

}
				i++;
			}
	

pstatement.addBatch();
				System.out.println(line);
				//statement.executeUpdate(query);
			}
			br.close();
			pstatement.executeBatch();
			//connection.commit();
			connection.close();

			System.out.println("Insertion completed");

		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
	String getVar(String path,String r	){
		String line = "";
		String cvsSplitBy = ",";
String t="";
String[] y=r.split(",");
int i=0;
while(i<y.length) {
	if(y[i].contains("INTEGER")) {
		System.out.println("INTEGER");
	}
	i++;
}
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

line=			br.readLine();
	
			br.close();
			
	}catch(IOException e) {
		e.printStackTrace();
	}
		return line;
	}

	public static void main(String[] args) throws SQLException {

		PopulateFromCSV p1 = new PopulateFromCSV();

		//p1.populateTable("/home/lotfi/Bureau/Sales.csv", "Sales", "OrderID INTEGER,CustomurID INTEGER,ItemType VARCHAR,SalesChannel VARCHAR,OrderPriority VARCHAR,OrderDate DATE,ShipDate DATE,UnitsSold INTEGER,UnitPrice FLOAT,UnitCost FLOAT,TotalRevenue FLOAT,TotalCost FLOAT");
		
		p1.populateTableAcount("/home/lotfi/Bureau/Financial/account.csv", "account", "account_id INTEGER,district_id INTEGER,frequency VARCHAR,date VARCHAR");
		p1.populateTablecard("/home/lotfi/Bureau/Financial/card.csv", "card", "card_id INTEGER,disp_id INTEGER,type VARCHAR,issued VARCHAR");
		p1.populateTableClient("/home/lotfi/Bureau/Financial/disp.csv", "disp", "disp_id INTEGER,client_id INTEGER,account_id INTEGER,type VARCHAR");
		p1.populateTableDisp("/home/lotfi/Bureau/Financial/client.csv", "client", "client_id INTEGER,gender VARCHAR,birth_date VARCHAR,district_id INTEGER");
		p1.populateTableDistrict("/home/lotfi/Bureau/Financial/district.csv", "district", "district_id INTEGER,A2 VARCHAR,A3 VARCHAR,A4 INTEGER,A5 INTEGER,A6 INTEGER,A7 INTEGER,A8 INTEGER,A9 INTEGER,A10 FLOAT,A11 INTEGER,A12 FLOAT,A13 FLOAT,A14 INTEGER,A15 INTEGER,A16 INTEGER");
		p1.populateTableLoan("/home/lotfi/Bureau/Financial/loan.csv", "loan", "loan_id INTEGER,account_id INTEGER,date VARCHAR,amount INTEGER,duration INTEGER,payments INTEGER,status VARCHAR");
		p1.populateTableOrder("/home/lotfi/Bureau/Financial/order.csv", "order_", "order_id INTEGER,account_id INTEGER,bank_to VARCHAR,account_to INTEGER,amount INTEGER,k_symbol VARCHAR");
		//p1.populateTableTrans("/home/lotfi/Bureau/Financial/trans.csv", "trans", "trans_id INTEGER,account_id INTEGER,date VARCHAR,type VARCHAR,operation VARCHAR,amount INTEGER,balance INTEGER,k_symbol VARCHAR,bank VARCHAR,account INTEGER");

		System.out.println(p1.getVar("/home/lotfi/Bureau/Financial/account.csv","card_id INTEGER,disp_id INTEGER,type VARCHAR,issued Date"));
		//System.out.println(p1.getVar("/home/lotfi/Bureau/Financial/district.csv"));

	}

}
