package ua.foxminded.hibernate.school.mockito.testservice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataDao;
import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataService;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;
import ua.foxminded.hibernate.school.facade.SchoolManager;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GeneratorDataServiceMockitoTest {

  @Autowired
  private JPAGeneratorDataService service;

  @MockBean
  private JPAGeneratorDataDao repository;

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

  private AutoCloseable closeable;

  @BeforeEach
  public void open() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void release() throws Exception {
    closeable.close();
  }

  @Test
  void shouldCreateStudent() {
    service.createStudent();
    verify(repository, Mockito.times(200)).createStudent(studentCaptor.capture());
    List<Student> capturedStudents = studentCaptor.getAllValues();
    Assertions.assertEquals(200, capturedStudents.size());
    capturedStudents.forEach(student -> Assertions.assertNotNull(student.getFirstName()));
  }

  @Test
  void shouldCreateGroup() {
    service.createGroup();
    verify(repository, Mockito.times(10)).createGroup(groupCaptor.capture());
    List<Group> capturedGroups = groupCaptor.getAllValues();
    Assertions.assertEquals(10, capturedGroups.size());
    capturedGroups.forEach(group -> Assertions.assertNotNull(group.getGroupName()));
  }

  @Test
  void shouldCreateCourse() {
    service.createCourse();
    verify(repository, Mockito.times(10)).createCourse(courseCaptor.capture());
    List<Course> capturedCourses = courseCaptor.getAllValues();
    Assertions.assertEquals(10, capturedCourses.size());
    capturedCourses.forEach(course -> Assertions.assertNotNull(course.getCourseName()));
  }

  @Test
  void shouldCreateCourseStudentRelation() {
    service.createCourseStudentRelation();
    verify(repository, Mockito.atLeast(200)).createCourseStudentRelation(relationCaptor.capture());
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
