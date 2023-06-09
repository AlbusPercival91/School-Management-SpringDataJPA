package ua.foxminded.springdatajpa.school.dao.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.springdatajpa.school.dao.interfaces.StudentRepository;
import ua.foxminded.springdatajpa.school.entity.Student;

@Service
@Transactional
public class StudentService {
	private static final String WRONG = "oops something went wrong";

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> findStudentsRelatedToCourse(String courseName) {
		return studentRepository.findStudentsRelatedToCourse(courseName);
	}

	public int addNewStudent(Student student) {
		Student savedStudent = studentRepository.save(student);
		return savedStudent.getId();
	}

	public int deleteStudentByID(int studentId) {
		int id = 0;
		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent.isPresent()) {
			id = optionalStudent.get().getId();
			studentRepository.deleteById(studentId);
		}
		return id;
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

	public Student updateStudentById(Integer studentId, Student student) {
		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent.isEmpty()) {
			throw new IllegalArgumentException("Student not found");
		}
		Student stud = optionalStudent.get();
		stud.setGroupId(student.getGroupId());
		stud.setFirstName(student.getFirstName());
		stud.setLastName(student.getLastName());
		return studentRepository.save(stud);
	}

	public List<Student> showAllStudents() {
		return studentRepository.findAll();
	}
}
