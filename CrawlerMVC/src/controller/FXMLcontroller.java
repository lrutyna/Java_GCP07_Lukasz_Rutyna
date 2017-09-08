package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class FXMLcontroller implements Initializable{
	
	@FXML
	private AnchorPane anchorPane;
	@FXML
	private TabPane customTabPane;
	@FXML
	private CTPcontroller customTabPaneController;
	
	
	public FXMLcontroller(){
		
	}
	
	@FXML
	private void initialize()
	{
		
	}
	
	public CTPcontroller getCTPcontroller(){
		return customTabPaneController;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
