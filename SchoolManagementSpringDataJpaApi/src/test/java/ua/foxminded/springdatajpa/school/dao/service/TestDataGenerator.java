package ua.foxminded.springdatajpa.school.dao.service;

import java.util.Map;
import java.util.Set;

import ua.foxminded.springdatajpa.school.dao.testdata.GeneratorDataDao;
import ua.foxminded.springdatajpa.school.entity.Course;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.entity.Student;
import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;
import ua.foxminded.springdatajpa.school.testdata.CourseMaker;
import ua.foxminded.springdatajpa.school.testdata.GroupMaker;
import ua.foxminded.springdatajpa.school.testdata.StudentMaker;

public class TestDataGenerator {
  private final StudentMaker studentMaker;
  private final GroupMaker groupMaker;
  private final CourseMaker courseMaker;
  private final GeneratorDataDao generatorDataDao;

  public TestDataGenerator(StudentMaker studentMaker, GroupMaker groupMaker, CourseMaker courseMaker,
      GeneratorDataDao generatorDataDao) {
    this.studentMaker = studentMaker;
    this.groupMaker = groupMaker;
    this.courseMaker = courseMaker;
    this.generatorDataDao = generatorDataDao;
  }

  public void createStudent() {
    int i = 0;

    for (String s : studentMaker.generateStudents(studentMaker.generateNames(20), studentMaker.generateSurnames(20))) {
      Student student = new Student(groupMaker.assignGroupId().get(i++), s.substring(0, s.indexOf(" ")),
          s.substring(s.indexOf(" ")));
      generatorDataDao.createStudents(student);
    }
  }

  public void createGroup() {
    for (String s : groupMaker.generateGroups()) {
      Group group = new Group(s);
      generatorDataDao.createGroups(group);
    }
  }

  public void createCourse() {
    for (String s : courseMaker.generateCourses()) {
      Course course = new Course(s, "TBD");
      generatorDataDao.createCourses(course);
    }
  }

  public void createCourseStudentRelation() {
    for (Map.Entry<Integer, Set<Integer>> entry : courseMaker.assignCourseId().entrySet()) {
      Integer key = entry.getKey();
      Set<Integer> value = entry.getValue();

      for (Integer i : value) {
        StudentCourseRelation scRelation = new StudentCourseRelation(key, i);
        generatorDataDao.createCourseStudentRelations(scRelation);
      }
    }
  }

}
