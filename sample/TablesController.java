package sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.mindfusion.diagramming.Align;
import com.mindfusion.diagramming.AutoResize;
import com.mindfusion.diagramming.Diagram;
import com.mindfusion.diagramming.DiagramView;
import com.mindfusion.diagramming.SimpleShape;
import com.mindfusion.diagramming.SolidBrush;
import com.mindfusion.diagramming.TableNode;
import com.mindfusion.diagramming.TextFormat;

import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.EntrepotController.HyperlinkCell;

public class TablesController {
    private Task copyWorker;
@FXML
private Button model;
@FXML
private Label c;
@FXML
private ProgressBar p;
@FXML
private TextField nom_table;
@FXML
private TextField nbr_colonne;
@FXML
private TextField filtre;
@FXML
private ChoiceBox<String> type;
@FXML
private Button creer;
@FXML
private Button back;
@FXML
private Button search;
@FXML
private Button reload;
@FXML
private Button supprimer;
@FXML
private TableView<T> table;

private JScrollPane _scrollPane;
@FXML
private TableColumn<T,String> check;
@FXML
private TableColumn tables;
@FXML
private TableColumn<T,String> types;
@FXML
private TableColumn<T,String> create_the;	
@FXML
private TableColumn<T,String> last;
private Diagram diagram;
private DiagramView diagramView;

 static String t="";
private final ObservableList<T> vars = FXCollections.observableArrayList();
@FXML
void Goback() throws IOException {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Vous les vous quitter?");
	//alert.setHeaderText("Look, a Confirmation Dialog");
	alert.setContentText("Etes vous sur?");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entrepot_.fxml"));
	    Parent parent = fxmlLoader.load();
	    EntrepotController dialogController = fxmlLoader.getController();
	    // dialogController.setAppMainObservableList(tvObservableList);
	    //dialogController.setMainApp(mainApp);
	    Scene scene = new Scene(parent,  727, 533);
	    Stage stage = new Stage();
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.setScene(scene);
	    dialogController.getTable(stage);

	    stage.showAndWait();
	    System.out.println("jaja");

	    // ... user chose OK
	} else {
		alert.close();
	    // ... user chose CANCEL or closed the dialog
	}
	
}

static ArrayList<DBColumn> getColumInfo(String table) throws SQLException {
	  String databaseURL = "jdbc:postgresql://localhost:5432/new";
	    String username = "postgres";
	    String password = "linux12";
	    Connection connection = DriverManager.getConnection(databaseURL, username, password);
	    System.out.println("connected");
	    
	    String sql="SELECT * FROM information_schema.columns WHERE TABLE_NAME ='"+table.toLowerCase()+"';";
	    System.out.print(sql);
	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery(sql);
	    ArrayList<DBColumn> d = new ArrayList<DBColumn>();
	    while (result.next()) {
	        String aDBName = result.getString(1);
	        d.add(new DBColumn(result.getString(4),result.getString(8),result.getString(10)));
	System.out.println(result.getString(1));
	    }      
	     
	    connection.close();
		return d;
}

@FXML
public void SupprimerLigne() throws SQLException{
	for(int i=0;i<vars.size();i++) {
	CheckBox	u=(CheckBox) table.getColumns().get(0).getCellData(i);
if(u.isSelected()) {
	Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+t, "postgres", "linux12");
	Statement statement = c.createStatement();
	statement.executeUpdate("DROP TABLE "+vars.get(i).getNom_table().getText());
	vars.remove(i);
}
	}
	table.setItems(vars);

	
}
public TablesController() {

}
public TablesController(String t) {
	this.t=t;
}

@FXML
void DisplayModel() {
	MainWindow window = null;
    try {
        window = new MainWindow();
        window.setVisible(true);
    }
    catch (Exception exp) {
        exp.printStackTrace();
    }
	


}

@FXML
public void ToVar() throws Exception {
	
	 FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("variables.fxml"));
	    Parent parent = fxmlLoader.load();
	    VariablesController dialogController = fxmlLoader.<VariablesController>getController();
	    if(nbr_colonne.getText().isEmpty() || type.getSelectionModel().isEmpty() || nom_table.getText().isEmpty()) {
	    	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error ");
			alert.setContentText("Veuillez remplire ce champs");

			alert.showAndWait();
	    }else {
	    	dialogController.transmettre_info(nom_table.getText()+","+t, type.getSelectionModel().getSelectedItem(), Integer.parseInt(nbr_colonne.getText()));
	   
	    	  Scene scene = new Scene(parent,  877, 570);
	  	    Stage stage = new Stage();
	  	    stage.initModality(Modality.WINDOW_MODAL);
	  	    stage.setScene(scene);
	  	    stage.showAndWait();
	    }
	   // dialogController.setAppMainObservableList(tvObservableList);
	    //dialogController.setMainApp(mainApp);
	  
}

public void transferMessage(String message, Stage mainApp) throws SQLException, InterruptedException {
    //Display the message
    t=message;
    String databaseURL = "jdbc:postgresql://localhost:5432/"+t;
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
    while (result.next()) {

vars.add(new T(result.getString(1),getType(result.getString(1)),"Pas defini","Pas defini"));
System.out.println(result.getString(1));
    }      
     
    connection.close();
	check.setCellValueFactory(new PropertyValueFactory<T,String>("check"));
	tables.setCellValueFactory(new PropertyValueFactory<T,String>("nom_table"));
	tables.setCellFactory(new HyperlinkCell());
	types.setCellValueFactory(new PropertyValueFactory<T,String>("type"));
	create_the.setCellValueFactory(new PropertyValueFactory<T,String>("create_the"));
	last.setCellValueFactory(new PropertyValueFactory<T,String>("last_modif"));
	type.getItems().add("Table des faits");
	type.getItems().add("Dimension");
    // Add observable list data to the table
    table.setItems( vars);
    //mainApp.close();

}

@FXML
void refresh() {
	table.setItems(vars);
	System.out.println("aadaad"+t);

}
public String getT() {
	return t;
}
public void setT(String t) {
	this.t = t;
}
static class HyperlinkCell implements  Callback<TableColumn<BDD, Hyperlink>, TableCell<BDD, Hyperlink>> {
	public void ToVariable(String t,String r) throws Exception {
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDetails.fxml"));
		    Parent parent = fxmlLoader.load();
		    VariableDetailController dialogController = fxmlLoader.getController();
		   // dialogController.setAppMainObservableList(tvObservableList);
		    dialogController.transferMessage(t,r);
		    //dialogController.setMainApp(mainApp);
		    Scene scene = new Scene(parent,  575, 460);
		    Stage stage = new Stage();
		    stage.initModality(Modality.APPLICATION_MODAL);
		    stage.setScene(scene);
			  

		    stage.showAndWait();
		    dialogController.getTable(stage);
		    System.out.println("jaja");

		
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
                	    	
                	       String r=hyperlink.getText();
                	        try {
								ToVariable(r,t);
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
public String getType(String table) throws SQLException {
	 String databaseURL = "jdbc:postgresql://localhost:5432/"+t;
	    String username = "postgres";
	    String password = "linux12";
	    Connection connection = DriverManager.getConnection(databaseURL, username, password);
	    System.out.println("connected");
	String sql2="SELECT * FROM information_schema.columns WHERE TABLE_NAME ='"+table+"'";        
	Statement statement1 = connection.createStatement();
	String h="";
	ResultSet resu=statement1.executeQuery(sql2);
	while(resu.next()) {
		if(resu.isLast()) {
			System.out.print(resu.getString(6));
			 h= resu.getString(6).substring(0,resu.getString(6).indexOf(":"));
		}
			
	}
	System.out.print(h);
	return h;
}
public void getEntrepot(Stage stage) {
	// TODO Auto-generated method stub
	System.out.println("bhalo");
	stage.close();
}

public void getTable(Stage stage) {
	// TODO Auto-generated method stub
	stage.close();
}


}
