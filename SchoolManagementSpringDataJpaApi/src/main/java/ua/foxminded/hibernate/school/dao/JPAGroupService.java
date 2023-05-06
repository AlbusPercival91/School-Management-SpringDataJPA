package ua.foxminded.hibernate.school.dao;

import java.util.List;
import org.springframework.stereotype.Service;

import ua.foxminded.hibernate.school.dao.interfaces.GroupDao;
import ua.foxminded.hibernate.school.entity.Group;

@Service
public class JPAGroupService {
  private static final String WRONG = "oops something went wrong";

  private final GroupDao groupDao;

  public JPAGroupService(GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public List<Group> findGroupsWithLessOrEqualsStudents(int students) {
    return groupDao.findGroupsWithLessOrEqualsStudents(students);
  }

  public int createGroup(Group group) {
    int result = groupDao.createGroup(group);

    if (result < 1) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public int editGroupName(String groupName, String newGroupName) {
    int result = groupDao.editGroupName(groupName, newGroupName);

    if (result != 1) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public int deleteGroupByName(String groupName) {
    int result = groupDao.deleteGroupByName(groupName);

    if (result != 1) {
      throw new IllegalStateException(WRONG);
    }
    return result;
  }

  public List<Group> showAllGroups() {
    return groupDao.showAllGroups();
  }

}
