package controller;

import java.io.IOException;
import java.net.URL;
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

public class registerController implements Initializable{

	@FXML 
	private Button saveButton;
	@FXML 
	private Button cancelButton;
	@FXML 
	private TextField genderField;
	@FXML 
	private TextField loginField;
	@FXML 
	private TextField passField;
	@FXML 
	private TextField addressField;
	@FXML 
	private TextField ageField;
	@FXML 
	private Label infoLabel;
	
	private String login;
	private String password;
	private String gender;
	private String address;
	private String age;
	private Stage stage;
	private Parent root;
	private Client client;
	private boolean ifRegistered = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLogin(){
		this.login = loginField.getText();
	}
	
	public void setPassword(){
		this.password = passField.getText();
	}
	
	public void setGender(){
		this.gender = genderField.getText();
	}
	
	public void setAge(){
		this.age = ageField.getText();
	}
	
	public void setAddress(){
		this.address = addressField.getText();
	}
	
	public void setClient(Client client){
		this.client = client;
	}
	
	@FXML
	public void onSave(){
		this.setLogin();
		this.setPassword();
		this.setAddress();
		this.setGender();
		this.setAge();
		if(login.length()<5 || password.length() < 5 ){
			infoLabel.setText("Login i has³o musza mieæ po conajmniej 5 znakow");
		}else{
			infoLabel.setText("Zarejestrowano");
			ifRegistered = true;
		}
	}
	
	@FXML
	public void onCancel() throws IOException{
		stage = (Stage) cancelButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loggingFXML.fxml"));
		
		root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		
		
		stage.show();
	}
	
}
