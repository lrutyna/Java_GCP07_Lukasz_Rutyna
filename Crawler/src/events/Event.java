package events;

import java.util.Set;

import example.Student;

public interface Event {

	void logEvent(Set<Student> set);
}
