package common;

import java.util.List;

import client.Client;

public class Event {

	private Client client;
	private List<EventListener> listenerList;
	
	public Event(Client client){
		this.client = client;
		listenerList.add(client);
	}
	
}
