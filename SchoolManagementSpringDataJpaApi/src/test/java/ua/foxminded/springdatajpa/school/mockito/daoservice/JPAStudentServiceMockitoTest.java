package ua.foxminded.springdatajpa.school.mockito.daoservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.springdatajpa.school.dao.interfaces.StudentRepository;
import ua.foxminded.springdatajpa.school.dao.service.StudentService;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.facade.SchoolManager;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JPAStudentServiceMockitoTest {

	@Autowired
	private StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	@MockBean
	private SchoolManager schoolManager;

	@ParameterizedTest
	@CsvSource({ "Art, 7, Jack, Sparrow", "Sports, 4, Sasha, Burdin", "History, 3, Jinny, Wesley" })
	void shouldFindStudentsRelatedToCourse(String courseName, int groupId, String name, String surname) {
		Student student = new Student(groupId, name, surname);
		List<Student> students = Collections.singletonList(student);
		when(studentRepository.findStudentsRelatedToCourse(courseName)).thenReturn(students);
		List<Student> actual = studentService.findStudentsRelatedToCourse(courseName);

		Assertions.assertTrue(!actual.isEmpty());
		Assertions.assertNotNull(student.getGroupId());
		Assertions.assertNotNull(student.getFirstName());
		Assertions.assertNotNull(student.getLastName());
		Assertions.assertEquals(students, actual);
		verify(studentRepository, Mockito.times(1)).findStudentsRelatedToCourse(courseName);
	}

	@ParameterizedTest
	@CsvSource({ "12, Art", "190, Literature", "19, Computer Science", "21, Geography", "193, Physical Science",
			"1, Life Science", "9, English", "2, Mathematics", "150, Sports", "7, History" })
	void shouldAddStudentToTheCourse(int studentId, String courseName) {
		Integer expectedCount = 1;
		when(studentRepository.addStudentToTheCourse(studentId, courseName)).thenReturn(expectedCount);
		Integer actualCount = studentService.addStudentToTheCourse(studentId, courseName);

		Assertions.assertNotNull(actualCount);
		Assertions.assertEquals(expectedCount, actualCount);
		verify(studentRepository).addStudentToTheCourse(studentId, courseName);
	}

	@ParameterizedTest
	@CsvSource({ "1, 1, Harry, Potter", "2, 1, Ron, Wesley", "3, 1, Herminone, Granger", "4, 4, Draco, Malfoy " })
	void shouldAddNewStudent(int id, int groupId, String name, String surname) {
		Student student = new Student(groupId, name, surname);
		student.setId(id);
		when(studentRepository.save(student)).thenReturn(student);
		Integer actualId = studentService.addNewStudent(student);

		Assertions.assertNotNull(student.getGroupId());
		Assertions.assertNotNull(student.getFirstName());
		Assertions.assertNotNull(student.getLastName());
		Assertions.assertEquals(student.getId(), actualId);
		verify(studentRepository).save(student);
	}

//	@ParameterizedTest
//	@CsvSource({ "1, 1, Harry, Potter", "2, 1, Ron, Wesley", "3, 1, Herminone, Granger", "4, 4, Draco, Malfoy " })
//	void shouldDeleteStudentByID(int id, int groupId, String name, String surname) {
//		Student student = new Student(groupId, name, surname);
//		student.setId(id);
//		when(studentRepository.save(student)).thenReturn(student);
//		doNothing().when(studentRepository).deleteById(id);
//		Integer removeId = studentService.deleteStudentByID(id);
//
//		Assertions.assertNotNull(removeId);
//		Assertions.assertEquals(student.getId(), removeId);
//		verify(studentRepository).deleteById(id);
//	}

	@ParameterizedTest
	@CsvSource({ "1, Harry, Potter", "1, Ron, Wesley", "1, Herminone, Granger", "4, Draco, Malfoy " })
	void shouldReturnAllStudentsID(int groupId, String name, String surname) {
		List<Integer> expected = new ArrayList<>();
		Student student = new Student(groupId, name, surname);
		expected.add(student.getId());
		when(studentRepository.getStudentID()).thenReturn(expected);
		List<Integer> actual = studentService.getStudentID();

		Assertions.assertTrue(!expected.isEmpty() && !actual.isEmpty());
		Assertions.assertEquals(expected, actual);
		verify(studentRepository).getStudentID();
	}

	@ParameterizedTest
	@CsvSource({ "12, Art", "190, Literature", "19, Computer Science", "21, Geography", "193, Physical Science",
			"1, Life Science", "9, English", "2, Mathematics", "150, Sports", "7, History" })
	void shouldRemoveStudentFromCourse(int studentId, String courseName) {
		Integer expectedCount = 1;
		when(studentRepository.removeStudentFromCourse(studentId, courseName)).thenReturn(expectedCount);
		Integer actualCount = studentService.removeStudentFromCourse(studentId, courseName);

		Assertions.assertNotNull(actualCount);
		Assertions.assertEquals(expectedCount, actualCount);
		verify(studentRepository).removeStudentFromCourse(studentId, courseName);
	}

	@ParameterizedTest
	@CsvSource({ "1, 2, James, Potter, 3, Lara, Petrow", "3, 1, Fred, Wesley, 2, Jorge, Wesley",
			"9, 1, Bartey, Krauch, 4, Frodo, Beggins" })
	void shouldUpdateStudentById(int id, int groupId, String name, String surname, int newGroupId, String newName,
			String newSurname) {
		Student student = new Student(groupId, name, surname);
		student.setId(id);
		Student studentUpdate = new Student(newGroupId, newName, newSurname);
		studentUpdate.setId(id);
		when(studentRepository.findById(id)).thenReturn(Optional.of(student));
		when(studentRepository.save(student)).thenReturn(student);
		Integer actualId = studentService.updateStudentById(id, student);

		Assertions.assertNotNull(actualId);
		Assertions.assertEquals(student.getId(), actualId);
		verify(studentRepository).findById(id);
		verify(studentRepository).save(student);
	}

	@ParameterizedTest
	@CsvSource({ "1, Harry, Potter", "1, Ron, Wesley", "1, Herminone, Granger", "4, Draco, Malfoy " })
	void shouldShowAllStudents(int groupId, String name, String surname) {
		List<Student> expected = new ArrayList<>();
		Student student = new Student(groupId, name, surname);
		expected.add(student);
		when(studentRepository.findAll()).thenReturn(expected);
		List<Student> actual = studentService.showAllStudents();

		Assertions.assertTrue(!expected.isEmpty() && !actual.isEmpty());
		Assertions.assertNotNull(student.getGroupId());
		Assertions.assertNotNull(student.getFirstName());
		Assertions.assertNotNull(student.getLastName());
		Assertions.assertEquals(expected, actual);
	}
}
