package ua.foxminded.hibernate.school.dao.interfaces;

import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;

public interface GeneratorDataDao {

  void createStudent(Student student);

  void createGroup(Group group);

  void createCourse(Course course);

  void createCourseStudentRelation(StudentCourseRelation scRelation);

  int rowsCount();
}
