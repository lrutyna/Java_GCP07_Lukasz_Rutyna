package comparators;

import java.util.Comparator;

import example.Student;

public class AgeComparator implements Comparator<Student> {

	@Override
	public int compare(Student arg0, Student arg1) {
		
		return Integer.compare(arg0.getAge(), arg1.getAge());
	}
}
