package sample;

import java.sql.Date;

import com.mindfusion.diagramming.Diagram;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Main extends Application {
	private Stage primaryStage;
	private ObservableList<BDD> personData = FXCollections.observableArrayList();

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    public Main() {
		// Add some sample data
		personData.add(new BDD( "Muster",""));
		personData.add(new BDD("Ruth", ""));
	
	}
  
	/**
	 * Returns the data as an observable list of Persons. 
	 * @return
	 */
	public ObservableList<BDD> getPersonData() {
		return personData;
	}
	public Stage getPrimary() {
		return primaryStage;
	}
    @Override
    public void start(Stage primaryStage) throws Exception{
		  FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
Parent root=fxmlLoader.load();
        
        primaryStage.initStyle(StageStyle.TRANSPARENT);
primaryStage.setTitle("Dashboard");
        //grab your root here
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        Controller controller =fxmlLoader.getController();
        controller.setMainApp(primaryStage);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("sample/sample.fxml"));
        //move around here
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        //controller.setMainApp(this);
        loader.setController(controller);

        Scene scene = new Scene(root);
        //set transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage=primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
