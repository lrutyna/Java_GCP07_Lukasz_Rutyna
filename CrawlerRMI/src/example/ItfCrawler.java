package example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import events.MessageEvent;
import example.RMICrawlerProxy.ExtremumMode;
import example.RMICrawlerProxy.OrderMode;

public interface ItfCrawler extends Remote{
	
	void lookForChanges() throws RemoteException;
	
	void downloadStudentList() throws RemoteException;
	
	void downloadFromUrl() throws RemoteException;
	
	void downloadFromFile() throws RemoteException;
	
	List<Student> extractStudents(OrderMode mode) throws RemoteException;

	double extractMark(ExtremumMode mode) throws RemoteException;
	
	int extractAge(ExtremumMode mode) throws RemoteException;
	
	void showInfo() throws RemoteException;
	
	String getUrlAdress() throws RemoteException;

	void setUrlAdress(String urlAdress) throws RemoteException;

	String getFilePath() throws RemoteException;

	void setFilePath(String filePath) throws RemoteException;
	
	OrderMode getMode() throws RemoteException;

	void setMode(OrderMode mode) throws RemoteException;

	void setMessageEvent(MessageEvent event) throws RemoteException;

}
