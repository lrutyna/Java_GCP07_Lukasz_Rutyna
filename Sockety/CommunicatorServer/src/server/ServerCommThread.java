package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerCommThread extends Thread{
	
	protected Socket socket = null;
	protected Server server;
	protected DataInputStream inputStream = null;
	protected DataOutputStream outStream = null;
	public boolean stopped = false;
	
	public ServerCommThread(Server server, Socket socket){
		this.socket = socket;
		this.server = server;
	}
	
	public void run(){
		
		try{
			inputStream = new DataInputStream(socket.getInputStream());
			
			outStream = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException e){
			System.out.println("Blad przy tworzeniu strumieni");
			server.removeThread(this);
			return;
		}
		
		
        String line = null;
        int type = 0;
        
		while(!stopped){
			try {
				type= inputStream.readByte();
				line = inputStream.readUTF();
				
				processMessage(line,type);
				
				} catch (IOException e) {
					System.out.println("Blad wejscia-wyjscia"+e);
					break;
			}
		}
		
		try{
			if(!socket.isClosed()){
				socket.close();
			}
		}
		catch(IOException e){}

		stopped = true;
		server.removeThread(this);
	}
	
	public void processMessage(String line, int type){
		sendToAll(line,type);
	}
	
	public void send(String line, int type){
		try {
			outStream.writeByte(type);
			outStream.writeUTF(line + "\n\r");
		} catch (IOException e) {
			System.out.println("Blad wejscia-wyjscia"+e);
		}
	}
	
	public void sendToAll(String line, int type){
		synchronized(server.commThreadList){
			for(int i =0; i < server.commThreadList.size(); i++ ){
				server.commThreadList.elementAt(i).send(line,type);
			}
		}
	}
	
	public void interrupt(){
		super.interrupt();
		
		try{
			socket.close();
		}
		catch(IOException e){}
	}

	public String getInfo() {
		String info = this.getName() + "| ";
		info += "IP " + socket.getInetAddress().getHostAddress() + " ";
		info += "Port " + socket.getPort() + " ";
		return info;
	}

}
