package ua.foxminded.hibernate.school.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import ua.foxminded.hibernate.school.dao.interfaces.GroupDao;
import ua.foxminded.hibernate.school.entity.Group;

@Repository
@Transactional
public class JPAGroupDao implements GroupDao {

  @PersistenceContext
  private final EntityManager entityManager;

  public JPAGroupDao(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Group> findGroupsWithLessOrEqualsStudents(Integer students) {
    TypedQuery<Group> query = entityManager.createQuery(
        "SELECT g FROM Group g JOIN Student s ON g.id = s.groupId GROUP BY g HAVING COUNT(s) <= :students",
        Group.class);
    query.setParameter("students", students.longValue());
    return query.getResultList();
  }

  @Override
  public int createGroup(Group group) {
    entityManager.persist(group);
    return group.getId();
  }

  @Override
  public int editGroupName(String groupName, String newGroupName) {
    String jpql = "UPDATE Group c SET c.groupName = :newGroupName WHERE c.groupName = :groupName";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("newGroupName", newGroupName);
    query.setParameter("groupName", groupName);
    return query.executeUpdate();
  }

  @Override
  public int deleteGroupByName(String groupName) {
    String jpql = "DELETE FROM Group c WHERE c.groupName = :groupName";
    Query query = entityManager.createQuery(jpql);
    query.setParameter("groupName", groupName);
    return query.executeUpdate();
  }

  @Override
  public List<Group> showAllGroups() {
    TypedQuery<Group> query = entityManager.createQuery("SELECT c FROM Group c", Group.class);
    return query.getResultList();
  }

}
