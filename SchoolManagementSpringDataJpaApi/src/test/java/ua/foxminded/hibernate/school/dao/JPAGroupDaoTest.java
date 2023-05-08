package ua.foxminded.hibernate.school.dao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.testdata.CourseMaker;
import ua.foxminded.hibernate.school.testdata.GroupMaker;
import ua.foxminded.hibernate.school.testdata.StudentMaker;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
		JPAGroupService.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JPAGroupDaoTest {

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

	@ParameterizedTest
	@CsvSource({ "30", "100" })
	void testFindGroupsWithLessOrEqualsStudents_ShouldReturnGroupsWhenMatchedLessOrEqual(int number) {
		testData.createGroup();
		testData.createStudent();
		Pattern pattern = Pattern.compile("[a-z]{2}-[0-9]{2}");
		List<Group> actual = groupRepository.findGroupsWithLessOrEqualsStudents(number);
		int matchedPattern = (int) actual.stream().map(Group::toString).map(pattern::matcher).filter(Matcher::find)
				.count();

		Assertions.assertEquals(10, matchedPattern);
	}

	@Test
	void testFindGroupsWithLessOrEqualsStudents_WhenStudentsZero_ShouldReturnEmptyList() {
		testData.createGroup();
		testData.createStudent();
		List<Group> actual = groupRepository.findGroupsWithLessOrEqualsStudents(0);

		Assertions.assertTrue(actual.isEmpty());
	}

	@ParameterizedTest
	@CsvSource({ "aa-34, aa-35", "aa-35, 35-aa", "test, test-test", "123, 321", "aa-aa, bb-bb", "00-00, 11-11",
			"!@-@$, )&-%^" })
	void testEditGroupName_ShouldReturnOneIfGroupUpdated(Group group, Group newGroup) {
		groupRepository.save(group);
		Assertions.assertEquals(1, groupRepository.editGroupName(group.getGroupName(), newGroup.getGroupName()));
	}

	@ParameterizedTest
	@CsvSource({ "aa-34", "35-aa", "test", "123", "aa-aa", "00-00", "!@-@$" })
	void testDeleteGroupByName_ShouldReturnOneIfGroupDeleted(Group group) {
		groupRepository.save(group);
		Assertions.assertEquals(1, groupRepository.deleteGroupByName(group.getGroupName()));
	}

	@Test
	void testShowAllGroups_ShouldReturnAllGroups() {
		testData.createGroup();
		List<Group> actual = groupRepository.findAll();

		Assertions.assertEquals(10, actual.size());
	}

}
