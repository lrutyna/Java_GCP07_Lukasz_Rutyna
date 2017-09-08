package client;

import controller.loggingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	private Client client;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		    client = new Client();

		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loggingFXML.fxml"));
			
			Parent root = loader.load();
			
			loggingController controller = loader.getController();
			controller.setClient(client);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			
			stage.show();
	
	}
}
