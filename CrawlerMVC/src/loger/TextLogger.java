package loger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import example.Student;

public class TextLogger implements Logger, Closable {

	private String fileName = "textLogger.txt";
	private DateTime date;
	private File file;
	private BufferedWriter bw;
	private FileWriter fw;

	public TextLogger() {
		file = new File(fileName);
		try {
			fw = new FileWriter(file.getAbsolutePath(), true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// constructor used for CompressedLogger
	public TextLogger(DateTime d) {
		this.date = d;
		file = new File(setFileNameWithDate(d));

		// create new file
		try {
			file.createNewFile();
			fw = new FileWriter(file.getAbsolutePath(), true);
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		try {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void log(String status, Student student) {
		// write log to file
		DateTime now = new DateTime();
		DateTimeFormatter df = DateTimeFormat.forPattern("HH:mm:ss.SSS");

		try {
			bw.write(df.print(now) + " " + status + " " + student);
			bw.newLine();
			bw.flush();
			System.out.println(df.print(now) + " " + status + " " + student);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String setFileNameWithDate(DateTime d) {
		DateTimeFormatter out = DateTimeFormat.forPattern("yyyy.MM.dd_HH:mm:ss.SSS");
		return out.print(d) + ".txt";
	}
}
