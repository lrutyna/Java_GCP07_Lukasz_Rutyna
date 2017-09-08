package example;


import java.util.ArrayList;
import events.StudentEventListener;
import loger.ConsoleLogger;
import loger.Logger;
import loger.ParallelLogger;

public class Program 
{
	private static ArrayList<String> linksList;
	final static Logger [] loggers = new Logger[]
			{
				new ConsoleLogger(),
			};

	public static void main( String[] args )
	{
		linksList = new ArrayList<String>();
		setLinksList();
		ParallelLogger parallelLogger = new ParallelLogger(loggers);
		Monitor monitor = new Monitor(linksList, parallelLogger);
		Thread monitorThread = new Thread(monitor);
		
		
		monitor.addStudentAddedListener(new StudentEventListener<Student>(){ 

			@Override
			public void logHandle(Object source, Student object) {
				parallelLogger.log(parallelLogger.getLogList().get(parallelLogger.getLogList().size() - 1));
			}
		});
		
	    monitor.addStudentRemovedListener(( source, object ) -> {
	    	parallelLogger.log(parallelLogger.getLogList().get(parallelLogger.getLogList().size() - 1));
		});
		
	    monitorThread.start();
	    
	    try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    monitor.cancel();
	}
	
	public static void setLinksList(){
		linksList.add("lista1.txt");
		linksList.add("lista2.txt");
		linksList.add("lista3.txt");
		linksList.add("lista4.txt");
		linksList.add("lista5.txt");
		linksList.add("lista6.txt");
		linksList.add("lista7.txt");
		linksList.add("lista8.txt");
		linksList.add("lista9.txt");
		linksList.add("lista10.txt");
	}
}
