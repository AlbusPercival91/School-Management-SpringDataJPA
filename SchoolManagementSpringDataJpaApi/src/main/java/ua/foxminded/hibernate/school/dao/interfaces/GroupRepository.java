package ua.foxminded.hibernate.school.dao.interfaces;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.hibernate.school.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

	public List<Group> findGroupsWithLessOrEqualsStudents(Integer students);

	public int createGroup(Group group);

	public int editGroupName(String groupName, String newGroupName);

	public int deleteGroupByName(String groupName);

	public List<Group> showAllGroups();

}
