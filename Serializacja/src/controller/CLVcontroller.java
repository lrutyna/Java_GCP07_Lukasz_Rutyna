package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LogTableRow;



public class CLVcontroller implements Initializable{

	@FXML
	TableView<LogTableRow> customLogView;
	@FXML
	TableColumn<LogTableRow, String> col;
	
	
	public TableView<LogTableRow> getTable() {
		return customLogView;
	}

	public void setTable(TableView<LogTableRow> table) {
		this.customLogView = table;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col.setCellValueFactory(
                new PropertyValueFactory<LogTableRow, String>("log")
        );
		
	}

	
}
