package model;


import controller.CBCcontroller;
import controller.CLVcontroller;
import controller.CTPcontroller;
import controller.CTVcontroller;
import example.Student;
import example.StudentsParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import loger.Logger;
import loger.SerializedLogger;
import loger.BinaryLogger;
import loger.CompressedLogger;
import loger.Log;
import loger.TextLogger;


public class CrawlerTask extends Task<ObservableList<Student>> {

	private String filePath = "students.txt";

	// lista przekazywana do tabeli ze studentami
	private ObservableList<Student> list;
	// lista przekazywana do tabeli z logami
	private ObservableList<LogTableRow> logList;

	// tabs
	private CTVcontroller tableView;
	private CBCcontroller chart;
	private CLVcontroller logs;

	// zbior oczekwianych wartosci ocen
	private Set<Double> marks = Stream.of(2.0, 3.0, 3.5, 4.0, 4.5, 5.0).collect(Collectors.toCollection(HashSet::new));
	// seria danych do wykresu
	private Series<String, Number> series;

	private boolean flag;
	
	// loggery
	final Logger[] loggers = new Logger[]{
		new TextLogger(),
		new CompressedLogger(),
		new SerializedLogger(),
		new BinaryLogger(),
	};

	// konstruktor 
	public CrawlerTask(CTPcontroller customTabPaneController) {
		this.tableView = customTabPaneController.getCTVcontroller();
		this.chart = customTabPaneController.getCBCcontroller();
		this.logs = customTabPaneController.getCLVcontroller();
	}


	// metoda wykonywana w tle programu
	@Override
	protected ObservableList<Student> call() throws Exception {

		// inicjalizacja listy
		initList();

		while (!flag) {
			if(isCancelled()){
				// close files
				for(Logger l : loggers){
					l.close();
				}
			}
			// szukanie zmian 
			lookForChanges();
			// uspienie watku
			Thread.sleep(4000);
		}
			
		return list;
	}

	private void lookForChanges() {

		// metoda wyszukiwania zmian identyczna jak we wczesniejszym cralwerze
		List<Student> newStudentList = new ArrayList<>(readListFromFile());
		List<Student> addedStudents = new ArrayList<>(newStudentList);
		List<Student> removedStudents = new ArrayList<>(list);
		List<Student> intersectionList = new ArrayList<>(list);

		intersectionList.retainAll(newStudentList);
		addedStudents.removeAll(intersectionList);
		removedStudents.removeAll(intersectionList);

		// otrzymuje dwie listy, usunietych i dodanych studento
		// dodaje nowe elementy do listy
		for (Student s : addedStudents) {
			list.add(s);
		}
		// usuwam stare elementy z listy
		for (Student s : removedStudents) {
			list.remove(s);
		}
		
		// aktualizuje serie danych na podstawie listy "list"
		Platform.runLater(() -> {
			updateSeries();
		});
	}

	private ObservableList<Student> readListFromFile() {
		ObservableList<Student> studentList;
		try {
			studentList = FXCollections.observableList(StudentsParser.parse(new File(filePath)));
			return studentList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	private void initList() {

		// wczytanie listy z pliku
		list = readListFromFile();
		
		// inicjalizacja listy z logami
		logList = FXCollections.observableList(new ArrayList<LogTableRow>());

		// ustawienie listy do tabel ze studentami
		tableView.getTable().setItems(list);
		// logami
		logs.getTable().setItems(logList);

		// inicjalizacja serii danych
		series = new Series<String, Number>();
		// etykieta serii danych
		series.setName("Marks");

		// zapisanie referencji serii danych do wykresu
		Platform.runLater(() -> {
			chart.setSeries(series);
			// inicjalizuje serie danych pustymi danymi dla wystapienia kazdej z ocen ustawiam zero
			initSeries();
		});

		
		// listener nasluchujacy zmaian na liscie ze studentami
		list.addListener(new ListChangeListener<Student>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Student> c) {
				while (c.next()) {
					// dla kazdego dodanego i usunietego studenta odpalane jest metoda ktora potem odpala logi
					for (Student s : c.getRemoved()) {
						removeStudent(s);
					}
					for (Student s : c.getAddedSubList()) {
						addStudent(s);
					}
				}
			}
		});
	}

	// metoda ktora dodaje nowy obiekt do listy z logami, to wystarczy zeby sama sie zupdatowala na widoku
	// a to przez ze to ze w klasie z tabela CustomGuiLoggerView pole "log" jest zbindowane z kolumna i automatycznie sie doadje
	// podobnie jest z lista studentami, w zasadzie identycznie
	private void addStudent(Student s) {
		for (Logger l : loggers) {
			l.log("[NEW]", s);
		}
		
		// gui logger doesn't implement Logger but Log interface
		Log l = (msg, stud) -> {
				logList.add(new LogTableRow(stud, msg));
		};
		l.log("NEW", s);

	}

	private void removeStudent(Student s) {
		for (Logger l : loggers) {
			l.log("[REMOVED]", s);
		}
		// gui logger doesn't implement Logger but Log interface
		Log l = (msg, stud) -> {
			logList.add(new LogTableRow(stud, msg));
		};
		l.log("REMOVED", s);
	}

	// metoda ktora przygotowuje serie danych do wykresu
	private void updateSeries() {

		// wyciagamy streama z listy, odfiltrowujemy obiekty ktorych oceny nie sa w zbiorze spodziewanych ocen "marks"
		// potem grupujemy dane po liczbie ocen, 
		Map<Double, Long> count = list.stream().filter(mark -> marks.contains(mark.getMark()))
				.collect(Collectors.groupingBy(Student::getMark, Collectors.counting()));

		// testowo wyswietlam mape z ocena i liczba wystapien tej oceny
		System.out.println(count);

		// jade petla po mapie i dla kazdego elementu mapy dodaje go do serii danych
		count.forEach((k, v) -> {
			series.getData().add(new XYChart.Data(k.toString(), v));
		});

	}
	
	private void initSeries(){
		series.getData().add(new XYChart.Data("2.0", 0));
		series.getData().add(new XYChart.Data("3.0", 0));
		series.getData().add(new XYChart.Data("3.5", 0));
		series.getData().add(new XYChart.Data("4.0", 0));
		series.getData().add(new XYChart.Data("4.5", 0));
		series.getData().add(new XYChart.Data("5.0", 0));
	}
	
	// close files
	public void close(){
		for(Logger l : loggers){
			l.close();
		}
	}
}



