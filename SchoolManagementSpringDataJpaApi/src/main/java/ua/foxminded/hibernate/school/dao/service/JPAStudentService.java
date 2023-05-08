package ua.foxminded.hibernate.school.dao.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ua.foxminded.hibernate.school.dao.interfaces.StudentRepository;
import ua.foxminded.hibernate.school.entity.Student;

@Service
public class JPAStudentService {
	private static final String WRONG = "oops something went wrong";

	private final StudentRepository studentRepository;

	public JPAStudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> findStudentsRelatedToCourse(String courseName) {
		return studentRepository.findStudentsRelatedToCourse(courseName);
	}

	public int addNewStudent(Student student) {
		Student savedStudent = studentRepository.save(student);
		return savedStudent.getId();
	}

	public int deleteStudentByID(int id) {
		studentRepository.deleteById(id);
		return 1;
	}

	public List<Integer> getStudentID() {
		return studentRepository.getStudentID();
	}

	public int addStudentToTheCourse(Integer studentId, String courseName) {
		int result = studentRepository.addStudentToTheCourse(studentId, courseName);

		if (result != 1) {
			throw new IllegalStateException(WRONG);
		}
		return result;
	}

	public int removeStudentFromCourse(Integer studentId, String courseName) {
		return studentRepository.removeStudentFromCourse(studentId, courseName);
	}

	public int updateStudentById(Integer studentId, Student student) {
		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent.isEmpty()) {
			throw new IllegalArgumentException("Course not found");
		}
		Student stud = optionalStudent.get();
		stud.setGroupId(student.getGroupId());
		stud.setFirstName(student.getFirstName());
		stud.setLastName(student.getLastName());
		Student savedStudent = studentRepository.save(stud);
		return savedStudent.getId();
	}

	public List<Student> showAllStudents() {
		return studentRepository.findAll();
	}

}
