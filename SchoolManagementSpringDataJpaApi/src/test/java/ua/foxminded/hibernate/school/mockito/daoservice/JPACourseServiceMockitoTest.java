package ua.foxminded.hibernate.school.mockito.daoservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.hibernate.school.dao.JPACourseDao;
import ua.foxminded.hibernate.school.dao.JPACourseService;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.facade.SchoolManager;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JPACourseServiceMockitoTest {

  @Autowired
  private JPACourseService courseService;

  @MockBean
  private JPACourseDao courseDAO;

  @MockBean
  private SchoolManager schoolManager;

  private AutoCloseable closeable;

  @BeforeEach
  public void open() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void release() throws Exception {
    closeable.close();
  }

  @ParameterizedTest
  @CsvSource({ "40, History", "100, Art", "70, Sports" })
  void shouldFindCoursesWithLessOrEqualsStudents_WhenGivenNumberOfStudents(int numberOfStudents, String courseName) {
    Course course = new Course(courseName);
    List<Course> courses = Collections.singletonList(course);
    when(courseDAO.findCoursesWithLessOrEqualsStudents(numberOfStudents)).thenReturn(courses);
    List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(numberOfStudents);

    Assertions.assertEquals(courses.size(), actual.size(), "Expected number of courses to match actual");
    Course actualCourse = actual.get(0);
    Assertions.assertEquals(courseName, actualCourse.getCourseName(), "Expected course name to match actual");
    verify(courseDAO, Mockito.times(1)).findCoursesWithLessOrEqualsStudents(numberOfStudents);
  }

  @ParameterizedTest
  @CsvSource({ "History", "Art", "Sports" })
  void shouldFindCoursesWithLessOrEqualsStudents_WhenStudentsZero(String courseName) {
    when(courseDAO.findCoursesWithLessOrEqualsStudents(0)).thenReturn(Collections.emptyList());
    List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(0);

    Assertions.assertTrue(actual.isEmpty());
    verify(courseDAO, Mockito.times(1)).findCoursesWithLessOrEqualsStudents(0);
  }

  @ParameterizedTest
  @CsvSource({ "History, TBD", "Art, TBD", "Sports, TBD", "English, TBD", "123, TBD", "%$#, TBD", "!@-@$, )&-%^" })
  void shouldCreateCourse(String courseName, String courseDescription) {
    Integer expectedCount = 1;
    Course course = new Course(courseName, courseDescription);
    when(courseDAO.createCourse(any(Course.class))).thenReturn(expectedCount);
    Integer actualCount = courseService.createCourse(course);

    Assertions.assertNotNull(course.getCourseName());
    Assertions.assertNotNull(course.getCourseDescription());
    Assertions.assertEquals(expectedCount, actualCount);
    verify(courseDAO).createCourse(any(Course.class));
  }

  @ParameterizedTest
  @CsvSource({ "History, TBD, Geography, TBD-2", "Art, TBD, Paint, TBD-3", "Sports, TBD, Yoga, TBD-5",
      "English, TBD, Spanish, TBD-6", "123, TBD, 321, asdf", "%$#, TBD, $%^&, TBDTBD", "!@-@$, )&-%^, Swimming, TBD" })
  void shouldEditCourseNameAndDescription(String courseName, String courseDescription, String newCourseName,
      String newCourseDescription) {
    Integer expectedCount = 1;
    when(courseDAO.editCourseNameAndDescription(courseName, newCourseName, newCourseDescription))
        .thenReturn(expectedCount);
    Integer actualCount = courseService.editCourseNameAndDescription(courseName, newCourseName, newCourseDescription);

    Assertions.assertNotNull(actualCount);
    Assertions.assertEquals(expectedCount, actualCount);
    verify(courseDAO).editCourseNameAndDescription(courseName, newCourseName, newCourseDescription);
  }

  @ParameterizedTest
  @CsvSource({ "History", "Swimming", "Paint", "Spanish", "Geography" })
  void shouldDeleteCourseByName(String courseName) {
    Integer expectedCount = 1;
    when(courseDAO.deleteCourseByName(courseName)).thenReturn(expectedCount);
    Integer actualCount = courseService.deleteCourseByName(courseName);

    Assertions.assertEquals(expectedCount, actualCount);
    verify(courseDAO).deleteCourseByName(courseName);
  }

  @ParameterizedTest
  @CsvSource({ "History", "Swimming", "Paint", "Spanish", "Geography" })
  void shouldShowAllCourses(String courseName) {
    Course course = new Course(courseName);
    List<Course> courses = Collections.singletonList(course);
    when(courseDAO.showAllCourses()).thenReturn(courses);
    List<Course> actual = courseService.showAllCourses();

    Assertions.assertTrue(!courses.isEmpty() && !actual.isEmpty());
    Assertions.assertNotNull(course.getCourseName());
    Assertions.assertEquals(courses, actual);
    verify(courseDAO).showAllCourses();
  }
}
