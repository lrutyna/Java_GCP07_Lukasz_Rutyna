package loger;


public class ConsoleLogger implements Logger {

	@Override
	public void log(Log log) {
		System.out.println(log.getStatus().toString() + " " + log.getStudent().toString()); 

	}

}
