package events;

import java.util.Set;

import example.Student;
import loger.ConsoleLogger;
import loger.Logger;
import loger.MailLogger;

public class RemovedStudentEvent implements Event {

	final Logger [] loggers = new Logger[]
			{
				new ConsoleLogger(),
				new MailLogger()
			};
	
	@Override
	public void logEvent(Set<Student> set) {
		for(Student student : set){
			for(Logger el : loggers){
				el.log("REMOVED", student);
			}
		}
	}

}
