package ua.foxminded.springdatajpa.school.dao.service;

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
import ua.foxminded.springdatajpa.school.dao.interfaces.CourseRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.GroupRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.StudentCourseRepository;
import ua.foxminded.springdatajpa.school.dao.interfaces.StudentRepository;
import ua.foxminded.springdatajpa.school.dao.testdata.GeneratorDataDao;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;
import ua.foxminded.springdatajpa.school.testdata.CourseMaker;
import ua.foxminded.springdatajpa.school.testdata.GroupMaker;
import ua.foxminded.springdatajpa.school.testdata.StudentMaker;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		StudentService.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudentServiceTest {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private StudentCourseRepository studentCourseRepository;

	@Autowired
	private StudentService studentService;

	private TestDataGenerator testData;

	@BeforeEach
	void setUp() {
		testData = new TestDataGenerator(new StudentMaker(), new GroupMaker(), new CourseMaker(),
				new GeneratorDataDao(studentRepository, groupRepository, courseRepository, studentCourseRepository));
	}

	@Test
	void testFindStudentsRelatedToCourse_ShouldReturnNameOfCourses() {
		testData.createCourse();
		testData.createStudent();
		testData.createCourseStudentRelation();

		for (String s : new CourseMaker().generateCourses()) {
			long studentCount = studentService.findStudentsRelatedToCourse(s).stream()
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

		Assertions.assertEquals(1, studentService.addStudentToTheCourse(studentId, course));
	}

	@Test
	void testAddNewStudent_ShouldReturnEqualsWhenNewStudentCreated() {
		Student student = new Student(4, "Harry", "Potter");
		studentRepository.save(student);
		List<Student> actual = studentService.showAllStudents();

		Assertions.assertEquals(student, actual.get(0));
	}

	@Test
	void testDeleteStudentByID_ShouldReturnOneIfStudentRemoved() {
		Student student = new Student(1, "Albus", "Dambldor");
		studentRepository.save(student);
		Assertions.assertEquals(1, studentService.deleteStudentByID(student.getId()));
		Assertions.assertTrue(studentService.showAllStudents().isEmpty());
	}

	void testGetStudentID_ShouldReturnAllStudentsID() {
		testData.createStudent();
		List<Integer> actual = studentService.getStudentID();

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
		int deleted = studentService.removeStudentFromCourse(student.get().getId(), course.get().getCourseName());

		Assertions.assertEquals(1, deleted);
	}

	@ParameterizedTest
	@CsvSource({ "4, Harry, Potter, 3, Ron, Wesley", "6, Draco, Malfoy, 7, Hermione, Granger" })
	void testUpdateStudentById_ShouldReturnEqualsStringsWhenStudentUpdated(int groupId, String firstName,
			String lastName, int newGroupId, String newName, String newLastName) {
		Student student = new Student(groupId, firstName, lastName);
		studentService.addNewStudent(student);

		Student updatedStudent = new Student(newGroupId, newName, newLastName);
		studentService.updateStudentById(student.getId(), updatedStudent);

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
		List<Student> actual = studentService.showAllStudents();

		Assertions.assertEquals(200, actual.size());
	}
}
