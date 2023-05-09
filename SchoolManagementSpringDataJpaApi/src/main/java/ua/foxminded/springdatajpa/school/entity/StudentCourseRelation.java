package ua.foxminded.springdatajpa.school.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "students_courses_checkouts", schema = "school")
public class StudentCourseRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checkouts_id")
	private Integer id;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "course_id")
	private Integer courseId;

	public StudentCourseRelation() {

	}

	public StudentCourseRelation(Integer studentId, Integer courseId) {
		this.studentId = studentId;
		this.courseId = courseId;
	}

}
