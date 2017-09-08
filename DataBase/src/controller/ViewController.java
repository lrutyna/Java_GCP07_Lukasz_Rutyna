package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ManageUser;
import model.User;
import model.UserAddress;
import model.UserHistory;

public class ViewController implements Initializable{

	@FXML
	private TableColumn<User, String> loginColumn;
	@FXML
	private TableColumn<User, String> passwordColumn;
	@FXML
	private TableColumn<User, String> nameColumn;
	@FXML
	private TableColumn<User, String> lastNameColumn;
	@FXML
	private TableColumn<User, Integer> ageColumn;
	@FXML
	private TableColumn<User, String> genderColumn;
	@FXML
	private TableColumn<User, String> emailColumn;
	@FXML
	private TableColumn<UserAddress, String> cityColumn;
	@FXML
	private TableColumn<UserAddress, String> streetColumn;
	@FXML
	private TableColumn<UserAddress, Integer> localNumberColumn;
	@FXML
	private TableColumn<UserAddress, String> postcodeColumn;
	@FXML
	private TableColumn<UserAddress, String> countryColumn;
	@FXML
	private TableColumn<UserHistory, Date> loggingHistoryColumn;
	@FXML
	private Button readButton;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button editButton;
	@FXML
	private TableView<User> tableView;
	@FXML
	private TableView<UserAddress> tableView2;
	@FXML
	private TableView<UserHistory> tableView3;
	
	private List<User> updatedUsers;
	private List<UserAddress> updatedAddresses;
	private List<UserHistory> updatedHistories;
	private ManageUser manageUser = new ManageUser();
	private ObservableList<UserAddress> address = FXCollections.observableArrayList();
	private ObservableList<UserHistory> histories = FXCollections.observableArrayList();
	private User actualUser;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
		passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
		streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
		localNumberColumn.setCellValueFactory(new PropertyValueFactory<>("localNumber"));
		postcodeColumn.setCellValueFactory(new PropertyValueFactory<>("postcode"));
		countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
		loggingHistoryColumn.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
		
		addButton.setDisable(true);
		deleteButton.setDisable(true);
		editButton.setDisable(true);
	}
	
	
	public ObservableList<User> getUsersList(){
		ObservableList<User> users = FXCollections.observableArrayList();
		for(User user: updatedUsers){
			users.add(user);
		}
		return users;
	}
	
	public ObservableList<UserAddress> getAddressesList(){
		ObservableList<UserAddress> addresses = FXCollections.observableArrayList();
		for(UserAddress address: updatedAddresses){
			addresses.add(address);
		}
		return addresses;
	}
	
	public ObservableList<UserHistory> getHistoriesList(){
		ObservableList<UserHistory> histories = FXCollections.observableArrayList();
		for(UserHistory history: updatedHistories){
			histories.add(history);
		}
		return histories;
	}
	
	public void onRead(){
		updatedUsers = manageUser.readUsers();
		tableView.setItems(getUsersList());
		
		this.isAdmin();
	}
	
    public void onAdd() throws IOException{ 
    	User selectedUser = tableView.getSelectionModel().getSelectedItem();
    	
    	Stage stage = (Stage) addButton.getScene().getWindow();
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editFXML.fxml"));
	    Parent root = loader.load();
	    
	    EditController editController = loader.getController();
	    editController.setInfo("dodano");
		editController.setActualUser(selectedUser);
			
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
    
    public void onDelete(){
    	User selectedUser = tableView.getSelectionModel().getSelectedItem();
    	
    	if(selectedUser != null){
        	if(selectedUser.getLogin().equals("admin")){
        		
        	}else{
        		manageUser.deleteUser(selectedUser);
            	this.onRead();
            	tableView2.getItems().clear();
        		tableView3.getItems().clear();
        	}
    	}
    }
    
    public void onEdit() throws IOException{
    	User selectedUser = tableView.getSelectionModel().getSelectedItem();
    	
    	if(selectedUser != null){
    		Stage stage = (Stage) addButton.getScene().getWindow();
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editFXML.fxml"));
    	    Parent root = loader.load();
    		EditController editController = loader.getController();
    		editController.setInfo("edytowano");
    		editController.setActualUser(selectedUser);
    			
    		Scene scene = new Scene(root);
    		stage.setScene(scene);
    		stage.show();
    	}
    }
	
	public void onReleased(){
		User selectedUser = tableView.getSelectionModel().getSelectedItem();
		tableView2.getItems().clear();
		tableView3.getItems().clear();
		
		if(selectedUser.getId() == selectedUser.getUserAddress().getId()){
			address.add(selectedUser.getUserAddress());
			tableView2.setItems(address);
			histories.add(selectedUser.getUserHistory());
			tableView3.setItems(histories);
		}
	}

	public void isAdmin(){
		if(actualUser.getLogin().equals("admin")){
			addButton.setDisable(false);
			deleteButton.setDisable(false);
			editButton.setDisable(false);
		}else{
			addButton.setDisable(true);
			deleteButton.setDisable(true);
			editButton.setDisable(true);
		}
	}

	public User getActualUser() {
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}
}
