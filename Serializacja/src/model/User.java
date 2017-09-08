package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {

	private StringProperty login;
	private StringProperty password;
	private StringProperty age;
	private StringProperty address;
	private StringProperty gender;
	
	public User(){
		login = new SimpleStringProperty();
		password = new SimpleStringProperty();
		age = new SimpleStringProperty();
		address = new SimpleStringProperty();
		gender = new SimpleStringProperty();
		
	}
	
	public String getLogin() {
		return login.getValue();
	}
	
	public void setLoginValue(String v){
		login.setValue(v);
	}
	
	public String getAge() {
		return age.get();
	}
	
	public void setAgeValue(String v){
		age.setValue(v);
	}

	public String getAddress() {
		return address.get();
	}
	
	public void setAddres(String v){
		address.setValue(v);
	}

	public String getGender() {
		return gender.get();
	}
	
	public void setGenderValue(String v){
		gender.setValue(v);
	}
	
	public void setPasswordValue(String passwordValue){
		password.setValue(passwordValue);
	}
	
	public String getPasswordValue(){
		return password.getValue();
	}
}
