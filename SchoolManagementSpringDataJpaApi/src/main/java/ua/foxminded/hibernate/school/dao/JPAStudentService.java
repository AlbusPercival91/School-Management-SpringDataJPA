package ua.foxminded.hibernate.school.dao;

import java.util.List;
import org.springframework.stereotype.Service;

import ua.foxminded.hibernate.school.dao.interfaces.StudentRepository;
import ua.foxminded.hibernate.school.entity.Student;

@Service
public class JPAStudentService {
  private static final String WRONG = "oops something went wrong";

  private final StudentRepository studentDao;

  public JPAStudentService(StudentRepository studentDao) {
    this.studentDao = studentDao;
  }

  public List<Student> findStudentsRelatedToCourse(String courseName) {
    return studentDao.findStudentsRelatedToCourse(courseName);
  }

  public int addNewStudent(Student student) {
    int result = studentDao.addNewStudent(student);

    if (result == 0) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public int deleteStudentByID(int id) {
    return studentDao.deleteStudentByID(id);
  }

  public List<Integer> getStudentID() {
    return studentDao.getStudentID();
  }

  public int addStudentToTheCourse(Integer studentId, String courseName) {
    int result = studentDao.addStudentToTheCourse(studentId, courseName);

    if (result != 1) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public int removeStudentFromCourse(Integer studentId, String courseName) {
    return studentDao.removeStudentFromCourse(studentId, courseName);
  }

  public int updateStudentById(Integer studentId, Student student) {
    int result = studentDao.updateStudentById(studentId, student);

    if (result != 1) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public List<Student> showAllStudents() {
    return studentDao.showAllStudents();
  }

}
