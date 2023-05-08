package ua.foxminded.hibernate.school.dao.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.*;
import ua.foxminded.hibernate.school.dao.interfaces.CourseRepository;
import ua.foxminded.hibernate.school.dao.interfaces.GroupRepository;
import ua.foxminded.hibernate.school.dao.interfaces.StudentCourseRepository;
import ua.foxminded.hibernate.school.dao.interfaces.StudentRepository;
import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataDao;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;
import ua.foxminded.hibernate.school.testdata.CourseMaker;
import ua.foxminded.hibernate.school.testdata.GroupMaker;
import ua.foxminded.hibernate.school.testdata.StudentMaker;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		JPAStudentService.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JPAStudentDaoTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	private TestDataGenerator testData;

	@BeforeEach
	void setUp() {
		testData = new TestDataGenerator(new StudentMaker(), new GroupMaker(), new CourseMaker(),
				new JPAGeneratorDataDao(studentRepository, groupRepository, courseRepository, studentCourseRepository));
	}

	@Test
	void testFindStudentsRelatedToCourse_ShouldReturnNameOfCourses() {
		testData.createCourse();
		testData.createStudent();
		testData.createCourseStudentRelation();

		for (String s : new CourseMaker().generateCourses()) {
			long studentCount = studentRepository.findStudentsRelatedToCourse(s).stream()
					.filter(course -> course.toString().trim().contains(" ")).count();
			Assertions.assertTrue(studentCount > 0);
		}
	}

	@ParameterizedTest
	@CsvSource({ "12, Art", "190, Literature", "19, Computer Science", "21, Geography", "193, Physical Science",
			"1, Life Science", "9, English", "2, Mathematics", "150, Sports", "7, History" })
	void testAddStudentToTheCourse_ShouldReturnOneIfStudentAssigned(int studentId, String course) {
		testData.createCourse();
		testData.createStudent();

		Assertions.assertEquals(1, studentRepository.addStudentToTheCourse(studentId, course));
	}

	@Test
	void testAddNewStudent_ShouldReturnEqualsWhenNewStudentCreated() {
		Student student = new Student(4, "Harry", "Potter");
		studentRepository.save(student);
		List<Student> actual = studentRepository.findAll();

		Assertions.assertEquals(student, actual.get(0));
	}

	@Test
	void testDeleteStudentByID_ShouldReturnOneIfStudentRemoved() {
		Student student = new Student(1, "Albus", "Dambldor");
		studentRepository.save(student);
		Assertions.assertEquals(1, studentRepository.findAll().size());

		studentRepository.deleteById(student.getId());
		Assertions.assertTrue(studentRepository.findAll().isEmpty());
	}

	void testGetStudentID_ShouldReturnAllStudentsID() {
		testData.createStudent();
		List<Integer> actual = studentRepository.getStudentID();

		Assertions.assertEquals(200, actual.size());
	}

	@Test
	void testRemoveStudentFromCourse_ShouldReturnOneIfStudentRemovedFromCourse() {
		testData.createCourse();
		testData.createStudent();
		Optional<Student> student = studentRepository.findById(17);
		Optional<Course> course = courseRepository.findById(3);
		StudentCourseRelation relation = new StudentCourseRelation(student.get().getId(), course.get().getId());
		studentCourseRepository.save(relation);
		studentCourseRepository.flush();
		int deleted = studentRepository.removeStudentFromCourse(student.get().getId(), course.get().getCourseName());

		Assertions.assertEquals(1, deleted);
	}

	@ParameterizedTest
	@CsvSource({ "4, Harry, Potter, 3, Ron, Wesley", "6, Draco, Malfoy, 7, Hermione, Granger" })
	void testUpdateStudentById_ShouldReturnEqualsStringsWhenStudentUpdated(int groupId, String firstName,
			String lastName, int newGroupId, String newName, String newLastName) {
		Student student = new Student(groupId, firstName, lastName);
		studentRepository.save(student);

		studentRepository.flush();

		Student updatedStudent = new Student(newGroupId, newName, newLastName);
		studentRepository.updateStudentById(student.getId(), updatedStudent);

		Optional<Student> actualStudent = studentRepository.findById(student.getId());
		String actual = actualStudent.get().getId() + " " + actualStudent.get().getGroupId() + " "
				+ actualStudent.get().getFirstName() + " " + actualStudent.get().getLastName();
		String expected = student.getId() + " " + updatedStudent.getGroupId() + " " + updatedStudent.getFirstName()
				+ " " + updatedStudent.getLastName();

		Assertions.assertEquals(expected, actual);
	}

	@Test
	void testShowAllStudents_ShouldReturnAllStudents() {
		testData.createStudent();
		List<Student> actual = studentRepository.findAll();

		Assertions.assertEquals(200, actual.size());
	}
}
