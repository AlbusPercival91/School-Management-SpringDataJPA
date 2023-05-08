package ua.foxminded.hibernate.school.dao.service;

import java.util.Map;
import java.util.Set;
import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataDao;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;
import ua.foxminded.hibernate.school.testdata.CourseMaker;
import ua.foxminded.hibernate.school.testdata.GroupMaker;
import ua.foxminded.hibernate.school.testdata.StudentMaker;

public class TestDataGenerator {
  private final StudentMaker studentMaker;
  private final GroupMaker groupMaker;
  private final CourseMaker courseMaker;
  private final JPAGeneratorDataDao generatorDataDao;

  public TestDataGenerator(StudentMaker studentMaker, GroupMaker groupMaker, CourseMaker courseMaker,
      JPAGeneratorDataDao generatorDataDao) {
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
      generatorDataDao.createStudent(student);
    }
  }

  public void createGroup() {
    for (String s : groupMaker.generateGroups()) {
      Group group = new Group(s);
      generatorDataDao.createGroup(group);
    }
  }

  public void createCourse() {
    for (String s : courseMaker.generateCourses()) {
      Course course = new Course(s, "TBD");
      generatorDataDao.createCourse(course);
    }
  }

  public void createCourseStudentRelation() {
    for (Map.Entry<Integer, Set<Integer>> entry : courseMaker.assignCourseId().entrySet()) {
      Integer key = entry.getKey();
      Set<Integer> value = entry.getValue();

      for (Integer i : value) {
        StudentCourseRelation scRelation = new StudentCourseRelation(key, i);
        generatorDataDao.createCourseStudentRelation(scRelation);
      }
    }
  }

}
