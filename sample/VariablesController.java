package sample;

import java.io.IOException;
import java.sql.Connection;
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
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VariablesController {
	
@FXML
private TextField nom_table;
@FXML
private TextField add_column;
@FXML
private Button execute;
@FXML
private Button enrengistrer;

@FXML
private TableView<Variables> table;
@FXML
private TableColumn<Variables,String> reference;
@FXML
private TableColumn<Variables,String> nom;
@FXML
private TableColumn<Variables,String> type;
@FXML
private TableColumn<Variables,String> taille;

@FXML
private TableColumn<Variables,String> ifnull;
@FXML
private TableColumn<Variables,String> index;
@FXML
private TableColumn<Variables,String> ia;
private Main mainApp;
private String  nom_bdd="";
private final ObservableList<Variables> vars = FXCollections.observableArrayList();
@FXML
private Button back;
private String t;
public VariablesController() {
	

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
@FXML
public void  Save() throws SQLException {
	String sql="CREATE TABLE IF NOT EXISTS "+nom_table.getText()+" ( ";
	boolean u=true;
	for(int i=0;i<vars.size();i++) {
		TextField t=(TextField) table.getColumns().get(0).getCellData(i);
		ChoiceBox<String> b=(ChoiceBox<String>) table.getColumns().get(1).getCellData(i);
			TextField tai=(TextField) table.getColumns().get(2).getCellData(i);
						CheckBox c=(CheckBox) table.getColumns().get(3).getCellData(i);
						ChoiceBox<String> h=(ChoiceBox<String>) table.getColumns().get(4).getCellData(i);
								CheckBox ia_=(CheckBox) table.getColumns().get(5).getCellData(i);
								ChoiceBox<String> refe=(ChoiceBox<String>) table.getColumns().get(6).getCellData(i);

			if(t.getText().isEmpty() || b.getSelectionModel().isEmpty()) {
		    	Alert alert = new Alert(AlertType.ERROR);
u=false;
				alert.setTitle("Erreur ");
				alert.setContentText("Veuillez remplire les champs manquants");

				alert.showAndWait();
			}else {
				
				sql=sql+t.getText()+" "+b.getSelectionModel().getSelectedItem();
				if(b.getSelectionModel().getSelectedItem().equals("VARCHAR")) {
					if(tai.getText().isEmpty()) {
					 	Alert alert = new Alert(AlertType.ERROR);
					 	u=false;
					 					alert.setTitle("Erreur ");
					 					alert.setContentText("Veuillez remplire le champs  Taille");

					 					alert.showAndWait();
					}else {
						sql=sql+" ("+tai.getText()+" ) ";

					}
			}
				if(h.getSelectionModel().getSelectedItem().equals("PRIMARY")){
					sql=sql+" "+h.getSelectionModel().getSelectedItem()+" KEY ";
					if(c.isPressed()) {
						sql=sql+" NULL, ";
						
					}else {
						sql=sql+" NOT NULL, ";

					}
				}else {
					if(h.getSelectionModel().getSelectedItem().equals("FOREIGN")) {
						
						if(c.isPressed()) {
							sql=sql+" NULL, ";
							
						}else {
							sql=sql+" NOT NULL, ";

						}
						sql=sql+" CONSTRAINT fk_"+t.getText().substring(0,t.getText().indexOf("_"))+" "+h.getSelectionModel().getSelectedItem()+" KEY("+t.getText()+") ";
	System.out.println(sql);
						if(refe.getSelectionModel().isEmpty()) {
							Alert alert = new Alert(AlertType.ERROR);
						 	u=false;
						 					alert.setTitle("Erreur ");
						 					alert.setContentText("Veuillez remplire le champs  Reference");

						 					alert.showAndWait();
						}else {
						
							sql=sql+"REFERENCES "+refe.getSelectionModel().getSelectedItem().toString()+",";	
							System.out.println(sql);

						}
					}else {
						sql=sql+" "+h.getSelectionModel().getSelectedItem();
						if(c.isPressed()) {
							sql=sql+" NULL, ";
							
						}else {
							sql=sql+" NOT NULL, ";

						}
					}
					

				}	
				
			
				
				
				
			}

		
	}
	sql=sql+" Type VARCHAR ( 30 ) DEFAULT '"+t+"'";
	if(u) {
		String databaseURL = "jdbc:postgresql://localhost:5432/"+nom_bdd;
	    String username = "postgres";
	    String password = "linux12";
	    Connection connection = DriverManager.getConnection(databaseURL, username, password);
	    System.out.println("connected");
	    sql=sql+" );";
	    Statement statement = connection.createStatement();
	     statement.executeUpdate(sql);
	     Alert alert = new Alert(AlertType.INFORMATION);
	     alert.setTitle("Information ");
	     alert.setHeaderText(null);
	     alert.setContentText("La table a ete creer avec succes");

	     alert.showAndWait();
	    connection.close();
	}
		 
		    
	
}
@FXML
public void AddColonne(){
	System.out.println("jajaaaa");

	List<String> type1=new ArrayList<String>();

	vars.add(new Variables( "",type1,"",type1,type1));
    table.setItems( vars);
	
}
public void setMainApp(Main mainApp) {
    this.mainApp = mainApp;

}

public void transmettre_info(String t ,String type_,int nbr) throws SQLException {
	this.t=type_;
	nom_table.setText(t.substring(0,t.indexOf(",")));
	nom_bdd=t.substring(t.indexOf(",")+1);
	// TODO Auto-generated method stub
	nom.setCellValueFactory(new PropertyValueFactory<Variables,String>("nom"));
	type.setCellValueFactory(new PropertyValueFactory<Variables,String>("type"));
	taille.setCellValueFactory(new PropertyValueFactory<Variables,String>("taille"));
	ifnull.setCellValueFactory(new PropertyValueFactory<Variables,String>("ifnull"));
	index.setCellValueFactory(new PropertyValueFactory<Variables,String>("index"));
	ia.setCellValueFactory(new PropertyValueFactory<Variables,String>("ia"));
	reference.setCellValueFactory(new PropertyValueFactory<Variables,String>("reference"));

	List<String> type1=new ArrayList<String>();
	type1.add("INT");	type1.add("VARCHAR");
	type1.add("TEXT");
	type1.add("FLOAT");
	type1.add("DATE");
	List<String> d=new ArrayList<String>();
	d.add("NULL");	d.add("CURRENT_TIMESTAMP");
	List<String> i=new ArrayList<String>();
	i.add("PRIMARY");	i.add("UNIQUE");
	i.add("FOREIGN");
	i.add("INDEX");	i.add("FULLTEXT");
for(int k=0;k<nbr;k++) {
	vars.add(new Variables( "",type1,"",i,getIDs()));

}
// Add observable list data to the table
	    table.setItems( vars);
}
List<String> getIDs() throws SQLException{
	String databaseURL = "jdbc:postgresql://localhost:5432/"+nom_bdd;
    String username = "postgres";
    String password = "linux12";
    Connection connection = DriverManager.getConnection(databaseURL, username, password);
    System.out.println("connected");
    Statement statement = connection.createStatement();
    List<String> l1=getTables(nom_bdd);
    List<String> l=new ArrayList<String>();

    for(String k:l1) {
    	 String sql="SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+k+"';";
    	    ResultSet result = statement.executeQuery(sql);
    	    
result.next();
    		l.add(k+" ("+result.getString(1)+")");
    	
    }
   
    connection.close();
    return l;
	
}
static ArrayList<String> getTables(String bdd) throws SQLException{
	String databaseURL = "jdbc:postgresql://localhost:5432/"+bdd;
    String username = "postgres";
    String password = "linux12";
    Connection connection = DriverManager.getConnection(databaseURL, username, password);
    System.out.println("connected");

    String sql = "SELECT table_name\n" + 
    		"FROM information_schema.tables\n" + 
    		"WHERE table_schema = 'public'\n" + 
    		"ORDER BY table_name;";
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);
    ArrayList<String> l=new ArrayList<String>();
    while (result.next()) {
    	l.add(result.getString(1));
    }
    connection.close();

    return l;
}

}

