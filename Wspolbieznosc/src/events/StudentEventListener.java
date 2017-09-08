package events;


public interface StudentEventListener<T> {

	void logHandle(Object source, T object);
}
