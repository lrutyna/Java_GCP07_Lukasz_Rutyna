package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CMBcontroller implements Initializable{

	private Stage stage;
	private Alert alert;
	
	@FXML
	private MenuBar menuBar;
	
	@FXML
	public void toCloseStage() throws IOException{
		stage = (Stage) menuBar.getParent().getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void showAlert(){
		alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("About");
    	alert.setHeaderText("Example program informations");
    	alert.setContentText("Author: £ukasz Rutyna");
    	alert.showAndWait().ifPresent(rs -> {
    	    if (rs == ButtonType.OK) {
    	    }
    	});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
