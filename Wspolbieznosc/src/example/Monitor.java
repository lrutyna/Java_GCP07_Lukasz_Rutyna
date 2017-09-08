package example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import events.StudentEvent;
import events.StudentEventListener;
import example.Crawler.OrderMode;
import exception.MonitorException;
import loger.Log;
import loger.ParallelLogger;

public class Monitor implements Runnable {
	private boolean running = true;
	private List<String> linksList;
	private List<Crawler> crawlerList;
	private int threadsAmount;
	private ParallelLogger parallelLogger;
	private Monitor monitor;
	private List<Log> logList;
	private Scanner scanner;
	private String string;
	
	private final StudentEvent<Student> studentAddedEventM = new StudentEvent<>();
	private final StudentEvent<Student> studentRemovedEventM = new StudentEvent<>();
	
	public Monitor(ArrayList<String> linksList, ParallelLogger parallelLogger){
		this.linksList = linksList;
		this.parallelLogger = parallelLogger;
		monitor = this;
		crawlerList = new ArrayList<Crawler>();
		logList = parallelLogger.getLogList();
		scanner = new Scanner(System.in);
	}

	@Override
	public void run() throws MonitorException{
		
		while(running){
			
			System.out.print("Podaj liczbe watkow: ");
		    string = scanner.nextLine();
		    threadsAmount = Integer.parseInt(string);
			
			if(linksList.size()< threadsAmount){
				throw new MonitorException("Ilosc watkow jest wieksza od ilosci linkow");
			}else{
				 for(int i = 0; i < threadsAmount; i++){
					 crawlerList.add(new Crawler());
					 crawlerList.get(i).setFilePath(linksList.get(i));
					 crawlerList.get(i).setMode(OrderMode.AGE);
					 
					 crawlerList.get(i).addStudentAddedListener(new StudentEventListener<Student>(){

							@Override
							public void logHandle(Object source, Student object) {
								monitor.crawlerAddedEvent(source, object);
							}
						});
						
					 crawlerList.get(i).addStudentRemovedListener(( source, object ) -> {
						 	 monitor.crawlerRemovedEvent(source, object);
						});
					 
					 crawlerList.get(i).start();
					 
					 try {
						 Thread.sleep(1000);
					 } catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			running = false;
		}
	}
	
	public void cancel(){
		System.out.println("Zamykanie...");
		for(Crawler el : crawlerList){
			try {
				el.postCancel();
				el.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		this.running = false;
		System.out.println("Zakonczono dzialanie monitora");
	}
	
	public synchronized void crawlerAddedEvent(Object source, Student object){
		logList.add(new Log("ADDED", object));
		monitor.studentAddedEventM.fire( this, object );
	}
	
	public synchronized void crawlerRemovedEvent(Object source, Student object){
		logList.add(new Log("REMOVED", object));
		monitor.studentRemovedEventM.fire( this, object );
	}
	
	public void addStudentAddedListener( StudentEventListener<Student> listener )
	{
		this.studentAddedEventM.add( listener );
	}
	
	public void removeStudentAddedListener( StudentEventListener<Student> listener )
	{
		this.studentAddedEventM.remove( listener );
	}
	
	public void addStudentRemovedListener( StudentEventListener<Student> listener )
	{
		this.studentRemovedEventM.add( listener );
	}
	
	public void removeStudentRemovedListener( StudentEventListener<Student> listener )
	{
		this.studentRemovedEventM.remove( listener );
	}

}
