package comparators;

import java.util.Comparator;

import example.Student;

public class FirstNameComparator implements Comparator <Student>{

	@Override
	public int compare(Student o1, Student o2) {
		return o1.getFirstName().compareTo(o2.getFirstName());
	}
}
