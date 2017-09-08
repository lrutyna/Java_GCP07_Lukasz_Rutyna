package controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RPcontroller {

	@FXML
	Button button1;

	@FXML
	Button clearButton;

	@FXML
	Button cancelButton;

	@FXML
	TextField login;

	@FXML
	PasswordField password;

	@FXML
	TextField age;

	@FXML
	TextField address;

	@FXML
	TextField gender;

	@FXML
	Label success;

	private Stage stage;
	private Parent root;

	private String propertiesFileName = "conf.properties";

	@FXML
	public void onAction() throws IOException {
		// save new user values to properties file and return to login panel
		Properties properties = new Properties();
		OutputStream outputStream = null;

		try {
			outputStream = new FileOutputStream(propertiesFileName);

			// set properties value
			properties.setProperty("login", login.getText());
			properties.setProperty("password", getPasswordHash(password.getText()));
			properties.setProperty("age", age.getText());
			properties.setProperty("address", address.getText());
			properties.setProperty("gender", gender.getText());

			// save properties to file
			properties.store(outputStream, null);

			// show message
			success.setVisible(true);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void clearAction() {
		// clear fields
		login.clear();
		password.clear();
		age.clear();
		address.clear();
		gender.clear();
	}

	@FXML
	public void cancelAction() throws IOException {

		stage = (Stage) button1.getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPanel.fxml"));
		root = loader.load();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	private String getPasswordHash(String password) {
		int hash = 7;
		for (int i = 0; i < password.length(); i++) {
			hash = hash * 31 + password.charAt(i);
		}
		return String.valueOf(hash);
	}
}
