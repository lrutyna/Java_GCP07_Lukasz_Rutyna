package example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import comparators.AgeComparator;
import comparators.FirstNameComparator;
import comparators.LastNameComparator;
import comparators.MarkComparator;
import events.AddedStudentEvent;
import events.Event;
import events.RemovedStudentEvent;
import exception.CrawlerException;

public class Crawler implements Runnable {

	// sciezka do pliku
	private String urlAdress ="";
	private String filePath = "";
	
	// lista eventow
	private List<Event> eventList;
	
	// struktury danych przechowujace studentow
	private HashSet<Student> oldStudentList;
	private HashSet<Student> newStudentList;
	
	// porzadek sortowania
	private OrderMode mode;
	
	public Crawler(){
		eventList = new ArrayList<>();
		
		// domyslnie sortowanie wg ocen
		mode = OrderMode.MARK;
	}
	
	@Override
	public void run() {	
		
		int iterationCount = 1;
		
		while(true){
			
			// pobranie listy studentow
			downloadStudentList();
			
			// wykrycie zmian
			lookForChanges();
			
			// wyswietlenie informacji o iteracji
			System.out.println("Iteration: " + iterationCount);
			showInfo();
			
			//odczekanie 10 sekund
			try {
				Thread.sleep(10000);
				iterationCount++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// wykrycie zmian
	private void lookForChanges(){
		
		if(oldStudentList == null){
			return;
		}else{
			Set<Student> addedStudents = new HashSet<>(newStudentList);
			Set<Student> removedStudents = new HashSet<>(oldStudentList);
			Set<Student> intersectionSet;
			
			intersectionSet = new HashSet<>(oldStudentList);
			intersectionSet.retainAll(newStudentList);
			
			addedStudents.removeAll(intersectionSet);
			removedStudents.removeAll(intersectionSet);
			
			sendNotifications(addedStudents, removedStudents);
		}
	}
	
	// pobieranie danych z pliku lub serwera w zaleznosci czy podane sa adresy/sciezki
	private void downloadStudentList(){
		// wrzucenie poprzedniej listy (newlist) do oldList
		oldStudentList = newStudentList;
		
		// sprawdzenie czy adresy/sciezki sa podane
		if(urlAdress.equals("") && filePath.equals("")){
			throw new CrawlerException("Nie podano adresu URL i ścieżki do pliku!");
		}
		// pobranie nowej listy studentow do newList
		if(!urlAdress.equals("")){
			downloadFromUrl();
		}else if(!filePath.equals("")){
			downloadFromFile();
		}
	}
	
	private void downloadFromUrl(){
		try {
			newStudentList = new HashSet<Student>(StudentsParser.parse( new URL( urlAdress ) ));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void downloadFromFile(){
		try {
			newStudentList = new HashSet<Student>(StudentsParser.parse( new File( filePath ) ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> extractStudents(OrderMode mode){
		List<Student> list = new ArrayList<Student>(newStudentList);
		
		switch(mode){
			case MARK:
				Collections.sort(list, new MarkComparator());
				break;
			case AGE:
				Collections.sort(list, new AgeComparator());
				break;
			case FIRST_NAME:
				Collections.sort(list, new FirstNameComparator());
				break;
			case LAST_NAME:
				Collections.sort(list, new LastNameComparator());
				break;
		}
		return list;
	}

	private double extractMark(ExtremumMode mode){
		switch(mode){
			case MIN:
				Student s1 = Collections.min(newStudentList, new MarkComparator());
				return s1.getMark();
			case MAX:
				Student s2 = Collections.max(newStudentList, new MarkComparator());
				return s2.getMark();
			default:
					return 0;
		}
	}
	
	private int extractAge(ExtremumMode mode){
		switch(mode){
		case MIN:
			Student s1 = Collections.min(newStudentList, new AgeComparator());
			return s1.getAge();
		case MAX:
			Student s2 = Collections.max(newStudentList, new AgeComparator());
			return s2.getAge();
		default:
				return 0;
	}
		
	}
	
	private void showInfo(){
		// wyswietlenie zakresu wieku
		System.out.println("Age: <" + extractAge(ExtremumMode.MIN) + ", " + extractAge(ExtremumMode.MAX) + ">");
		
		// wyswietlenie zakresu ocen
		System.out.println("Mark: <" + extractMark(ExtremumMode.MIN) + ", " + extractMark(ExtremumMode.MAX) + ">");
		
		// wyswietlenie posortowanej listy studentow
		List<Student> orderedList;
		String orderMode = "";
		
		switch(mode){
			case MARK:
				orderedList = extractStudents(OrderMode.MARK);
				orderMode = "mark";
				break;
			case AGE:
				orderedList = extractStudents(OrderMode.AGE);
				orderMode = "age";
				break;
			case FIRST_NAME:
				orderedList = extractStudents(OrderMode.FIRST_NAME);
				orderMode = "first name";
				break;
			case LAST_NAME:
				orderedList = extractStudents(OrderMode.LAST_NAME);
				orderMode = "last name";
				break;
			default:
				orderedList = null;
				break;
		}
		System.out.println("Ordered by: " + orderMode);
		
		if(orderedList != null){
			for(Student s : orderedList){
				System.out.println(s.toString());
			}
		}
		
	}
	
	public void addAddedStudentEvent(){
		eventList.add(new AddedStudentEvent());
	}
	
	public void addRemovedStudentEvevnt(){
		eventList.add(new RemovedStudentEvent());
	}
	
	// odpala eventy ktore odpalaja loggery
	private void sendNotifications(Set<Student> addeddStudents, Set<Student> removedStudents){
		for(Event e : eventList){
			if(e instanceof AddedStudentEvent){
				e.logEvent(addeddStudents);
			}else if(e instanceof RemovedStudentEvent){
				e.logEvent(removedStudents);
			}
		}
	}
	
	public String getUrlAdress() {
		return urlAdress;
	}

	public void setUrlAdress(String urlAdress) {
		this.urlAdress = urlAdress;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public OrderMode getMode() {
		return mode;
	}

	public void setMode(OrderMode mode) {
		this.mode = mode;
	}



	// enum for age and mark
	public enum ExtremumMode {

		MIN,
		MAX;
	}
	
	// enum for order mode
	public enum OrderMode {

		MARK,
		FIRST_NAME,
		LAST_NAME,
		AGE;
	}
	
}
