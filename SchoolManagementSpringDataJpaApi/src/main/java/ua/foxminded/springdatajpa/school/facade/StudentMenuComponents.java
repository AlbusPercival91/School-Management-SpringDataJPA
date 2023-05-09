package ua.foxminded.springdatajpa.school.facade;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.springdatajpa.school.dao.service.JPACourseService;
import ua.foxminded.springdatajpa.school.dao.service.StudentService;
import ua.foxminded.springdatajpa.school.entity.Student;

@Slf4j
@Component
public class StudentMenuComponents {
	private final StudentService studentService;
	private final JPACourseService courseService;

	public StudentMenuComponents(StudentService studentService, JPACourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
	}

	public void findStudentsRelatedToCourseFacade(Scanner scan) {
		log.info(MenuConstants.COURSE_LIST);
		courseService.showAllCourses().forEach(course -> log.info(course.toString()));
		String courseName = scan.next();

		if (courseService.showAllCourses().stream().anyMatch(course -> course.getCourseName().equals(courseName))) {
			studentService.findStudentsRelatedToCourse(courseName).forEach(student -> log.info(student.toString()));
		} else {
			log.warn(MenuConstants.WRONG_COURSE);
		}
	}

	public void addNewStudentFacade(Scanner scan) {
		log.info(MenuConstants.STUDENT_NAME);
		String firstName = scan.nextLine();
		log.info(MenuConstants.STUDENT_LAST_NAME);
		String lastName = scan.nextLine();

		if (!firstName.isEmpty() || !lastName.isEmpty()) {
			log.info(MenuConstants.GROUP_ID);

			if (scan.hasNextInt()) {
				Integer groupId = scan.nextInt();

				if (groupId >= 0 && groupId <= 10) {

					if (groupId == 0) {
						groupId = null;
					}
					Student student = new Student(groupId, firstName, lastName);
					log.info("Student with ID:" + studentService.addNewStudent(student) + " added");
				} else {
					log.warn(MenuConstants.GROUP_ID_NOTE);
				}
			} else {
				log.warn(MenuConstants.GROUP_ID_NOTE2);
			}
		} else {
			log.warn(MenuConstants.EMPTY_NOTE);
		}
	}

	public void deleteStudentByIdFacade(Scanner scan) {
		log.info(MenuConstants.STUDENT_ID);
		int studentId = scan.nextInt();
		if (studentService.getStudentID().contains(studentId)) {
			log.info("Student with ID: " + studentService.deleteStudentByID(studentId) + " deleted");
		} else {
			log.warn(MenuConstants.STUDENT_ID_NOT_EXIST);
		}
	}

	public void addStudentToTheCourseFacade(Scanner scan) {
		log.info(MenuConstants.STUDENT_ID);

		if (scan.hasNextInt()) {
			Integer studentId = scan.nextInt();

			if (studentService.getStudentID().contains(studentId)) {
				log.info(MenuConstants.COURSE_LIST);
				courseService.showAllCourses().forEach(course -> log.info(course.toString()));
				String courseName = scan.next();

				if (courseService.showAllCourses().stream()
						.anyMatch(course -> course.getCourseName().equals(courseName))) {
					studentService.addStudentToTheCourse(studentId, courseName);
					log.info("Student with ID: " + studentId + " assigned to the course: " + courseName);
				} else {
					log.warn(MenuConstants.WRONG_COURSE);
				}
			} else {
				log.warn(MenuConstants.STUDENT_ID_NOT_EXIST);
			}
		} else {
			log.warn(MenuConstants.DIGITS_REQUIRED);
		}
	}

	public void removeStudentFromCourseFacade(Scanner scan) {
		log.info(MenuConstants.STUDENT_ID);

		if (scan.hasNextInt()) {
			Integer studentId = scan.nextInt();

			if (studentService.getStudentID().contains(studentId)) {
				log.info(MenuConstants.COURSE_LIST);
				courseService.showAllCourses().forEach(course -> log.info(course.toString()));
				String courseName = scan.next();

				if (courseService.showAllCourses().stream()
						.anyMatch(course -> course.getCourseName().equals(courseName))) {
					log.info(studentService.removeStudentFromCourse(studentId, courseName) + " student(s) with ID "
							+ studentId + " deleted from course " + courseName);
				} else {
					log.warn(MenuConstants.WRONG_COURSE);
				}
			} else {
				log.warn(MenuConstants.STUDENT_ID_NOT_EXIST);
			}
		} else {
			log.warn(MenuConstants.DIGITS_REQUIRED);
		}
	}

	public void updateStudentByIdFacade(Scanner scan) {
		log.info(MenuConstants.STUDENT_ID);
		Integer studentId = scan.nextInt();

		if (studentService.getStudentID().contains(studentId)) {
			log.info(MenuConstants.STUDENT_NAME);
			String newFirstName = scan.next();
			log.info(MenuConstants.STUDENT_LAST_NAME);
			String newLastName = scan.next();

			if (!newFirstName.isEmpty() || !newLastName.isEmpty()) {
				log.info(MenuConstants.GROUP_ID);

				if (scan.hasNextInt()) {
					Integer newGroupId = scan.nextInt();
					updateStudent(studentId, newFirstName, newLastName, newGroupId);
				} else {
					log.warn(MenuConstants.GROUP_ID_NOTE2);
				}
			} else {
				log.warn(MenuConstants.EMPTY_NOTE);
			}
		} else {
			log.warn(MenuConstants.STUDENT_ID_NOT_EXIST);
		}
	}

	private void updateStudent(Integer studentId, String newFirstName, String newLastName, Integer newGroupId) {
		if (newGroupId >= 0 && newGroupId <= 10) {

			if (newGroupId == 0) {
				newGroupId = null;
			}
			Student updatedStudent = new Student(newGroupId, newFirstName, newLastName);
			log.info("Student with ID: " + studentService.updateStudentById(studentId, updatedStudent) + " updated");
		} else {
			log.warn(MenuConstants.GROUP_ID_NOTE);
		}
	}

	public void showAllStudentsFacade() {
		studentService.showAllStudents().forEach(student -> log.info(student.toString()));
		log.info(MenuConstants.STUDENT_MENU);
	}

}
