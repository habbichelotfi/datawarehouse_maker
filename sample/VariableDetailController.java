package sample;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VariableDetailController {
	@FXML
	private Button modifier;
	@FXML
	private Label nom_table;
	
	
	@FXML
	private Button enrengistrer;

	@FXML
	private TableView<VariableDetail> table;
	@FXML
	private TableColumn<VariableDetail,String> check;
	@FXML
	private TableColumn<VariableDetail,String> nom;
	@FXML
	private TableColumn<VariableDetail,String> type;
	
	@FXML
	private TableColumn<VariableDetail,String> ifnull;
	@FXML
	private Button supprimer;
	@FXML
	private Button back;

	private Main mainApp;
	private final ObservableList<VariableDetail> vars = FXCollections.observableArrayList();
	
	public VariableDetailController() {
		
	}
	@FXML
	void Supprimer() {
		
	}
	@FXML
	void Modify(){
		
	}
	@FXML
	void Goback() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Vous les vous quitter?");
		//alert.setHeaderText("Look, a Confirmation Dialog");
		alert.setContentText("Etes vous sur?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tables.fxml"));
		    Parent parent = fxmlLoader.load();
		    TablesController dialogController = fxmlLoader.getController();
		    // dialogController.setAppMainObservableList(tvObservableList);
		    //dialogController.setMainApp(mainApp);
		    Scene scene = new Scene(parent,  727, 533);
		    Stage stage = new Stage();
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.setScene(scene);
		    stage.showAndWait();
		    dialogController.getTable(stage);
		    System.out.println("jaja");

		    // ... user chose OK
		} else {
			alert.close();
		    // ... user chose CANCEL or closed the dialog
		}
	}
	public void transferMessage(String t,String r) throws SQLException {
		nom_table.setText(t);
		System.out.println(t+r);
		// TODO Auto-generated method stub
		check.setCellValueFactory(new PropertyValueFactory<VariableDetail,String>("check"));

		nom.setCellValueFactory(new PropertyValueFactory<VariableDetail,String>("nom_variable"));
		type.setCellValueFactory(new PropertyValueFactory<VariableDetail,String>("type"));
		ifnull.setCellValueFactory(new PropertyValueFactory<VariableDetail,String>("null_"));
		
		  String databaseURL = "jdbc:postgresql://localhost:5432/"+r;
		    String username = "postgres";
		    String password = "linux12";
		    Connection connection = DriverManager.getConnection(databaseURL, username, password);
		    System.out.println("connected");
		    String y=""+nom_table.getText().charAt(0);
		    y=y.toUpperCase()+nom_table.getText().substring(1);
		    String sql="SELECT * FROM information_schema.columns WHERE TABLE_NAME ='"+nom_table.getText()+"';";
		    System.out.print(sql);
		    Statement statement = connection.createStatement();
		    ResultSet result = statement.executeQuery(sql);
			System.out.println(getFKeyData(nom_table.getText(),1,r));

		    while (result.next()) {
		    	
				        vars.add(new VariableDetail(result.getString(4),result.getString(8),result.getString(7)));

		System.out.println(result.getString(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4)+" "+result.getString(5)+" "+result.getString(6)+" "+result.getString(7)+" "+result.getString(8)+" "+result.getString(9)+" "+result.getString(10)+" "+result.getString(11)+" "+result.getString(12));
		    }      
		     
		    connection.close();
		//vars.add(new VariableDetail( "",type1,"",d,i));
			
		System.out.println("jajsssa");
		    // Add observable list data to the table
		    table.setItems( vars);
	}
	public void getTable(Stage stage) {
		// TODO Auto-generated method stub
		stage.close();
	}
	public String getFKeyData(String tableName, int i,String r) throws SQLException {
		 String databaseURL = "jdbc:postgresql://localhost:5432/"+r;
		    String username = "postgres";
		    String password = "linux12";
		    Connection connection = DriverManager.getConnection(databaseURL, username, password);
	    DatabaseMetaData dm = connection.getMetaData();
	    String sql="select kcu.table_schema || '.' ||kcu.table_name as foreign_table,\n" + 
	    		"       '>-' as rel,\n" + 
	    		"       rel_tco.table_schema || '.' || rel_tco.table_name as primary_table,\n" + 
	    		"       string_agg(kcu.column_name, ', ') as fk_columns,\n" + 
	    		"       kcu.constraint_name\n" + 
	    		"from information_schema.table_constraints tco\n" + 
	    		"join information_schema.key_column_usage kcu\n" + 
	    		"          on tco.constraint_schema = kcu.constraint_schema\n" + 
	    		"          and tco.constraint_name = kcu.constraint_name\n" + 
	    		"join information_schema.referential_constraints rco\n" + 
	    		"          on tco.constraint_schema = rco.constraint_schema\n" + 
	    		"          and tco.constraint_name = rco.constraint_name\n" + 
	    		"join information_schema.table_constraints rel_tco\n" + 
	    		"          on rco.unique_constraint_schema = rel_tco.constraint_schema\n" + 
	    		"          and rco.unique_constraint_name = rel_tco.constraint_name\n" + 
	    		"where tco.constraint_type = 'FOREIGN KEY'\n" + 
	    		"group by kcu.table_schema,\n" + 
	    		"         kcu.table_name,\n" + 
	    		"         rel_tco.table_name,\n" + 
	    		"         rel_tco.table_schema,\n" + 
	    		"         kcu.constraint_name\n" + 
	    		"order by kcu.table_schema,\n" + 
	    		"         kcu.table_name;";
	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery(sql);
	    String fkTableData="";
	    while (result.next()) {
			System.out.println(result.getString(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4)+" "+result.getString(5));
	    }
	    return fkTableData;
	}
	
	
	
}
