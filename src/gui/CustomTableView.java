package gui;

import example.Student;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class CustomTableView extends AnchorPane{
	
	public CustomTableView(){
	    
	    TableView<Student> table = new TableView<Student>();
	    table.setEditable(true);

	    TableColumn<Student,Double> markCol = new TableColumn<>("Mark");
	    markCol.setMinWidth(150);
	    
	    TableColumn<Student,String> firstNameCol = new TableColumn<>("First Name");
	    firstNameCol.setMinWidth(150);
	    
	    TableColumn<Student,String> lastNameCol = new TableColumn<>("Last Name");
	    lastNameCol.setMinWidth(150);
	    
	    TableColumn<Student,Integer> ageCol = new TableColumn<>("Age");
	    ageCol.setMinWidth(150);
	           
	    //table.setItems(arg0);
	    table.getColumns().addAll(markCol, firstNameCol, lastNameCol, ageCol);
	    
	    setTopAnchor(table, 0.0);
	    setLeftAnchor(table, 0.0);
	    setRightAnchor(table, 0.0);
	    setBottomAnchor(table, 0.0);
	    
	    
	    getChildren().add(table);
	    
	}
}
