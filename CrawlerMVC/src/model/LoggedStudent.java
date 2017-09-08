package model;

import java.util.ArrayList;
import java.util.List;

import example.Student;

public class LoggedStudent extends Student{

	// unix time
	private long time;
	
	private Status status;
	
	// return students
	public List<LoggedStudent> listStudents(){
		return new ArrayList<LoggedStudent>();
	}
	
	private enum Status{
		ADDED, REMOVED;
	}
}
