package ua.foxminded.springdatajpa.school.mockito.daoservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
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

//@SpringBootTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//class JPAStudentServiceMockitoTest {
//
//  @Autowired
//  private JPAStudentService studentService;
//
//  @MockBean
//  private JPAStudentDao studentDAO;
//
//  @MockBean
//  private SchoolManager schoolManager;
//
//  private AutoCloseable closeable;
//
//  @BeforeEach
//  public void open() {
//    closeable = MockitoAnnotations.openMocks(this);
//  }
//
//  @AfterEach
//  public void release() throws Exception {
//    closeable.close();
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "Art, 7, Jack, Sparrow", "Sports, 4, Sasha, Burdin", "History, 3, Jinny, Wesley" })
//  void shouldFindStudentsRelatedToCourse(String courseName, int groupId, String name, String surname) {
//    Student student = new Student(groupId, name, surname);
//    List<Student> students = Collections.singletonList(student);
//    when(studentDAO.findStudentsRelatedToCourse(courseName)).thenReturn(students);
//    List<Student> actual = studentService.findStudentsRelatedToCourse(courseName);
//
//    Assertions.assertTrue(!actual.isEmpty());
//    Assertions.assertNotNull(student.getGroupId());
//    Assertions.assertNotNull(student.getFirstName());
//    Assertions.assertNotNull(student.getLastName());
//    Assertions.assertEquals(students, actual);
//    verify(studentDAO, Mockito.times(1)).findStudentsRelatedToCourse(courseName);
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "12, Art", "190, Literature", "19, Computer Science", "21, Geography", "193, Physical Science",
//      "1, Life Science", "9, English", "2, Mathematics", "150, Sports", "7, History" })
//  void shouldAddStudentToTheCourse(int studentId, String courseName) {
//    Integer expectedCount = 1;
//    when(studentDAO.addStudentToTheCourse(studentId, courseName)).thenReturn(expectedCount);
//    Integer actualCount = studentService.addStudentToTheCourse(studentId, courseName);
//
//    Assertions.assertNotNull(actualCount);
//    Assertions.assertEquals(expectedCount, actualCount);
//    verify(studentDAO).addStudentToTheCourse(studentId, courseName);
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "1, Harry, Potter", "1, Ron, Wesley", "1, Herminone, Granger", "4, Draco, Malfoy " })
//  void shouldAddNewStudent(int groupId, String name, String surname) {
//    Integer expectedCount = 1;
//    Student student = new Student(groupId, name, surname);
//    when(studentDAO.addNewStudent(any(Student.class))).thenReturn(expectedCount);
//    Integer actualCount = studentService.addNewStudent(student);
//
//    Assertions.assertNotNull(student.getGroupId());
//    Assertions.assertNotNull(student.getFirstName());
//    Assertions.assertNotNull(student.getLastName());
//    Assertions.assertEquals(expectedCount, actualCount);
//    verify(studentDAO).addNewStudent(any(Student.class));
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "1", "2", "3" })
//  void shouldDeleteStudentByID(int studentId) {
//    Integer expectedCount = 1;
//    when(studentDAO.deleteStudentByID(studentId)).thenReturn(expectedCount);
//    Integer actualCount = studentService.deleteStudentByID(studentId);
//
//    Assertions.assertNotNull(actualCount);
//    Assertions.assertEquals(expectedCount, actualCount);
//    verify(studentDAO).deleteStudentByID(studentId);
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "1, Harry, Potter", "1, Ron, Wesley", "1, Herminone, Granger", "4, Draco, Malfoy " })
//  void shouldReturnAllStudentsID(int groupId, String name, String surname) {
//    List<Integer> expected = new ArrayList<>();
//    Student student = new Student(groupId, name, surname);
//    expected.add(student.getId());
//    when(studentDAO.getStudentID()).thenReturn(expected);
//    List<Integer> actual = studentService.getStudentID();
//
//    Assertions.assertTrue(!expected.isEmpty() && !actual.isEmpty());
//    Assertions.assertEquals(expected, actual);
//    verify(studentDAO).getStudentID();
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "12, Art", "190, Literature", "19, Computer Science", "21, Geography", "193, Physical Science",
//      "1, Life Science", "9, English", "2, Mathematics", "150, Sports", "7, History" })
//  void shouldRemoveStudentFromCourse(int studentId, String courseName) {
//    Integer expectedCount = 1;
//    when(studentDAO.removeStudentFromCourse(studentId, courseName)).thenReturn(expectedCount);
//    Integer actualCount = studentService.removeStudentFromCourse(studentId, courseName);
//
//    Assertions.assertNotNull(actualCount);
//    Assertions.assertEquals(expectedCount, actualCount);
//    verify(studentDAO).removeStudentFromCourse(studentId, courseName);
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "1, 2, James, Potter", "3, 1, Fred, Wesley", "9, 1, Bartey, Krauch" })
//  void shouldUpdateStudentById(int updateId, int groupId, String name, String surname) {
//    Integer expectedCount = 1;
//    Student studentUpdate = new Student(groupId, name, surname);
//    when(studentDAO.updateStudentById(updateId, studentUpdate)).thenReturn(expectedCount);
//    Integer actualCount = studentService.updateStudentById(updateId, studentUpdate);
//
//    Assertions.assertNotNull(actualCount);
//    Assertions.assertEquals(expectedCount, actualCount);
//    verify(studentDAO).updateStudentById(updateId, studentUpdate);
//  }
//
//  @ParameterizedTest
//  @CsvSource({ "1, Harry, Potter", "1, Ron, Wesley", "1, Herminone, Granger", "4, Draco, Malfoy " })
//  void shouldShowAllStudents(int groupId, String name, String surname) {
//    List<Student> expected = new ArrayList<>();
//    Student student = new Student(groupId, name, surname);
//    expected.add(student);
//    when(studentDAO.showAllStudents()).thenReturn(expected);
//    List<Student> actual = studentService.showAllStudents();
//
//    Assertions.assertTrue(!expected.isEmpty() && !actual.isEmpty());
//    Assertions.assertNotNull(student.getGroupId());
//    Assertions.assertNotNull(student.getFirstName());
//    Assertions.assertNotNull(student.getLastName());
//    Assertions.assertEquals(expected, actual);
//    verify(studentDAO).showAllStudents();
//  }
//}
