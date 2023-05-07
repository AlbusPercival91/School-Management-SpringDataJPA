package ua.foxminded.hibernate.school.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ua.foxminded.hibernate.school.dao.interfaces.GroupRepository;
import ua.foxminded.hibernate.school.entity.Group;

@Service
public class JPAGroupService {
	private final GroupRepository groupRepository;

	public JPAGroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public List<Group> findGroupsWithLessOrEqualsStudents(int students) {
		return groupRepository.findGroupsWithLessOrEqualsStudents(students);
	}

	public int createGroup(Group group) {
		Group savedGroup = groupRepository.save(group);
		return savedGroup.getId();
	}

	public int editGroupName(String groupName, String newGroupName) {
		Optional<Group> optionalGroup = groupRepository.findByGroupName(groupName);

		if (optionalGroup.isEmpty()) {
			throw new IllegalArgumentException("Course not found");
		}
		Group group = optionalGroup.get();
		group.setGroupName(newGroupName);
		Group savedGroup = groupRepository.save(group);
		return savedGroup.getId();
	}

	public int deleteGroupByName(String groupName) {
		return groupRepository.deleteGroupByName(groupName);
	}

	public List<Group> showAllGroups() {
		return groupRepository.findAll();
	}

}
