package ua.foxminded.hibernate.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ua.foxminded.hibernate.school.dao.testdata.JPAGeneratorDataService;
import ua.foxminded.hibernate.school.facade.SchoolManager;

@Component
public class SchoolRunner implements ApplicationRunner {

  @Autowired
  private JPAGeneratorDataService testData;

  @Autowired
  private SchoolManager schoolManager;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    if (testData.databaseIsEmpty()) {
      testData.createGroup();
      testData.createStudent();
      testData.createCourse();
      testData.createCourseStudentRelation();
    }
    schoolManager.manage();
  }

}
