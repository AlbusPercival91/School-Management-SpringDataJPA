package ua.foxminded.springdatajpa.school.mockito.daoservice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.springdatajpa.school.dao.interfaces.CourseRepository;
import ua.foxminded.springdatajpa.school.dao.service.CourseService;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.facade.SchoolManager;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CourseServiceMockitoTest {

	@Autowired
	private CourseService courseService;

	@MockBean
	private CourseRepository courseRepository;

	@MockBean
	private SchoolManager schoolManager;

	@ParameterizedTest
	@CsvSource({ "40, History", "100, Art", "70, Sports" })
	void shouldFindCoursesWithLessOrEqualsStudents_WhenGivenNumberOfStudents(int numberOfStudents, String courseName) {
		Course course = new Course(courseName);
		List<Course> courses = Collections.singletonList(course);
		when(courseRepository.findCoursesWithLessOrEqualsStudents(numberOfStudents)).thenReturn(courses);
		List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(numberOfStudents);

		Assertions.assertEquals(courses.size(), actual.size());
		Course actualCourse = actual.get(0);
		Assertions.assertEquals(courseName, actualCourse.getCourseName());
		verify(courseRepository, Mockito.times(1)).findCoursesWithLessOrEqualsStudents(numberOfStudents);
	}

	@ParameterizedTest
    @CsvSource({ "History", "Art", "Sports" })
    void shouldFindCoursesWithLessOrEqualsStudents_WhenStudentsZero(String courseName) {
        when(courseRepository.findCoursesWithLessOrEqualsStudents(0)).thenReturn(Collections.emptyList());
        List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(0);

        Assertions.assertTrue(actual.isEmpty());
        verify(courseRepository, Mockito.times(1)).findCoursesWithLessOrEqualsStudents(0);
    }

	@ParameterizedTest
	@CsvSource({ "1, History, TBD", "2, Art, TBD", "3, Sports, TBD", "4, English, TBD", "5, 123, TBD", "6, %$#, TBD",
			"7, !@-@$, )&-%^" })
	void shouldCreateCourse(int id, String courseName, String courseDescription) {
		Course course = new Course(courseName, courseDescription);
		course.setId(id);
		when(courseRepository.save(course)).thenReturn(course);
		Integer actualCourseId = courseService.createCourse(course);

		Assertions.assertNotNull(course.getCourseName());
		Assertions.assertNotNull(course.getCourseDescription());
		Assertions.assertEquals(course.getId(), actualCourseId);
		verify(courseRepository).save(course);
	}

	@ParameterizedTest
	@CsvSource({ "1, History, TBD, Geography, TBD-2", "2, Art, TBD, Paint, TBD-3", "3, Sports, TBD, Yoga, TBD-5",
			"4, English, TBD, Spanish, TBD-6", "5, 123, TBD, 321, asdf" })
	void shouldEditCourseNameAndDescription(int id, String courseName, String courseDescription, String newCourseName,
			String newCourseDescription) {
		Course course = new Course(courseName, courseDescription);
		course.setId(id);
		Course expectedCourse = new Course(newCourseName, newCourseDescription);
		expectedCourse.setId(id);
		when(courseRepository.save(course)).thenReturn(course);
		when(courseRepository.findByCourseName(courseName)).thenReturn(Optional.of(course));
		when(courseRepository.editCourseNameAndDescription(courseName, newCourseName, newCourseDescription))
				.thenReturn(expectedCourse);
		Course actualCourse = courseService.editCourseNameAndDescription(courseName, newCourseName,
				newCourseDescription);

		Assertions.assertNotNull(expectedCourse);
		Assertions.assertEquals(expectedCourse.toString(), actualCourse.toString());
		verify(courseRepository).save(course);
		verify(courseRepository).findByCourseName(courseName);
	}

	@ParameterizedTest
	@CsvSource({ "History", "Swimming", "Paint", "Spanish", "Geography" })
	void shouldDeleteCourseByName(String courseName) {
		Integer expectedCount = 1;
		when(courseRepository.deleteCourseByName(courseName)).thenReturn(expectedCount);
		Integer actualCount = courseService.deleteCourseByName(courseName);

		Assertions.assertEquals(expectedCount, actualCount);
		verify(courseRepository, Mockito.times(1)).deleteCourseByName(courseName);
	}

	@ParameterizedTest
	@CsvSource({ "Ecomomic", "Swimming", "Paint", "Spanish", "Geography" })
	void shouldShowAllCourses(String courseName) {
		Course course = new Course(courseName);
		List<Course> courses = Collections.singletonList(course);
		when(courseRepository.findAll()).thenReturn(courses);
		List<Course> actual = courseService.showAllCourses();

		Assertions.assertTrue(!courses.isEmpty() && !actual.isEmpty());
		Assertions.assertNotNull(course.getCourseName());
		Assertions.assertEquals(courses, actual);
	}
}
