package ua.foxminded.springdatajpa.school.mockito.testservice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.springdatajpa.school.dao.interfaces.GeneratorDataRepository;
import ua.foxminded.springdatajpa.school.dao.testdata.GeneratorDataService;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;
import ua.foxminded.springdatajpa.school.facade.SchoolManager;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GeneratorDataServiceMockitoTest {

	@Autowired
	private GeneratorDataService service;

	@MockBean
	private GeneratorDataRepository repository;

	@MockBean
	private SchoolManager schoolManager;

	@Captor
	private ArgumentCaptor<Course> courseCaptor;

	@Captor
	private ArgumentCaptor<Student> studentCaptor;

	@Captor
	private ArgumentCaptor<Group> groupCaptor;

	@Captor
	private ArgumentCaptor<StudentCourseRelation> relationCaptor;

	@Test
	void shouldCreateStudent() {
		service.createStudent();
		verify(repository, Mockito.times(200)).createStudents(studentCaptor.capture());
		List<Student> capturedStudents = studentCaptor.getAllValues();
		Assertions.assertEquals(200, capturedStudents.size());
		capturedStudents.forEach(student -> Assertions.assertNotNull(student.getFirstName()));
	}

	@Test
	void shouldCreateGroup() {
		service.createGroup();
		verify(repository, Mockito.times(10)).createGroups(groupCaptor.capture());
		List<Group> capturedGroups = groupCaptor.getAllValues();
		Assertions.assertEquals(10, capturedGroups.size());
		capturedGroups.forEach(group -> Assertions.assertNotNull(group.getGroupName()));
	}

	@Test
	void shouldCreateCourse() {
		service.createCourse();
		verify(repository, Mockito.times(10)).createCourses(courseCaptor.capture());
		List<Course> capturedCourses = courseCaptor.getAllValues();
		Assertions.assertEquals(10, capturedCourses.size());
		capturedCourses.forEach(course -> Assertions.assertNotNull(course.getCourseName()));
	}

	@Test
	void shouldCreateCourseStudentRelation() {
		service.createCourseStudentRelation();
		verify(repository, Mockito.atLeast(200)).createCourseStudentRelations(relationCaptor.capture());
		List<StudentCourseRelation> capturedRelations = relationCaptor.getAllValues();
		Assertions.assertTrue(capturedRelations.size() >= 200);
		capturedRelations.forEach(relation -> {
			Assertions.assertNotNull(Integer.valueOf(relation.getStudentId()), "Student ID should not be null");
			Assertions.assertNotNull(Integer.valueOf(relation.getCourseId()), "Course ID should not be null");
		});
	}

	@Test
  void shouldGiveRowsCount() {
    when(repository.rowsCount()).thenReturn(100);
    boolean isEmpty = service.databaseIsEmpty();
    verify(repository, Mockito.times(1)).rowsCount();
    Assertions.assertFalse(isEmpty);
  }
}
