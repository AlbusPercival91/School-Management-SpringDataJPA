package ua.foxminded.springdatajpa.school.dao.interfaces;

import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;

public interface GeneratorDataRepository {

	void createStudents(Student student);

	void createGroups(Group group);

	void createCourses(Course course);

	void createCourseStudentRelations(StudentCourseRelation scRelation);

	int rowsCount();
}
