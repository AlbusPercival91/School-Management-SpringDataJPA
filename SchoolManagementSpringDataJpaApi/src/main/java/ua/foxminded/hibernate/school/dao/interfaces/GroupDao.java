package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;

import ua.foxminded.hibernate.school.entity.Group;

public interface GroupDao {

  public List<Group> findGroupsWithLessOrEqualsStudents(Integer students);

  public int createGroup(Group group);

  public int editGroupName(String groupName, String newGroupName);

  public int deleteGroupByName(String groupName);

  public List<Group> showAllGroups();

}
