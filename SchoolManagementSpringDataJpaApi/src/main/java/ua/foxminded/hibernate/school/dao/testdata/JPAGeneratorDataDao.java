package ua.foxminded.hibernate.school.dao.testdata;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.hibernate.school.dao.interfaces.CourseRepository;
import ua.foxminded.hibernate.school.dao.interfaces.GeneratorDataDao;
import ua.foxminded.hibernate.school.dao.interfaces.GroupRepository;
import ua.foxminded.hibernate.school.dao.interfaces.StudentCourseRepository;
import ua.foxminded.hibernate.school.dao.interfaces.StudentRepository;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;

@Repository
@Transactional
public class JPAGeneratorDataDao implements GeneratorDataDao {
	private final StudentRepository studentRepository;
	private final GroupRepository groupRepository;
	private final CourseRepository courseRepository;
	private final StudentCourseRepository studentCourseRepository;

	public JPAGeneratorDataDao(StudentRepository studentRepository, GroupRepository groupRepository,
			CourseRepository courseRepository, StudentCourseRepository studentCourseRepository) {
		this.studentRepository = studentRepository;
		this.groupRepository = groupRepository;
		this.courseRepository = courseRepository;
		this.studentCourseRepository = studentCourseRepository;
	}

	@Override
	public void createStudent(Student student) {
		studentRepository.save(student);
	}

	@Override
	public void createGroup(Group group) {
		groupRepository.save(group);
	}

	@Override
	public void createCourse(Course course) {
		courseRepository.save(course);
	}

	@Override
	public void createCourseStudentRelation(StudentCourseRelation scRelation) {
		studentCourseRepository.save(scRelation);
	}

	@Override
	public int rowsCount() {
		int studentCount = studentRepository.findAll().size();
		int groupCount = groupRepository.findAll().size();
		int courseCount = courseRepository.findAll().size();
		int scrCount = studentCourseRepository.findAll().size();
		return studentCount + groupCount + courseCount + scrCount;
	}
}
