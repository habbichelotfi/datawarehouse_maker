package sample;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;

import java.io.IOException;
import java.sql.Date;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EntrepotController {
@FXML 
private TableView<BDD> bdd;
@FXML
private Button back;
@FXML
private Button btn_sup;
@FXML
private TextField nom_bdd;
@FXML
private Button add_new_bdd;
@FXML
private Button search;
@FXML
private TextField filter;
@FXML
private TableColumn<BDD, String> firstNameColumn;
@FXML
private TableColumn secondNameColumn;

@FXML
private TableColumn<BDD, String> lastNameColumn;
@FXML
private TextField filtr;
static Stage mainApp;
private Stage j;
final ObservableList<BDD> personData = FXCollections.observableArrayList();

public EntrepotController() {
	super();
}
@FXML
public void Filter() {
	
		
}
@FXML
void Goback() throws IOException {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Vous les vous quitter?");
	//alert.setHeaderText("Look, a Confirmation Dialog");
	alert.setContentText("Etes vous sur?");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
	    Parent parent = fxmlLoader.load();
	    Controller dialogController = fxmlLoader.getController();
	    // dialogController.setAppMainObservableList(tvObservableList);
	    //dialogController.setMainApp(mainApp);
	    Scene scene = new Scene(parent,  1087, 560);
	    Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setScene(scene);
	    System.out.println(this.j.getTitle());
	    dialogController.closeEntrepot(j);

	    stage.showAndWait();
//
	    // ... user chose OK
	} else {
		alert.close();
	    // ... user chose CANCEL or closed the dialog
	}
	  
}
@FXML
public void ToVariable() throws Exception {
	  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tables.fxml"));
	    Parent parent = fxmlLoader.load();
	    TablesController dialogController = fxmlLoader.getController();
	   // dialogController.setAppMainObservableList(tvObservableList);
	    //dialogController.setMainApp(mainApp);
	    Scene scene = new Scene(parent,  870, 560);
	    Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setScene(scene);
	    stage.showAndWait();
	  //  dialogController.getEntrepot(stage);
	    System.out.println("jaja");

	
}

@FXML
void supprimer() throws SQLException {
	
	for(int i=0;i<personData.size();i++) {
	CheckBox	t=(CheckBox) bdd.getColumns().get(0).getCellData(i);
if(t.isSelected()) {
	Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "linux12");
	Statement statement = c.createStatement();
	statement.executeUpdate("DROP DATABASE "+personData.get(i).getBdd_name().getText());
personData.remove(i);
}
	}
	bdd.setItems(personData);

	
}
@FXML
void CreerBDD() throws SQLException {
	Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "linux12");
	Statement statement = c.createStatement();
String sql="CREATE DATABASE ";
	if(nom_bdd.getText().isEmpty()) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error ");
		alert.setContentText("Veuillez remplire ce champs");

		alert.showAndWait();
		
	}else {
		sql=sql+nom_bdd.getText();
		statement.executeUpdate(sql);
		personData.add(new BDD(nom_bdd.getText(),""));
		bdd.setItems(personData);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Fait ");
		alert.setContentText("Base de donne creer avec succees!");

		alert.showAndWait();
		
	}
	c.close();
	
	
}
@FXML
 void initialize() throws SQLException {
	// Initialize the person table with the two columns.
 
	firstNameColumn.setCellValueFactory(new PropertyValueFactory<BDD,String>("check"));
	lastNameColumn.setCellValueFactory(new PropertyValueFactory<BDD,String>("date"));
	String databaseURL = "jdbc:postgresql://localhost:5432/";
    String username = "postgres";
    String password = "linux12";
     
    Connection connection = DriverManager.getConnection(databaseURL, username, password);
     
    String sql = "SELECT * FROM pg_database;";
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(sql);
     
    while (result.next()) {
        String aDBName = result.getString(1);
        personData.add(new BDD(result.getString(1),"Pas disponible"));
        
    }      
     
    connection.close();
	personData.add(new BDD( "Muster","11"));
	secondNameColumn.setCellValueFactory(new PropertyValueFactory<BDD,String>("bdd_name"));
	secondNameColumn.setCellFactory(new HyperlinkCell());
	System.out.println("jajaTTT");
	    // Add observable list data to the table
	    bdd.setItems( personData);


}

/**
 * Is called by the main application to give a reference back to itself.
 * 
 * @param m
 */
public void setMainApp(Stage m) {
    this.j = m;
    System.out.println("a"+m.getTitle());
    this.j.close();
}
static class HyperlinkCell implements  Callback<TableColumn<BDD, Hyperlink>, TableCell<BDD, Hyperlink>> {
	public void ToVariable(String t) throws Exception {
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Tables.fxml"));
		    Parent parent = fxmlLoader.load();
		    TablesController dialogController = fxmlLoader.getController();
		   // dialogController.setAppMainObservableList(tvObservableList);
		    dialogController.transferMessage(t,mainApp);
		    //dialogController.setMainApp(mainApp);
		    Scene scene = new Scene(parent,  870, 560);
		    Stage stage = new Stage();
		    stage.initModality(Modality.APPLICATION_MODAL);

		    stage.setScene(scene);
		    stage.showAndWait();


		
	}
    private static  HostServices hostServices ;
    

    public  HostServices getHostServices() {
        return hostServices ;
    }

    public  void setHostServices(HostServices hostServices) {
        HyperlinkCell.hostServices = hostServices ;
    }

    @Override
    public TableCell<BDD, Hyperlink> call(TableColumn<BDD, Hyperlink> arg) {
        TableCell<BDD, Hyperlink> cell = new TableCell<BDD, Hyperlink>() {

            private Hyperlink hyperlink = new Hyperlink();

            {
            	
            	
            }

            @Override
            protected void updateItem(Hyperlink url, boolean empty) {
                super.updateItem(url, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                	hyperlink=url;
                	hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                	    @Override
                	    public void handle(ActionEvent e) {
                	    	
                	       String t=hyperlink.getText();
                	        try {
								ToVariable(t);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                	    }
                	});

                    setGraphic(hyperlink);
                }
            };
            
            };
          
        return cell;
    
 }

	

}
public void getEntrepot(Stage s) {
	this.mainApp=s;
	s.close();
}
public void getTable(Stage stage) {
	// TODO Auto-generated method stub
	stage.close();
}
}
 