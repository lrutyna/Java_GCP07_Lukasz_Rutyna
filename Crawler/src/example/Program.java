package example;

import example.Crawler.OrderMode;

public class Program 
{
	public static void main( String[] args )
	{	
		Crawler crawler = new Crawler();
		
		// dodawanie eventow
		crawler.addAddedStudentEvent();
		crawler.addRemovedStudentEvevnt();
		
		// ustawienie scizki do pliku lub urla
		crawler.setFilePath("students.txt");
//		crawler.setUrlAdress("http://volt.iem.pw.edu.pl/~rutynar/students.txt");
		
		// ustawieie porzadku sortowania
		crawler.setMode(OrderMode.AGE);
		
		Thread t = new Thread(crawler);
		t.start();
	}
}
