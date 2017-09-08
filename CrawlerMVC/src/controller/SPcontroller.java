package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CrawlerTask;
import model.User;

public class SPcontroller implements Initializable {

	@FXML
	private Button button1;
	@FXML
	private Button button2;

	@FXML
	private TextField login;

	@FXML
	private TextField password;

	@FXML
	private Label message;

	private Stage stage;
	private Parent root;
	private CrawlerTask task;
	
	// file name
	private String propertiesFileName = "conf.properties";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	public void onAction() throws IOException {

		// chceck password and login
		User user = loadPropertiesFile();
		if (user.getPasswordValue().equals(getPasswordHash(password.getText()))
				&& user.getLogin().equals(login.getText())) {

			stage = (Stage) button1.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/fxmlGUI.fxml"));

			root = loader.load();

			FXMLcontroller controller = loader.getController();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			
			// close files
			stage.setOnHiding(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					task.close();
				}
			});
			
			stage.show();

			task = new CrawlerTask(controller.getCTPcontroller());
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
		} else {
			message.setVisible(true);
		}
	}

	@FXML
	public void toRegister() throws IOException {

		stage = (Stage) button1.getScene().getWindow();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterPanel.fxml"));

		root = loader.load();

		Scene scene = new Scene(root);
		stage.setScene(scene);
		

		stage.show();
	}

	private User loadPropertiesFile() {
		Properties properties = new Properties();
		User user = new User();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(propertiesFileName);
			properties.load(inputStream);

			// setup user object
			user.setLoginValue(properties.getProperty("login"));
			user.setPasswordValue(properties.getProperty("password"));

			return user;

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	private String getPasswordHash(String password) {
		int hash = 7;
		for (int i = 0; i < password.length(); i++) {
			hash = hash * 31 + password.charAt(i);
		}
		return String.valueOf(hash);
	}

	
}
