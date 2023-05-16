package ua.foxminded.springdatajpa.school.dao.testdata;

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.springdatajpa.school.dao.interfaces.GeneratorDataRepository;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;
import ua.foxminded.springdatajpa.school.testdata.CourseMaker;
import ua.foxminded.springdatajpa.school.testdata.GroupMaker;
import ua.foxminded.springdatajpa.school.testdata.StudentMaker;

@Service
public class GeneratorDataService {

	@Autowired
	private StudentMaker studentMaker;

	@Autowired
	private CourseMaker courseMaker;

	@Autowired
	private GroupMaker groupMaker;

	private final GeneratorDataRepository dataRepository;

	public GeneratorDataService(GeneratorDataRepository dataRepository) {
		this.dataRepository = dataRepository;
	}

	public void createStudent() {
		int i = 0;

		for (String s : studentMaker.generateStudents(studentMaker.generateNames(20),
				studentMaker.generateSurnames(20))) {
			Student student = new Student(groupMaker.assignGroupId().get(i++), s.substring(0, s.indexOf(" ")),
					s.substring(s.indexOf(" ")));
			dataRepository.createStudents(student);
		}
	}

	public void createGroup() {
		for (String s : groupMaker.generateGroups()) {
			Group group = new Group(s);
			dataRepository.createGroups(group);
		}
	}

	public void createCourse() {
		for (String s : courseMaker.generateCourses()) {
			Course course = new Course(s, "TBD");
			dataRepository.createCourses(course);
		}
	}

	public void createCourseStudentRelation() {
		for (Map.Entry<Integer, Set<Integer>> entry : courseMaker.assignCourseId().entrySet()) {
			Integer key = entry.getKey();
			Set<Integer> value = entry.getValue();

			for (Integer i : value) {
				StudentCourseRelation scRelation = new StudentCourseRelation(key, i);
				dataRepository.createCourseStudentRelations(scRelation);
			}
		}
	}

	public boolean databaseIsEmpty() {
		return dataRepository.rowsCount() == 0;
	}

}
