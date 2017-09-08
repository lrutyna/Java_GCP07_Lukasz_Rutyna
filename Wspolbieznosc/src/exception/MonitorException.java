package exception;


public class MonitorException extends IllegalThreadStateException{

	private static final long serialVersionUID = 1L;

	public MonitorException(String message){
		super(message);
	}
}
