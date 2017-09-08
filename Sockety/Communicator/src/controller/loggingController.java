package controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loggingController implements Initializable{

	@FXML
	private Label infoLabel;
	@FXML
	private Button button1;
	@FXML
	private Button button2;
	@FXML
	private TextField textField1;
	@FXML
	private TextField textField2;
	
	
	private Client client;
	private Stage stage;
	private Parent root;
	private boolean ifLogged = false;
	private String login;
	private String password;
	
	
	public void setClient(Client client){
		this.client = client;
	}
	
	public void setLogin(){
		this.login = textField1.getText();
	}
	
	public void setPassword(){
		this.password = textField2.getText();
	}
	
	
	@FXML
	public void onAction() throws IOException{
		
		setLogin();
		setPassword();
		
		
		
		
			stage = (Stage) button1.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatFXML.fxml"));
			
			root = loader.load();
			
			ChatController controller = loader.getController();
			controller.setClient(client);
			controller.setName(login);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			
			stage.show();
		
	}
	
	@FXML
	public void toRegister() throws IOException{
		stage = (Stage) button1.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerFXML.fxml"));
		
		root = loader.load();
		
		registerController controller = loader.getController();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		
		stage.show();
	
	}




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
