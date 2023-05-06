package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;

import ua.foxminded.hibernate.school.entity.Student;

public interface StudentDao {

  int addNewStudent(Student student);

  int deleteStudentByID(Integer id);

  List<Integer> getStudentID();

  List<Student> findStudentsRelatedToCourse(String courseName);

  int addStudentToTheCourse(Integer studentId, String courseName);

  int removeStudentFromCourse(Integer studentId, String courseName);

  int updateStudentById(Integer studentId, Student student);

  List<Student> showAllStudents();
}
