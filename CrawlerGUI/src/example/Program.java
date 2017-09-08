package example;

import gui.CustomMenuBar;
import gui.CustomTabPane;
import example.Crawler.OrderMode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tasks.CrawlerTask;

public class Program extends Application
{
	
	public static void main( String[] args )
	{	
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Crawler");
		CustomMenuBar menuBar = new CustomMenuBar(stage);
		CustomTabPane pane = new CustomTabPane();
		
		VBox vbox = new VBox(menuBar,pane);
		
		Scene scene = new Scene(vbox,600,600);
		stage.setScene(scene);
			
		stage.show();
		
		/* crawler task */
		CrawlerTask task = new CrawlerTask(pane);
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
	

}
