package model;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import controller.FXMLcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage stage) throws Exception {

		  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPanel.fxml"));
			
			Parent root = loader.load();
			
			//FXMLcontroller controller = loader.getController();
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			
			stage.show();
	}
	
	

}
