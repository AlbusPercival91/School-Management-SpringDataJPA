package ua.foxminded.hibernate.school.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.dao.interfaces.StudentDao;
import ua.foxminded.hibernate.school.entity.Student;

@Repository
@Transactional
public class JPAStudentDao implements StudentDao {

  @PersistenceContext
  private final EntityManager entityManager;

  public JPAStudentDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public int addNewStudent(Student student) {
    entityManager.persist(student);
    return student.getId();
  }

  @Override
  public int deleteStudentByID(Integer id) {
    Query query = entityManager.createQuery("DELETE FROM Student s WHERE s.id = :id");
    query.setParameter("id", id);
    return query.executeUpdate();
  }

  @Override
  public List<Integer> getStudentID() {
    String jpql = "SELECT s.id FROM Student s";
    TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
    return query.getResultList();
  }

  @Override
  public List<Student> findStudentsRelatedToCourse(String courseName) {
    String jpql = """
        SELECT s FROM Student s
        JOIN StudentCourseRelation scr ON s.id = scr.studentId
        JOIN Course c ON c.id = scr.courseId WHERE c.courseName = :courseName
        """;
    TypedQuery<Student> query = entityManager.createQuery(jpql, Student.class);
    query.setParameter("courseName", courseName);
    return query.getResultList();
  }

  @Override
  public int addStudentToTheCourse(Integer studentId, String courseName) {
    String jpql = """
        INSERT INTO StudentCourseRelation (studentId, courseId)
        SELECT s.id, c.id FROM Student s, Course c
        WHERE s.id = :studentId AND c.courseName = :courseName
                """;
    Query query = entityManager.createQuery(jpql);
    query.setParameter("studentId", studentId);
    query.setParameter("courseName", courseName);
    return query.executeUpdate();
  }

  @Override
  public int removeStudentFromCourse(Integer studentId, String courseName) {
    String jpql = """
        DELETE FROM StudentCourseRelation scr
        WHERE scr.studentId = :studentId
        AND scr.courseId IN (SELECT c.id FROM Course c WHERE c.courseName = :courseName)
        """;
    Query query = entityManager.createQuery(jpql);
    query.setParameter("studentId", studentId);
    query.setParameter("courseName", courseName);
    return query.executeUpdate();
  }

  @Override
  public int updateStudentById(Integer studentId, Student student) {
    String jpql = "UPDATE Student s SET s.groupId = :groupId, s.firstName = :firstName, s.lastName = :lastName WHERE s.id = :studentId";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("groupId", student.getGroupId());
    query.setParameter("firstName", student.getFirstName());
    query.setParameter("lastName", student.getLastName());
    query.setParameter("studentId", studentId);
    return query.executeUpdate();
  }

  @Override
  public List<Student> showAllStudents() {
    TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s", Student.class);
    return query.getResultList();
  }
}
