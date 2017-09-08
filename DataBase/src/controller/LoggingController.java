package controller;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ManageUser;
import model.User;

public class LoggingController implements Initializable{

	@FXML
	private TextField textField1;
	@FXML
	private Label infoLabel;
	@FXML
	private Button loggingButton;
	@FXML
	private Button registerButton;
	@FXML
	private PasswordField passwordField;
	
	private String login,password, encryptedPassword;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
	
	@FXML
	public void onLogging() throws IOException{
		setLogin();
		try {
			setPassword();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		ManageUser manageUser = new ManageUser();
		List<User> users = manageUser.readUsers();
		
		for(User user: users){
			if(user.getLogin().equals(login) && user.getPassword().equals(encryptedPassword)){
			    user.getUserHistory().setEntryDate(new Date());;
			    manageUser.updateUser(user);
			    infoLabel.setText("");
			    
			    Stage stage = (Stage) loggingButton.getScene().getWindow();
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/viewFXML.fxml"));
				Parent root = loader.load();
				ViewController viewController = loader.getController();
				viewController.setActualUser(user);
				
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			    
			}else{
				infoLabel.setText("Nieprawid³owy login lub has³o");
			}
		}
	}
	
	@FXML
	public void onRegister() throws IOException{
		Stage stage = (Stage) loggingButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registerFXML.fxml"));
		Parent root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setLogin(){
		this.login = textField1.getText();
	}
	
	public void setPassword() throws NoSuchAlgorithmException{
		this.password = passwordField.getText();
		
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		this.encryptedPassword = new String(messageDigest.digest());
	}

}
