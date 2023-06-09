package ua.foxminded.springdatajpa.school.dao.testdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ua.foxminded.springdatajpa.school.dao.interfaces.GeneratorDataRepository;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
    GeneratorDataDao.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test-container")
@Sql(scripts = { "/drop_data.sql", "/init_tables.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JPAGeneratorDataDaoTest {

  @Autowired
  private GeneratorDataRepository generatorDataDao;

  @Test
  void testCreateStudent() {
    Student student = new Student(1, "Harry", "Potter");
    generatorDataDao.createStudents(student);
    Assertions.assertEquals(1, generatorDataDao.rowsCount());
  }

  @Test
  void testCreateGroup() {
    Group group = new Group("Gryffindor");
    generatorDataDao.createGroups(group);
    Assertions.assertEquals(1, generatorDataDao.rowsCount());
  }

  @Test
  void testCreateCourse() {
    Course course = new Course("Potions");
    generatorDataDao.createCourses(course);
    Assertions.assertEquals(1, generatorDataDao.rowsCount());
  }

  @Test
  void testCreateCourseStudentRelation() {
    Student student = new Student(1, "Harry", "Potter");
    generatorDataDao.createStudents(student);
    Course course = new Course("Potions");
    generatorDataDao.createCourses(course);
    StudentCourseRelation scRelation = new StudentCourseRelation(student.getId(), course.getId());
    generatorDataDao.createCourseStudentRelations(scRelation);
    Assertions.assertEquals(3, generatorDataDao.rowsCount());
  }

}
