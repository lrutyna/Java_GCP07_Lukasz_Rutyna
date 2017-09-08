package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{

	protected ServerSocket serverSocket = null;
	protected int port;
	protected Server server;
	
	public ServerThread(Server server, int port){
		this.server = server;
		this.port = port;
	}
	
	public void run(){
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e) {
			System.out.println("Blad przy utworzeniu gniazda serwerowego");
			return;
		}
		
		//oczekuje polaczen, startuje watek komunikacyjny
		while(true){
			try {
			    socket = serverSocket.accept();
			}
			catch (IOException e) {
				System.out.println("Blad wejscia-wyjscia" +e);
				return;
			}
			ServerCommThread thread = new ServerCommThread(server,socket);
			server.addThread(thread);                                        //dodanie do vectora watku komunikacji
			thread.start();
		}
	}
	
	public void interrupt(){
		super.interrupt();
		
		try{
			serverSocket.close();
		}
		catch(IOException e){
		}
	}
}
