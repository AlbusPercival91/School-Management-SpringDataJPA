package ua.foxminded.springdatajpa.school.dao.service;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.springdatajpa.school.dao.interfaces.CourseRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.GroupRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.StudentCourseRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.StudentRepository;
import ua.foxminded.springdatajpa.school.dao.testdata.GeneratorDataDao;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.testdata.CourseMaker;
import ua.foxminded.springdatajpa.school.testdata.GroupMaker;
import ua.foxminded.springdatajpa.school.testdata.StudentMaker;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		CourseService.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CourseServiceTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Autowired
	private CourseService courseService;

	private TestDataGenerator testData;

	@BeforeEach
	void setUp() {
		testData = new TestDataGenerator(new StudentMaker(), new GroupMaker(), new CourseMaker(),
				new GeneratorDataDao(studentRepository, groupRepository, courseRepository, studentCourseRepository));
	}

	@ParameterizedTest
	@CsvSource({ "40", "100", "70" })
	void testFindCoursesWithLessOrEqualsStudents_ShouldReturnCoursesWithLessOrEqualsStudents(int number) {
		testData.createStudent();
		testData.createCourse();
		testData.createCourseStudentRelation();
		List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(number);
		Assertions.assertNotNull(actual);

		Assertions.assertTrue(actual.size() > 0);
	}

	@Test
	void testFindCoursesWithLessOrEqualsStudents_WhenStudentsZero_ShouldReturnEmptyList() {
		testData.createStudent();
		testData.createCourse();
		testData.createCourseStudentRelation();
		List<Course> actual = courseService.findCoursesWithLessOrEqualsStudents(0);

		Assertions.assertTrue(actual.isEmpty());
	}

	@ParameterizedTest
	@CsvSource({ "1, History, TBD, Geography, TBD-2", "1, Art, TBD, Paint, TBD-3", "1, Sports, TBD, Yoga, TBD-5",
			"1, English, TBD, Spanish, TBD-6", "1, 123, TBD, 321, asdf", "1, %$#, TBD, $%^&, TBDTBD",
			"1, !@-@$, )&-%^, Swimming, TBD" })
	void testEditCourseNameAndDescription_ShouldReturnNewCourseIfCourseUpdated(int id, String courseName,
			String courseDescription, String newCourseName, String newCourseDescription) {
		Course course = new Course(courseName, courseDescription);
		Course expectedCourse = new Course(newCourseName, newCourseDescription);
		expectedCourse.setId(id);
		courseService.createCourse(course);
		Course actualCourse = courseService.editCourseNameAndDescription(course.getCourseName(), newCourseName,
				newCourseDescription);
		
		Assertions.assertEquals(expectedCourse.toString(), actualCourse.toString());
	}

	@ParameterizedTest
	@DisplayName("Should return 1 if 1 course deleted")
	@CsvSource({ "History, TBD", "Swimming, TBD", "Paint, TBD-3", "Spanish, TBD-6", "Geography, TBD-2" })
	void testDeleteByCourseName_ShouldReturnOneIfCourseDeleted(String courseName, String courseDescription) {
		Course course = new Course(courseName, courseDescription);
		courseService.createCourse(course);

		Assertions.assertEquals(1, courseService.deleteByCourseName(course.getCourseName()));
	}

	@Test
	@DisplayName("Should return 10 when initiated course test data")
	void testShowAllCourses_ShouldReturnAllCourses() {
		testData.createCourse();
		List<Course> actual = courseService.showAllCourses();

		Assertions.assertEquals(10, actual.size());
	}

}
