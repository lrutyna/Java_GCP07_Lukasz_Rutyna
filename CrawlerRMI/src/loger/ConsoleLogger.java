package loger;


public class ConsoleLogger implements Logger {

	@Override
	public void log(String message) {
		System.out.printf("Server message: %s\n", message); 
	}
}
