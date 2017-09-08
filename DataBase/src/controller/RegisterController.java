package controller;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.ManageUser;
import model.User;
import model.UserAddress;
import model.UserHistory;

public class RegisterController implements Initializable{
	@FXML
	private Button backButton;
	@FXML
	private Button okButton;
	@FXML
	private Label infoLabel;
	@FXML
	private TextField fieldLogin;
	@FXML
	private PasswordField fieldPassword;
	@FXML
	private TextField fieldName;
	@FXML
	private TextField fieldLastname;
	@FXML
	private TextField fieldAge;
	@FXML
	private TextField fieldMail;
	@FXML
	private TextField cityField;
	@FXML
	private TextField localField;
	@FXML
	private TextField postcodeField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField countryField;
	@FXML
	private ChoiceBox<String> genderBox;
	
	private String login, password, encryptedPassword, name, lastName, gender, mail, age, local, city, street, country, postcode;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderBox.setItems(FXCollections.observableArrayList(
			    "Mê¿czyzna", "Kobieta")
			);
	}

	@FXML
	public void onOKaction(){
		setLogin();
		try {
			setPassword();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		setName();
		setLastName();
		setAge();
		setGender();
		setMail();
		setCity();
		setLocal();
		setStreet();
		setCountry();
		setPostcode();
		
		if(login.length()==0 || password.length()==0 || name.length()==0 || lastName.length()==0 || age.length()==0 || gender.length()==0 || mail.length()==0
				|| city.length()==0 || local.length()==0 || postcode.length()==0 || street.length()==0){
			infoLabel.setText("Nie wype³niono wszystkich pól");
		}else{
	        
	        UserAddress userAddress = new UserAddress();
	        userAddress.setCity(city);
	        userAddress.setCountry(country);
	        userAddress.setLocalNumber(local);
	        userAddress.setPostcode(postcode);
	        userAddress.setStreet(street);
	        
	        User user = new User();
	        user.setLogin(login);
	        user.setPassword(encryptedPassword);
	        user.setName(name);
	        user.setLastName(lastName);
	        user.setAge(age);
	        user.setGender(gender);
	        user.setMail(mail);
	        
	        UserHistory userHistory = new UserHistory();
	        
	        user.setUserAddress(userAddress);
	        user.setUserHistory(userHistory);
			
			ManageUser manageUser = new ManageUser();
			manageUser.addUser(user);
			
			manageUser.readUsers();
			infoLabel.setText("Zarejestrowano");
			okButton.setDisable(true);
		}
	}
	
	@FXML
	public void onBackAction() throws IOException{
		Stage stage = (Stage) okButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loggingFXML.fxml"));
		Parent root = loader.load();
		LoggingController loggingController = loader.getController();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void setLogin(){
		this.login = fieldLogin.getText(); 
	}
	
	public void setPassword() throws NoSuchAlgorithmException{
		this.password = fieldPassword.getText();
		
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		this.encryptedPassword = new String(messageDigest.digest());
	}
	
	public void setName(){
		this.name = fieldName.getText(); 
	}
	
	public void setLastName(){
		this.lastName = fieldLastname.getText(); 
	}
	
	public void setAge(){
		this.age = fieldAge.getText(); 
	}
	
	public void setGender(){
		this.gender = genderBox.getSelectionModel().getSelectedItem(); 
	}
	
	public void setMail(){
		this.mail = fieldMail.getText(); 
	}
	
	public void setLocal(){
		this.local = localField.getText(); 
	}
	
	public void setCity(){
		this.city = cityField.getText(); 
	}
	
	public void setStreet(){
		this.street = streetField.getText(); 
	}
	
	public void setCountry(){
		this.country = countryField.getText(); 
	}
	
	public void setPostcode(){
		this.postcode = postcodeField.getText(); 
	}
	
}
