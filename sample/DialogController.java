package sample;

import java.io.File;
import java.util.List;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class DialogController {
@FXML
private Button creer;
@FXML
private TextField nom;
@FXML
private ProgressIndicator progressindicator;
@FXML
private Label files;
private CopyTask copyTask;

@FXML
public void btnCreer(Event e){
if(nom.getText().isEmpty()) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Erreur");
	alert.setHeaderText("Le champ Nom est vide!");
	alert.setContentText("Veuillez reessayer");

	alert.showAndWait();
}else {
	
            progressindicator.setProgress(0);
            

            // Create a Task.
            copyTask = new CopyTask();

            // Unbind progress property

            // Bind progress property

            // Hủy bỏ kết nối thuộc tính progress
            progressindicator.progressProperty().unbind();

            // Bind progress property.
            progressindicator.progressProperty().bind(copyTask.progressProperty());

            // Unbind text property for Label.
            files.textProperty().unbind();

            // Bind the text property of Label
             // with message property of Task
            files.textProperty().bind(copyTask.messageProperty());

            // When completed tasks
            copyTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
                    new EventHandler<WorkerStateEvent>() {

                        @Override
                        public void handle(WorkerStateEvent t) {
                            List<File> copied = copyTask.getValue();
                            files.textProperty().unbind();
                            files.setText("Base de donnees creer avec succees " );
                        }
                    });

            // Start the Task.
            new Thread(copyTask).start();

        
   

    // Cance

}
    System.out.println("Button clicked");
}

}
