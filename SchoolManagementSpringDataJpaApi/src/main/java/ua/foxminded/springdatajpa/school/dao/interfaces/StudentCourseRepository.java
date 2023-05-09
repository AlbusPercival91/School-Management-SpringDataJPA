package ua.foxminded.springdatajpa.school.dao.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.springdatajpa.school.entity.StudentCourseRelation;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourseRelation, Integer> {

}
