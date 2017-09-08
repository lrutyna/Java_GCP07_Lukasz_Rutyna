package gui;

import example.Student;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CustomTableView extends AnchorPane{
	
	private TableView<Student> table;
	
	public CustomTableView(){
	    

	    table = new TableView<Student>();
	    table.setEditable(true);   
	    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	    TableColumn<Student,Double> markCol = new TableColumn<>("Mark");
	    markCol.setMinWidth(150);
	    markCol.setCellValueFactory(new PropertyValueFactory<Student, Double>("mark"));
	    
	    TableColumn<Student,String> firstNameCol = new TableColumn<>("First Name");
	    firstNameCol.setMinWidth(150);
	    firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
	    
	    TableColumn<Student,String> lastNameCol = new TableColumn<>("Last Name");
	    lastNameCol.setMinWidth(150);
	    lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
	    
	    TableColumn<Student,Integer> ageCol = new TableColumn<>("Age");
	    ageCol.setMinWidth(150);
	    ageCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("age"));       
	    
	    table.getColumns().addAll(markCol, firstNameCol, lastNameCol, ageCol);
	    
	    setTopAnchor(table, 0.0);
	    setLeftAnchor(table, 0.0);
	    setRightAnchor(table, 0.0);
	    setBottomAnchor(table, 0.0);
	    
	    
	    getChildren().add(table);
	    
	}

	public TableView<Student> getTable() {
		return table;
	}

	public void setTable(TableView<Student> table) {
		this.table = table;
	}
	
	
	
}
