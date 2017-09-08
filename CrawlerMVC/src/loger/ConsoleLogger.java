package loger;

import example.Student;

public class ConsoleLogger implements Logger {

	@Override
	public void log(String status, Student student) {
		System.out.println(status + " " + student.toString()); 

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
