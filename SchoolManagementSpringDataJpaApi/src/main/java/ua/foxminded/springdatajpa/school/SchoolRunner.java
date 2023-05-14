package ua.foxminded.springdatajpa.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ua.foxminded.springdatajpa.school.dao.testdata.GeneratorDataService;
import ua.foxminded.springdatajpa.school.facade.SchoolManager;

@Component
public class SchoolRunner implements ApplicationRunner {

	@Autowired
	private GeneratorDataService testData;

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
