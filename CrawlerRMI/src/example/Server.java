package example;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		
			int port = 6666;
						
		    // uruchamianie serwera
				
			String name = "rmi://" + port + "/RMICrawlerProxyObject";
			Registry registry = LocateRegistry.createRegistry( port ); // uruchomienie rejestru dla rmi na wybranym porcie
			
			try
			{
				 RMICrawlerProxy rmiCrawlerProxy = new RMICrawlerProxy(); // tworzenie obiektu na serwerze
				 registry.bind( name, rmiCrawlerProxy ); // bindowanie obiektu 
				 
				 System.out.println( "Type 'exit' to exit server." );
				 Scanner scanner = new Scanner( System.in );
					
					while ( true )
					{
						if ( scanner.hasNextLine() )
						{
							if ( "exit".equals( scanner.nextLine() ) )
								break;
						}
					}
					
					scanner.close();

			}
			finally
			{
				UnicastRemoteObject.unexportObject( registry, true ); // zwalnianie rejestru
				System.out.println( "Server stopped." ); // komunikat zakonczenia
			}
	}
}
