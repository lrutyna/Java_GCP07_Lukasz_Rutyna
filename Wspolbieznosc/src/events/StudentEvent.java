package events;

import java.util.ArrayList;
import java.util.List;

public class StudentEvent<T> {

	private List<StudentEventListener<T>> listeners = new ArrayList<>();
	
	public void fire( Object source, T object )
	{
		for( StudentEventListener<T> el : this.listeners )
			el.logHandle( source, object );
	}
	
	public void add( StudentEventListener<T> listener )
	{
		this.listeners.add( listener );
	}
	
	public void remove( StudentEventListener<T> listener )
	{
		this.listeners.remove( listener );
	}
}
