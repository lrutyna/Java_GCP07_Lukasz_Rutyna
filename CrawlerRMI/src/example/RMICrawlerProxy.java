package example;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import comparators.AgeComparator;
import comparators.FirstNameComparator;
import comparators.LastNameComparator;
import comparators.MarkComparator;
import events.MessageEvent;
import exception.CrawlerException;

public class RMICrawlerProxy extends UnicastRemoteObject implements ItfCrawler{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// sciezka do pliku
	private String urlAdress ="";
	private String filePath = "";
	
	// struktury danych przechowujace studentow
	private HashSet<Student> oldStudentList;
	private HashSet<Student> newStudentList;
	
	// porzadek sortowania
	private OrderMode mode;
	
	// event
	private MessageEvent event;
	
	
	public RMICrawlerProxy() throws RemoteException{
		super();
		mode = OrderMode.MARK;
	}
	
	@Override
	public void setMessageEvent( MessageEvent event ) throws RemoteException
	{
		this.event = event;		
	}
	
	@Override
	public void lookForChanges() throws RemoteException{

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
			
			for(Student el : addedStudents){
				event.messageSended("ADDED " + el.toString());
			}
			
			for(Student el : removedStudents){
				event.messageSended("REMOVED " + el.toString());
			}
		}
		
	}

	@Override
	public void downloadStudentList() throws RemoteException {
		
        oldStudentList = newStudentList;
		
		// sprawdzenie czy adresy/sciezki sa podane 
		if(urlAdress.equals("") && filePath.equals("")){
			throw new CrawlerException("Nie podano adresu URL i œcie¿ki do pliku!");
		}
		// pobranie nowej listy studentow do newList
		if(!urlAdress.equals("")){
			downloadFromUrl();
		}else if(!filePath.equals("")){
			downloadFromFile();
		}
	}

	@Override
	public void downloadFromUrl() throws RemoteException {
		try {
			newStudentList = new HashSet<Student>(StudentsParser.parse( new URL( urlAdress ) ));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void downloadFromFile() throws RemoteException {
		try {
			newStudentList = new HashSet<Student>(StudentsParser.parse( new File( filePath ) ));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Student> extractStudents(OrderMode mode) throws RemoteException {
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

	@Override
	public double extractMark(ExtremumMode mode) throws RemoteException {
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

	@Override
	public int extractAge(ExtremumMode mode) throws RemoteException {
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

	@Override
	public void showInfo() throws RemoteException {
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
		
		//wypisanie tej listy posortowanej
		if(orderedList != null){
			for(Student s : orderedList){
				System.out.println(s.toString());
			}
		}
		
	}
	
	@Override
	public String getUrlAdress() throws RemoteException{
		return urlAdress;
	}

	@Override
	public void setUrlAdress(String urlAdress) throws RemoteException{
		this.urlAdress = urlAdress;
	}

	@Override
	public String getFilePath() throws RemoteException{
		return filePath;
	}

	@Override
	public void setFilePath(String filePath) throws RemoteException{
		this.filePath = filePath;
	}
	
	@Override
	public OrderMode getMode() throws RemoteException{
		return mode;
	}

	@Override
	public void setMode(OrderMode mode) throws RemoteException{
		this.mode = mode;
	}
	
	enum ExtremumMode{

		MIN,
		MAX;
	}
	
	enum OrderMode {

		MARK,
		FIRST_NAME,
		LAST_NAME,
		AGE;
	}

}
