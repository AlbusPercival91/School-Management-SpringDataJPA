package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.hibernate.school.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query("SELECT s.id FROM Student s")
	List<Integer> getStudentID();

	@Query("""
			SELECT s FROM Student s
			     JOIN StudentCourseRelation scr ON s.id = scr.studentId
			     JOIN Course c ON c.id = scr.courseId WHERE c.courseName = :courseName
			""")
	List<Student> findStudentsRelatedToCourse(@Param("courseName") String courseName);

	@Modifying
	@Query("""
			 INSERT INTO StudentCourseRelation (studentId, courseId)
			     SELECT s.id, c.id FROM Student s, Course c
			     WHERE s.id = :studentId AND c.courseName = :courseName
			""")
	int addStudentToTheCourse(@Param("studentId") Integer studentId, @Param("courseName") String courseName);

	@Modifying
	@Query("""
			 DELETE FROM StudentCourseRelation scr
			     WHERE scr.studentId = :studentId
			     AND scr.courseId IN (SELECT c.id FROM Course c WHERE c.courseName = :courseName)
			""")
	int removeStudentFromCourse(@Param("studentId") Integer studentId, @Param("courseName") String courseName);

	@Modifying
	@Query("UPDATE Student s SET s.groupId = :groupId, s.firstName = :firstName, s.lastName = :lastName WHERE s.id = :studentId")
	int updateStudentById(@Param("studentId") Integer studentId, Student student);
}
