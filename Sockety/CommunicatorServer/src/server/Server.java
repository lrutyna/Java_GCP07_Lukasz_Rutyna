package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Server {

	protected Vector<ServerCommThread> commThreadList;
	protected ServerThread serverThread = null;
	protected BufferedReader buffReader = null;
	
	public Server(){
		commThreadList = new Vector<ServerCommThread>();
		serverThread = new ServerThread(this,6666);
		serverThread.start();
		
		try{
			buffReader = new BufferedReader(new InputStreamReader(System.in));
		}
		catch(Exception e){
			System.out.println("Blad utworzenia strumienia wejsciowego: " + e);
		}
	}
	
	
	public static void main(String args[]){
		Server server = new Server();
		server.start();
	}
	
	//quit w konsoli lub status
	public void start(){
		String line;
		
		while(true){
			try{
				System.out.println(">");
				line = buffReader.readLine();
				if(line.equals("quit")){
					stop();
				}else if(line.equals("status")){
					showStatus();
				}
			}
			catch(IOException e){
				System.out.println("Blad wejscia-wyjscia: " + e);
			}
			catch(Exception e){
				System.out.println("Blad ogolny: " + e);
			}
		}
	}
	
	public void stop(){
        System.out.println("Konczenie pracy...");
		
		serverThread.interrupt();
		
		for(int i = 0; i < commThreadList.size(); i++){
			commThreadList.elementAt(i).interrupt();
		}
		try{
			Thread.sleep(200);
		}
		catch(Exception e){
			System.exit(0);
		}
	}
	
	public void showStatus(){
		if(commThreadList.size()<1){
			System.out.println("Nie ma zadnych polaczen");
		}else{
			for(int i = 0; i < commThreadList.size(); i++){
				System.out.println(commThreadList.elementAt(i).getInfo());
			}
		}
	}
	
	public void addThread(ServerCommThread thread){
		commThreadList.add(thread);
	}
	
	public void removeThread(ServerCommThread thred){
		commThreadList.remove(thred);
	}
}
