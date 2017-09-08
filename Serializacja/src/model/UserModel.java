package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class UserModel {

	private File f = new File("conf.properties");
	private Properties properties = new Properties();
	

    public void saveProperties(String login, String haslo, String wiek, String adres, String plec){
    	OutputStream os;
        try {
        	os = new FileOutputStream(f);
            properties.setProperty("Login", login);
            properties.setProperty("Haslo", haslo);
            properties.setProperty("Wiek", wiek);
            properties.setProperty("Adres", adres);
            properties.setProperty("Plec", plec);
            properties.store(os, null);
            os.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    public void loadProperties(){
    	InputStream is;
        try {
            is = new FileInputStream(f);
            //³adujemy nasze ustawienia
            properties.load(is);
            is.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
}
