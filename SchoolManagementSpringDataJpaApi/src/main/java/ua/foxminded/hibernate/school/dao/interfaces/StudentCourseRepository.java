package ua.foxminded.hibernate.school.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseRelation, Integer> {

}
