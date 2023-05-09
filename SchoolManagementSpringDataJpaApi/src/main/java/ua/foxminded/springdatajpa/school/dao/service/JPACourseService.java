package ua.foxminded.springdatajpa.school.dao.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.springdatajpa.school.dao.interfaces.CourseRepository;
import ua.foxminded.springdatajpa.school.entity.Course;

@Service
@Transactional
public class JPACourseService {
	private final CourseRepository courseRepository;

	public JPACourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public Optional<Course> findCoursesWithLessOrEqualsStudents(Integer students) {
		return courseRepository.findCoursesWithLessOrEqualsStudents(students);
	}

	public int createCourse(Course course) {
		Course savedCourse = courseRepository.save(course);
		return savedCourse.getId();
	}

	public int editCourseNameAndDescription(String courseName, String newCourseName, String newDescription) {
		Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
		if (optionalCourse.isEmpty()) {
			throw new IllegalArgumentException("Course not found");
		}
		Course course = optionalCourse.get();
		course.setCourseName(newCourseName);
		course.setCourseDescription(newDescription);
		Course savedCourse = courseRepository.save(course);
		return savedCourse.getId();
	}

	public int deleteCourseByName(String courseName) {
		return courseRepository.deleteCourseByName(courseName);
	}

	public List<Course> showAllCourses() {
		return courseRepository.findAll();
	}
}
