package sample;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class Controller {
	
	@FXML
	private Button quiter;
@FXML
private Button importation;
@FXML
private Button create_bdd;
@FXML
private Button create_entrepot;
@FXML
private ImageView quit;
@FXML
private VBox liste_files;

private Main mainApp;
private Stage stage;
static EventHandler<Event> btnSolHandler;
private Desktop desktop;
private Stage m;
public Controller(Stage stage) {
this.stage=stage;	
}
public Controller() {
}
@FXML
public void importation(Event e) {
	 final FileChooser fileChooser = new FileChooser();	
	 
	   File file = fileChooser.showOpenDialog(stage);
       if (file != null) {
          // openFile(file);
           List<File> files = Arrays.asList(file);
           Pane newPane = new Pane();
newPane.getChildren().add(new Label("aa"));
int t=file.toString().lastIndexOf("/");
Label label=new Label(file.toString().substring(t+1));
label.setTextFill(Color.web("#0076a3"));
label.setStyle("-fx-font-size:22;-fx-translate-x:40;-fx-translate-y:25");
liste_files.getChildren().add(label);
       }
}
@FXML
public void Quit(Event e){
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Vous les vous quitter?");
	//alert.setHeaderText("Look, a Confirmation Dialog");
	alert.setContentText("Etes vous sur?");

	Optional<ButtonType> result = alert.showAndWait();
	if (result.get() == ButtonType.OK){
	    System.exit(0);

	    // ... user chose OK
	} else {
		alert.close();
	    // ... user chose CANCEL or closed the dialog
	}

    System.out.println("Button clicked");
}
@FXML
void onOpenDialog(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dialog.fxml"));
    Parent parent = fxmlLoader.load();
    DialogController dialogController = fxmlLoader.getController();
   // dialogController.setAppMainObservableList(tvObservableList);

    Scene scene = new Scene(parent, 400, 200);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    
    stage.showAndWait();
}


@FXML
void onOpenEntrepotCreator(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entrepot_.fxml"));
    Parent parent = fxmlLoader.load();
    EntrepotController dialogController = fxmlLoader.getController();
   // dialogController.setAppMainObservableList(tvObservableList);
    System.out.println(this.m.getTitle());

dialogController.setMainApp(this.m);
    Scene scene = new Scene(parent, 727, 478);
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.setScene(scene);
    dialogController.getEntrepot(stage);
    stage.showAndWait();
}

private void printLog(TextArea textArea, List<File> files) {
    if (files == null || files.isEmpty()) {
        return;
    }
    for (File file : files) {
        textArea.appendText(file.getAbsolutePath() + "\n");
    }
}

private void openFile(File file) {
    try {
        this.desktop.open(file);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void getEntrepot(Stage m) {
	m.close();
    System.out.println(m.getTitle());

}
public void setMainApp(Stage mainApp) {
    this.m = mainApp;
    System.out.println(m.getTitle());
    

    // Add observable list data to the table

}
public void closeEntrepot(Stage j) {
	// TODO Auto-generated method stub
    System.out.println("saas"+j.getTitle());

	j.close();
}

}
