package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;

import ua.foxminded.hibernate.school.entity.Course;

public interface CourseDao {

  List<Course> findCoursesWithLessOrEqualsStudents(Integer students);

  int createCourse(Course course);

  int editCourseNameAndDescription(String courseName, String newCourseName, String newDescription);

  int deleteCourseByName(String courseName);

  List<Course> showAllCourses();
}
