package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	List<Course> findCoursesWithLessOrEqualsStudents(Integer students);

	int createCourse(Course course);

	int editCourseNameAndDescription(String courseName, String newCourseName, String newDescription);

	int deleteCourseByName(String courseName);

	List<Course> showAllCourses();
}
