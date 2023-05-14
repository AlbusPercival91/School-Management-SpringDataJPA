package ua.foxminded.springdatajpa.school.testdata;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import org.springframework.stereotype.Component;

@Component
public class CourseMaker {
	StudentMaker student = new StudentMaker();

	public Set<String> generateCourses() {
		return new HashSet<>(Arrays.asList("Mathematics", "English", "Life Science", "Physical Science", "Geography",
				"Computer Science", "History", "Sports", "Art", "Literature"));
	}

	public Map<Integer, Set<Integer>> assignCourseId() {
		Map<Integer, Set<Integer>> studentCourseID = new HashMap<>();
		int studentsQtty = student.generateStudents(student.generateNames(20), student.generateSurnames(20)).size();

		IntStream.range(1, studentsQtty + 1).forEachOrdered(i -> {
			Set<Integer> courseID = new HashSet<>();
			Integer localSetSize = ThreadLocalRandom.current().nextInt(1, 4);

			IntStream.range(0, localSetSize).mapToObj(j -> ThreadLocalRandom.current().nextInt(1, 11))
					.filter(rand -> Collections.frequency(courseID, rand) < 3).forEachOrdered(courseID::add);
			studentCourseID.put(i, courseID);
		});
		return studentCourseID;
	}

}
