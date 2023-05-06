package ua.foxminded.hibernate.school.dao.testdata;

import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.foxminded.hibernate.school.dao.interfaces.GeneratorDataDao;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;
import ua.foxminded.hibernate.school.testdata.CourseMaker;
import ua.foxminded.hibernate.school.testdata.GroupMaker;
import ua.foxminded.hibernate.school.testdata.StudentMaker;

@Service
public class JPAGeneratorDataService {

  @Autowired
  private StudentMaker studentMaker;

  @Autowired
  private CourseMaker courseMaker;

  @Autowired
  private GroupMaker groupMaker;

  private final GeneratorDataDao dataRepository;

  public JPAGeneratorDataService(GeneratorDataDao dataRepository) {
    this.dataRepository = dataRepository;
  }

  public void createStudent() {
    int i = 0;

    for (String s : studentMaker.generateStudents(studentMaker.generateNames(20), studentMaker.generateSurnames(20))) {
      Student student = new Student(groupMaker.assignGroupId().get(i++), s.substring(0, s.indexOf(" ")),
          s.substring(s.indexOf(" ")));
      dataRepository.createStudent(student);
    }
  }

  public void createGroup() {
    for (String s : groupMaker.generateGroups()) {
      Group group = new Group(s);
      dataRepository.createGroup(group);
    }
  }

  public void createCourse() {
    for (String s : courseMaker.generateCourses()) {
      Course course = new Course(s, "TBD");
      dataRepository.createCourse(course);
    }
  }

  public void createCourseStudentRelation() {
    for (Map.Entry<Integer, Set<Integer>> entry : courseMaker.assignCourseId().entrySet()) {
      Integer key = entry.getKey();
      Set<Integer> value = entry.getValue();

      for (Integer i : value) {
        StudentCourseRelation scRelation = new StudentCourseRelation(key, i);
        dataRepository.createCourseStudentRelation(scRelation);
      }
    }
  }

  public boolean databaseIsEmpty() {
    return dataRepository.rowsCount() == 0;
  }

}
