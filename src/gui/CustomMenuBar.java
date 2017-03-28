package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class CustomMenuBar extends MenuBar{

	
	private final Menu program;
	private final Menu about;
	
	public CustomMenuBar(Stage stage){
		
		 program = new Menu("Program");
		 about = new Menu("About");
		 
		 MenuItem menuItem = new MenuItem("Close Ctrl + C");
		 
		 menuItem.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	stage.close();
			    }
			});
		 
		 
		 MenuItem menuItem2 = new MenuItem("Author");
		 
		 menuItem2.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	Alert alert = new Alert(AlertType.INFORMATION);
			    	alert.setTitle("About");
			    	alert.setHeaderText("Example program informations");
			    	alert.setContentText("Author: £ukasz Rutyna");
			    	alert.showAndWait().ifPresent(rs -> {
			    	    if (rs == ButtonType.OK) {
			    	    }
			    	});
			    }
			});
		 
		 
		 program.getItems().add(menuItem);
		 about.getItems().add(menuItem2);
		 getMenus().addAll(program, about);
	}
}
