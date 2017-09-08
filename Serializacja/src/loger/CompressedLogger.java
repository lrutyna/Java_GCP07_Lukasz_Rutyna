package loger;

import org.joda.time.DateTime;

import example.Student;

public class CompressedLogger implements Logger, Closable {

	private String directoryName;
	private TextLogger textLogger;
	
	
	public CompressedLogger() {
		this.directoryName = "logArchive.zip";
		// create archive if not exist
		
		
		// create file to log
		textLogger = new TextLogger(new DateTime());
	}
	
	@Override
	public void log(String status, Student student) {
		// log to file
//		textLogger.log(status, student);

	}

	@Override
	public void close() {
		// close file
		textLogger.close();
		
	}

	private void createEmptyArchive(){
		
	}
	
}
