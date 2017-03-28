package gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

public class CustomTabPane extends AnchorPane{
	
	public CustomTabPane(){
		setPrefSize(600, 600);
		
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		CustomTableView tableView = new CustomTableView();
		
		Tab tab1 = new Tab();
		tab1.setText("Students");
		tab1.setContent(tableView);
		tabPane.getTabs().add(tab1);
		
		Tab tab2 = new Tab();
		tab2.setText("Log");
		tab2.setContent(new TextArea());
		tabPane.getTabs().add(tab2);
		
		Tab tab3 = new Tab();
		tab3.setText("Histogram");
		tab3.setContent(new TextArea());
		tabPane.getTabs().add(tab3);
		
		setTopAnchor(tabPane, 0.0);
	    setLeftAnchor(tabPane, 0.0);
	    setRightAnchor(tabPane, 0.0);
	    setBottomAnchor(tabPane, 0.0);
		
		getChildren().add(tabPane);
		
	}

}
