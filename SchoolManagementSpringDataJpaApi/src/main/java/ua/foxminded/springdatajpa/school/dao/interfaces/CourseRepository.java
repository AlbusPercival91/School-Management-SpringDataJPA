package ua.foxminded.springdatajpa.school.dao.interfaces;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.springdatajpa.school.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("SELECT c FROM Course c JOIN StudentCourseRelation scr ON c.id = scr.courseId GROUP BY c HAVING COUNT(scr) <= :students")
	List<Course> findCoursesWithLessOrEqualsStudents(@Param("students") long maxStudents);

	Optional<Course> findByCourseName(String courseName);

	@Modifying
	@Query("UPDATE Course c SET c.courseName = :newCourseName, c.courseDescription = :newDescription WHERE c.courseName = :courseName")
	Course editCourseNameAndDescription(@Param("courseName") String courseName,
			@Param("newCourseName") String newCourseName, @Param("newDescription") String newDescription);

	int deleteByCourseName(String courseName);
}
