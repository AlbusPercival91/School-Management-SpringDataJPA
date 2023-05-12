package ua.foxminded.springdatajpa.school.dao.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import ua.foxminded.springdatajpa.school.dao.interfaces.GroupRepository;
import ua.foxminded.springdatajpa.school.entity.Group;

@Service
@Transactional
public class GroupService {
	private final GroupRepository groupRepository;

	public GroupService(GroupRepository groupRepository) {
		this.groupRepository = groupRepository;
	}

	public List<Group> findGroupsWithLessOrEqualsStudents(long maxStudents) {
		return groupRepository.findGroupsWithLessOrEqualsStudents(maxStudents);
	}

	public int createGroup(Group group) {
		Group savedGroup = groupRepository.save(group);
		return savedGroup.getId();
	}

	public Group editGroupName(String groupName, String newGroupName) {
		Optional<Group> optionalGroup = groupRepository.findByGroupName(groupName);

		if (optionalGroup.isEmpty()) {
			throw new IllegalArgumentException("Course not found");
		}
		Group group = optionalGroup.get();
		group.setGroupName(newGroupName);
		return groupRepository.save(group);
	}

	public int deleteGroupByName(String groupName) {
		return groupRepository.deleteGroupByName(groupName);
	}

	public List<Group> showAllGroups() {
		return groupRepository.findAll();
	}

}
