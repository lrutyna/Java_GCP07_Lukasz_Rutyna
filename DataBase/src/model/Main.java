package model;

import org.hibernate.Session;
import controller.LoggingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String args[]){
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loggingFXML.fxml"));
		Parent root = loader.load();
		
		LoggingController controller = loader.getController();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
}
