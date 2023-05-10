package ua.foxminded.springdatajpa.school.dao.interfaces;

import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;

public interface GeneratorDataRepository {

	void createStudent(Student student);

	void createGroup(Group group);

	void createCourse(Course course);

	void createCourseStudentRelation(StudentCourseRelation scRelation);

	int rowsCount();
}
