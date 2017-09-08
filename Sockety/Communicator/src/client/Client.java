package client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import common.MessageType;
import controller.ChatController;
import common.EventListener;

public class Client implements EventListener{

	private Socket clientSocket = null;
	private DataInputStream inStream = null;
	private DataOutputStream outStream = null;
	private InetAddress inetAddress = null;
	private String ip;
	private String line;
	private String text;
	private ChatController chat;
	private int type;
	
	protected boolean connected = false;
	protected ClientThread clientThread;
	
	
	public Client(){
		
	}
	
	
	public void connect(String ip, int port){
		connected  = false;
		
		try{
			clientSocket = new Socket(ip,6666);
		}
		catch (IOException e) {
			System.err.println("Blad wejscia-wyjscia: " + e);
			return;
		}
		
		try{
			outStream = new DataOutputStream(clientSocket.getOutputStream());
			inStream = new DataInputStream( clientSocket.getInputStream() );
			
		}catch(IOException e){
			System.out.println("Blad przy tworzrniu strumieni");
			return;
		}
		clientThread = new ClientThread(this,clientSocket,inStream);
		clientThread.start();
		connected = true;
		
	}
	
	public void disconncect(){
		connected = false;
		clientThread.interrupt();
	}
	
	public void setLineToSend(String line, int type){
		this.line = line;
		this.type = type;
		sendLine(line,type);
	}
	
	public void setChatController(ChatController chat){
		this.chat = chat;
	}
	
	public void setText(String text, int type){
		this.text= text;
		chat.setType(type);
		chat.setTextOnArea(text);
	}
	
	public void sendLine(String line, int type){
		
		try{
			outStream.writeByte(type);
			outStream.writeUTF(line);
			outStream.flush();
		}catch(IOException e){
			
		}
	}
	
	
	public void clientThreadStopped(){
		connected = false;
		try{
			if(!clientSocket.isClosed()){
				clientSocket.close();
			}
			}catch(IOException e){}
	}
	
	


	@Override
	public void connectEvent() {
		this.setLineToSend("",MessageType.CONNECT);
		
	}


	@Override
	public void disconnectEvent() {
		this.setLineToSend("",MessageType.DISCONNECT);
		
	}
	
}
