package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("SELECT c FROM Course c JOIN StudentCourseRelation scr ON c.id = scr.courseId GROUP BY c HAVING COUNT(scr) <= :students")
	Optional<Course> findCoursesWithLessOrEqualsStudents(@Param("students") Integer students);

	@Query("SELECT c FROM Course c WHERE c.courseName = :courseName")
	Optional<Course> findByCourseName(@Param("courseName") String courseName);

	@Modifying
	@Query("UPDATE Course c SET c.courseName = :newCourseName, c.courseDescription = :newDescription WHERE c.courseName = :courseName")
	int editCourseNameAndDescription(@Param("courseName") String courseName,
			@Param("newCourseName") String newCourseName, @Param("newDescription") String newDescription);

	@Modifying
	@Query("DELETE FROM Course c WHERE c.courseName = :courseName")
	int deleteCourseByName(@Param("courseName") String courseName);
}
