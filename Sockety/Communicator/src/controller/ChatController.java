package controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client.Client;
import common.EventListener;
import common.MessageType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController implements Initializable{

	@FXML
	private Button connectButton;
	@FXML
	private Button disconnButton;
	@FXML
	private Button logoutButton;
	@FXML
	private TextArea textArea;
	@FXML
	private TextField textField;
	
	private Client client;
	private String text = " ";
	private InetAddress inetAddress = null;
	private String ip;
	private boolean connected = false;
	private String login;
	private int type;
	private List<EventListener> eventListener;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		eventListener = new ArrayList();
	}
	
	
	public void setClient(Client client){
		this.client = client;
		eventListener.add(client);
	}
	
	public void setName(String login){
		this.login = login;
	}
	
	public void setTextOnArea(String line){
		textArea.appendText(text + line);
	}
	
	public void setType(int type){
		
		switch ( type )
		{
		    case MessageType.CONNECT:
		    	textArea.appendText(text + "Polaczono z nowym uzytkownikiem");
				break;
			
			case MessageType.DISCONNECT:
				textArea.appendText(text + "Rozlaczono z uzytkownikiem");
				break;
			
			case MessageType.MESSAGE:
				//textArea.appendText(text + login+" wyslal wiadomosc: " + "\n");
				break;
			
			default:
				break;
		}
	}
	
	@FXML
	public void onConnect(){
		connected = true;
		client.setChatController(this);
	
		
		try{
			inetAddress = InetAddress.getLocalHost();
		}
		catch(UnknownHostException e){
			System.out.println("Nie mozna uzyskac adresu IP tego komputera");
			System.exit(0);
		}
		ip = inetAddress.getHostAddress();
		client.connect("192.168.241.166",6666);
		
		for(EventListener e : eventListener){
				e.connectEvent();
		}
	}
	
	@FXML
	public void onDisconn(){

		for(EventListener e : eventListener){
			e.disconnectEvent();
		}

		client.disconncect();
	}
	
	@FXML
	public void onLogout(){
		
	}
	
	@FXML
	public void onEnter(){
		if(connected){
			String line = " ";
			line = textField.getText();
			
		    client.setLineToSend(login + ": " + line,0);
		    textField.clear();
		}
	    
	}
	
}
