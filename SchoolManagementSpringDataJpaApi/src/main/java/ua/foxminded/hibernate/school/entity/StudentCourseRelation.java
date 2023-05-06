package ua.foxminded.hibernate.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "students_courses_checkouts", schema = "school")
public class StudentCourseRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checkouts_id")
	private int id;

	@Column(name = "student_id")
	private int studentId;

	@Column(name = "course_id")
	private int courseId;

	public StudentCourseRelation() {

	}

	public StudentCourseRelation(int studentId, int courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}

}
