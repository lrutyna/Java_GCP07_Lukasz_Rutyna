package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread{

	protected Client client;
	protected Socket socket = null;
	protected DataInputStream inputStream = null;
	public boolean stopped = false;
	
	public ClientThread(Client client, Socket socket, DataInputStream inStream){
		this.socket = socket;
		this.inputStream = inStream;
		this.client = client;
	}
	
	public void run(){
		String line = null;
		int type = 0;
		
		while(!stopped){
			try{
				type = inputStream.readByte();
				line = inputStream.readUTF();
				System.out.println("to jest " +line+type);
			}
			catch(IOException e){
				break;
			}
			processMessage(line,type);
		}
		client.clientThreadStopped();
	}
	
	public void processMessage(String line, int type){
		client.setText(line,type);
	}
	
	public void interrupt(){
		super.interrupt();
		try{
			socket.close();
		}
		catch(IOException e){
			
		}
	}
	
}
