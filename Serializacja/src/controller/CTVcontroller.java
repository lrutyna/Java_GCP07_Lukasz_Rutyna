package controller;

import java.net.URL;
import java.util.ResourceBundle;

import example.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CTVcontroller implements Initializable{

	@FXML
	private TableView<Student> customTableView;
	@FXML
	private TableColumn<Student,Double> markColumn;
	@FXML
	private TableColumn<Student,String> firstNameColumn;
	@FXML
	private TableColumn<Student,String> lastNameColumn;
	@FXML
	private TableColumn<Student,Integer> ageColumn;
	
	
	
	public void setTable(TableView<Student> table) {
		this.customTableView = table;
	}

	public TableView<Student> getTable() {
		return customTableView;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		markColumn.setCellValueFactory(
                new PropertyValueFactory<Student, Double>("mark")
        );
		
		firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName")
        );
		
		lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName")
        );
		
		ageColumn.setCellValueFactory(
                new PropertyValueFactory<Student, Integer>("age")
        );
		
	}

	
}
