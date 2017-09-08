package example;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import events.MessageEvent;
import example.RMICrawlerProxy.OrderMode;
import loger.ConsoleLogger;
import loger.Logger;


public class Client {
	
	final static Logger [] loggers = new Logger[]
			{
				new ConsoleLogger(),
			};

	public static void main(String[] args) throws RemoteException, NotBoundException {  
		
		// konfiguracja
		String host = "localhost";
		int port = 6666;
		
		// nawiazywanie polaczenia
		 
		String name = "rmi://" + port + "/RMICrawlerProxyObject";
		
		Registry registry = LocateRegistry.getRegistry( host, port ); // nawiazywanie polaczenia
		ItfCrawler itfCrawler = (ItfCrawler) registry.lookup( name ); // pobranie zbindowanej na serwerze logiki 
		
		itfCrawler.setFilePath("student.txt");
		itfCrawler.setMode(OrderMode.AGE);
		itfCrawler.setMessageEvent(new CustomMessageEvent());
		
		//int iterationCount = 1;
		
        while(true){
        	
			// pobranie listy studentow 
        	itfCrawler.downloadStudentList();
			
			// wykrycie zmian
        	itfCrawler.lookForChanges();
			
			// wyswietlenie informacji o iteracji
			//System.out.println("Iteration: " + iterationCount);
			itfCrawler.showInfo();
			
			//odczekanie 10 sekund
			try {
				Thread.sleep(10000);
				//iterationCount++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class CustomMessageEvent extends UnicastRemoteObject implements MessageEvent
	{
		private static final long serialVersionUID = 1L;
		
		protected CustomMessageEvent() throws RemoteException
		{
			super();
		}
		
		@Override
		public void messageSended( String message )
		{
			for(Logger el: loggers){
				el.log(message);
			}
		}
	}
}
