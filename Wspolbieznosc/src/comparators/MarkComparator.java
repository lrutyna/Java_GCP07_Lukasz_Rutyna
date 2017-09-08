package comparators;

import java.util.Comparator;

import example.Student;

public class MarkComparator implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		
		return Double.compare(o1.getMark(), o2.getMark());
	}

}
