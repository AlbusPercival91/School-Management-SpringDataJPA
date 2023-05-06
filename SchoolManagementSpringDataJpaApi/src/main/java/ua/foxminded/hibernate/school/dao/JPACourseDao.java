package ua.foxminded.hibernate.school.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.dao.interfaces.CourseDao;
import ua.foxminded.hibernate.school.entity.Course;

@Repository
@Transactional
public class JPACourseDao implements CourseDao {

  @PersistenceContext
  private final EntityManager entityManager;

  public JPACourseDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Course> findCoursesWithLessOrEqualsStudents(Integer students) {
    String jpql = "SELECT c FROM Course c JOIN StudentCourseRelation scr ON c.id = scr.courseId GROUP BY c HAVING COUNT(scr) <= :students";
    TypedQuery<Course> query = entityManager.createQuery(jpql, Course.class);
    query.setParameter("students", students.longValue());
    return query.getResultList();
  }

  @Override
  public int createCourse(Course course) {
    entityManager.persist(course);
    return course.getId();
  }

  @Override
  public int editCourseNameAndDescription(String courseName, String newCourseName, String newDescription) {
    String jpql = "UPDATE Course c SET c.courseName = :newCourseName, c.courseDescription = :newDescription WHERE c.courseName = :courseName";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("newCourseName", newCourseName);
    query.setParameter("newDescription", newDescription);
    query.setParameter("courseName", courseName);
    return query.executeUpdate();
  }

  @Override
  public int deleteCourseByName(String courseName) {
    String jpql = "DELETE FROM Course c WHERE c.courseName = :courseName";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("courseName", courseName);
    return query.executeUpdate();
  }

  @Override
  public List<Course> showAllCourses() {
    TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c", Course.class);
    return query.getResultList();
  }
}
