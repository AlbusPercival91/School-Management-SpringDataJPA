package ua.foxminded.springdatajpa.school.facade;

public final class MenuConstants {
	static final String DIGITS_REQUIRED = "Digits required!";
	static final String STUDENT_ID = "Enter Student's ID: ";
	static final String WRONG_COURSE = "Wrong course name!";
	static final String NUMBER_OF_STUDENTS = "Enter number of students: ";
	static final String COURSE_NAME = "Enter course name: ";
	static final String COURSE_DESCRIPTION = "Enter course description: ";
	static final String STUDENT_NAME = "Enter student name: ";
	static final String STUDENT_LAST_NAME = "Enter student last name: ";
	static final String GROUP_ID = "Enter group id (if student don't have group enter 0): ";
	static final String STUDENT_ID_NOT_EXIST = "Student ID not exist!";
	static final String COURSE_LIST = "Please choose the course from List\n";
	static final String GROUP_ID_NOTE = "Group ID should be from 0 to 10.";
	static final String GROUP_ID_NOTE2 = "Wrong id format, digits required!";
	static final String EMPTY_NOTE = "Empty entrance!";
	static final String GROUP_NAME = "Enter group name as in pattern: [a-z]{2}-[0-9]{2}";
	static final String CHOOSE_GROUP_NAME = "Choose group name from List:";
	static final String NEW_GROUP_NAME = "Enter new group name as in pattern: [a-z]{2}-[0-9]{2}";
	static final String NO_SUCH_GROUP = "Group not exists, try again";
	static final String CHOOSE_COURSE_NAME = "Choose course name from List:";
	static final String NEW_COURSE_NAME = "Enter new course name:";
	static final String NO_SUCH_COURSE = "Course not exists, try again";
	static final String MAIN_MENU = """

			a. STUDENTS SERVICE
			b. GROUP SERVICE
			c. COURSE SERVICE
			q. QUIT
			    """;
	static final String STUDENT_MENU = """

			a. Find all students related to the course with the given name
			b. Add a new student
			c. Delete a student by the ID
			d. Add a student to the course (from a list)
			e. Remove the student from one of their courses
			f. Update student by ID
			g. Show all students
			m. To Main Menu
			    """;
	static final String GROUP_MENU = """

			a. Find all groups with less or equal students’ number
			b. Create a new group
			c. Edit group name
			d. Delete group by name
			e. Show all groups
			m. To Main Menu
			    """;
	static final String COURSE_MENU = """

			a. Find all courses with less or equal students’ number
			b. Create a new course
			c. Edit course name and description
			d. Delete course by name
			e. Show all courses
			m. To Main Menu
			    """;

	private MenuConstants() {

	}

}
