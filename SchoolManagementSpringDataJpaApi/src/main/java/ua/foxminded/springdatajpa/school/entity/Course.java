package ua.foxminded.springdatajpa.school.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "course", schema = "school")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private Integer id;

	@Column(name = "course_name")
	private String courseName;

	@Column(name = "course_description")
	private String courseDescription;

	@ToString.Exclude
	@ManyToMany(mappedBy = "courses", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<Student> students = new HashSet<>();

	public Course() {

	}

	public Course(String courseName, String courseDescription) {
		this.courseName = courseName;
		this.courseDescription = courseDescription;
	}

	public Course(String courseName) {
		this.courseName = courseName;
	}

}
