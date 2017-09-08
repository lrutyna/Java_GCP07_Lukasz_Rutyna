package controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;

public class CTPcontroller implements Initializable{
	
	
	@FXML
	private TabPane customTabPane;
	
	@FXML
	private CTVcontroller customTableViewController;
	@FXML
	private CBCcontroller customBarChartController;
	@FXML
	private CLVcontroller customLogViewController;
	
	@FXML
	private void initialize()
	{
		
	}
	
	public CTVcontroller getCTVcontroller(){
		return customTableViewController;
	}
	
	public void setCTVcontroller(CTVcontroller tableView) {
		this.customTableViewController = tableView;
	}
	
	public CBCcontroller getCBCcontroller(){
		return customBarChartController;
	}
	
	public void setCBCcontroller(CBCcontroller chart) {
		this.customBarChartController = chart;
	}
	
	public CLVcontroller getCLVcontroller(){
		return customLogViewController;
	}
	
	public void setCLVcontroller(CLVcontroller logs) {
		this.customLogViewController = logs;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}


}
