package ua.foxminded.hibernate.school.dao;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.hibernate.school.dao.interfaces.CourseDao;
import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataDao;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.testdata.CourseMaker;
import ua.foxminded.hibernate.school.testdata.GroupMaker;
import ua.foxminded.hibernate.school.testdata.StudentMaker;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
    JPACourseDao.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JPACourseDaoTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CourseDao courseDao;

  private TestDataGenerator testData;

  @BeforeEach
  void setUp() {
    testData = new TestDataGenerator(new StudentMaker(), new GroupMaker(), new CourseMaker(),
        new JPAGeneratorDataDao(entityManager.getEntityManager()));
  }

  @ParameterizedTest
  @CsvSource({ "40", "100", "70" })
  void testFindCoursesWithLessOrEqualsStudents_ShouldReturnCoursesWithLessOrEqualsStudents(int number) {
    testData.createStudent();
    testData.createCourse();
    testData.createCourseStudentRelation();
    List<Course> actual = courseDao.findCoursesWithLessOrEqualsStudents(number);
    Assertions.assertNotNull(actual);

    Assertions.assertTrue(actual.size() > 0);
  }

  @Test
  void testFindCoursesWithLessOrEqualsStudents_WhenStudentsZero_ShouldReturnEmptyList() {
    testData.createStudent();
    testData.createCourse();
    testData.createCourseStudentRelation();
    List<Course> actual = courseDao.findCoursesWithLessOrEqualsStudents(0);

    Assertions.assertTrue(actual.isEmpty());
  }

  @ParameterizedTest
  @CsvSource({ "History, TBD, Geography, TBD-2", "Art, TBD, Paint, TBD-3", "Sports, TBD, Yoga, TBD-5",
      "English, TBD, Spanish, TBD-6", "123, TBD, 321, asdf", "%$#, TBD, $%^&, TBDTBD", "!@-@$, )&-%^, Swimming, TBD" })
  void testEditCourseNameAndDescription_ShouldReturnOneIfCourseUpdated(String courseName, String courseDescription,
      String newCourseName, String newCourseDescription) {
    Course course = new Course(courseName, courseDescription);
    courseDao.createCourse(course);

    Assertions.assertEquals(1,
        courseDao.editCourseNameAndDescription(course.getCourseName(), newCourseName, newCourseDescription));
  }

  @ParameterizedTest
  @DisplayName("Should return 1 if 1 course deleted")
  @CsvSource({ "History, TBD", "Swimming, TBD", "Paint, TBD-3", "Spanish, TBD-6", "Geography, TBD-2" })
  void testDeleteCourseByName_ShouldReturnOneIfCourseDeleted(String courseName, String courseDescription) {
    Course course = new Course(courseName, courseDescription);
    courseDao.createCourse(course);

    Assertions.assertEquals(1, courseDao.deleteCourseByName(course.getCourseName()));
  }

  @Test
  @DisplayName("Should return 10 when initiated course test data")
  void testShowAllCourses_ShouldReturnAllCourses() {
    testData.createCourse();
    List<Course> actual = courseDao.showAllCourses();

    Assertions.assertEquals(10, actual.size());
  }

}
