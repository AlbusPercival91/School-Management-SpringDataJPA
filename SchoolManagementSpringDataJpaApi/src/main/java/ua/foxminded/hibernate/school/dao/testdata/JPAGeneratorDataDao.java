package ua.foxminded.hibernate.school.dao.testdata;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ua.foxminded.hibernate.school.dao.interfaces.GeneratorDataDao;
import ua.foxminded.hibernate.school.entity.Course;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.entity.Student;
import ua.foxminded.hibernate.school.entity.StudentCourseRelation;

@Repository
@Transactional
public class JPAGeneratorDataDao implements GeneratorDataDao {

  @PersistenceContext
  private final EntityManager entityManager;

  public JPAGeneratorDataDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public void createStudent(Student student) {
    entityManager.persist(student);
  }

  @Override
  public void createGroup(Group group) {
    entityManager.persist(group);
  }

  @Override
  public void createCourse(Course course) {
    entityManager.persist(course);
  }

  @Override
  public void createCourseStudentRelation(StudentCourseRelation scRelation) {
    entityManager.persist(scRelation);
  }

  @Override
  public int rowsCount() {
    String sql = """
                  SELECT SUM (COUNT) FROM (
        SELECT COUNT(*) AS COUNT FROM school.students
        UNION ALL
        SELECT COUNT(*) AS COUNT FROM school.groups
        UNION ALL
        SELECT COUNT(*) AS COUNT FROM school.course
        UNION ALL
        SELECT COUNT(*) AS COUNT FROM school.students_courses_checkouts
        ) AS counts;
                  """;
    Query query = entityManager.createNativeQuery(sql);
    return ((Number) query.getSingleResult()).intValue();
  }

}
