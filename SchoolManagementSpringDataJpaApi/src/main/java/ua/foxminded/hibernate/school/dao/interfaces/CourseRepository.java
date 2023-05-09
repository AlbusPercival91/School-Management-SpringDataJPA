package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Modifying
	@Query("SELECT c FROM Course c JOIN StudentCourseRelation scr ON c.id = scr.courseId GROUP BY c HAVING COUNT(scr) <= :students")
	List<Course> findCoursesWithLessOrEqualsStudents(Integer students);

	@Query("SELECT c FROM Course c WHERE c.courseName = :courseName")
	Optional<Course> findByCourseName(@Param("courseName") String courseName);

	@Modifying
	@Query("UPDATE Course c SET c.courseName = :newCourseName, c.courseDescription = :newDescription WHERE c.courseName = :courseName")
	int editCourseNameAndDescription(String courseName, String newCourseName, String newDescription);

	@Modifying
	@Query("DELETE FROM Course c WHERE c.courseName = :courseName")
	int deleteCourseByName(String courseName);
}
