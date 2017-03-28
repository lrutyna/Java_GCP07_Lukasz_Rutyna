package example;

import gui.CustomMenuBar;
import gui.CustomTabPane;
import example.Crawler.OrderMode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Program extends Application
{
	public static void main( String[] args )
	{	
		launch(args);
		Crawler crawler = new Crawler();
		
		// dodawanie eventow
		crawler.addAddedStudentEvent();
		crawler.addRemovedStudentEvevnt();
		
		// ustawienie scizki do pliku lub urla
		crawler.setFilePath("students.txt");
		crawler.setUrlAdress("http://student.agh.edu.pl/~lrutyna/students.txt");
		
		// ustawieie porzadku sortowania
		crawler.setMode(OrderMode.AGE);
		
		Thread t = new Thread(crawler);
		t.start();
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		CustomMenuBar menuBar = new CustomMenuBar(stage);
		CustomTabPane pane = new CustomTabPane();
		
		VBox vbox = new VBox(menuBar,pane);
		
		Scene scene = new Scene(vbox,600,600);
		stage.setScene(scene);
		
		
		stage.show();
		
	}
}
