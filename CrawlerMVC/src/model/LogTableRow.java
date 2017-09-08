package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import example.Student;

public class LogTableRow {

	private Date date;
	private String log;

	public LogTableRow(Student s, String status) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		date = new Date();
		log = dateFormat.format(date) + " " + status + " " + s;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

}
