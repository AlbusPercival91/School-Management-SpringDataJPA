package ua.foxminded.springdatajpa.school.mockito.daoservice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.foxminded.springdatajpa.school.dao.interfaces.GroupRepository;
import ua.foxminded.springdatajpa.school.dao.service.GroupService;
import ua.foxminded.springdatajpa.school.entity.Group;
import ua.foxminded.springdatajpa.school.facade.SchoolManager;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
class GroupServiceMockitoTest {

	@Autowired
	private GroupService groupService;

	@MockBean
	private GroupRepository groupRepository;

	@MockBean
	private SchoolManager schoolManager;

	@ParameterizedTest
	@CsvSource({ "30, aa-34", "100, ab-45", "23, er-12" })
	void shouldFindGroupsWithLessOrEqualsStudents(int numberOfStudents, String groupName) {
		Group group = new Group(groupName);
		List<Group> groups = Collections.singletonList(group);
		when(groupRepository.findGroupsWithLessOrEqualsStudents(numberOfStudents)).thenReturn(groups);
		List<Group> actual = groupService.findGroupsWithLessOrEqualsStudents(numberOfStudents);

		Assertions.assertEquals(groups.size(), actual.size(), "Expected number of courses to match actual");
		Group actualGroup = actual.get(0);
		Assertions.assertEquals(groupName, actualGroup.getGroupName(), "Expected course name to match actual");
		verify(groupRepository, Mockito.times(1)).findGroupsWithLessOrEqualsStudents(numberOfStudents);
	}

	@ParameterizedTest
    @CsvSource({ "aa-34", "ab-45", "er-12" })
    void shouldFindGroupsWithLessOrEqualsStudents_WhenStudentsZero() {
		when(groupRepository.findGroupsWithLessOrEqualsStudents(0)).thenReturn(Collections.emptyList());
	    List<Group> actual = groupService.findGroupsWithLessOrEqualsStudents(0);

	    Assertions.assertTrue(actual.isEmpty());
	    verify(groupRepository, Mockito.times(1)).findGroupsWithLessOrEqualsStudents(0);
    }

	@ParameterizedTest
	@CsvSource({ "1, aa-23", "2, !@-@$", "3, cc-45", "4, 45-df", "5, @#-45" })
	void shouldCreateGroup(int id, String groupName) {
		Group group = new Group(groupName);
		group.setId(id);
		when(groupRepository.save(group)).thenReturn(group);
		Integer actualGroupId = groupService.createGroup(group);

		Assertions.assertNotNull(group.getGroupName());
		Assertions.assertEquals(group.getId(), actualGroupId);
		verify(groupRepository, Mockito.times(1)).save(group);
	}

	@ParameterizedTest
	@CsvSource({ "1, aa-34, 11-bb", "2,aa-35, !!-++", "3, test, 123-abc", "4, 123, abc", "5, aa-aa, 22-33" })
	void shouldEditGroupName(int id, String groupName, String newGroupName) {
		Group group = new Group(groupName);
		group.setId(id);
		Group expectedGroup = new Group(newGroupName);
		expectedGroup.setId(id);
		when(groupRepository.save(group)).thenReturn(group);
		when(groupRepository.findByGroupName(groupName)).thenReturn(Optional.of(group));
		when(groupRepository.editGroupName(groupName, newGroupName)).thenReturn(expectedGroup);
		Group actualGroup = groupService.editGroupName(groupName, newGroupName);

		Assertions.assertNotNull(actualGroup);
		Assertions.assertEquals(group.toString(), actualGroup.toString());
		verify(groupRepository).save(group);
		verify(groupRepository).findByGroupName(groupName);

	}

	@ParameterizedTest
	@CsvSource({ "aa-34", "35-aa", "test", "123", "aa-aa", "00-00", "!@-@$" })
	void shouldDeleteGroupByName(String groupName) {
		Integer expectedCount = 1;
		when(groupRepository.deleteGroupByName(groupName)).thenReturn(expectedCount);
		Integer actualCount = groupService.deleteGroupByName(groupName);

		Assertions.assertEquals(expectedCount, actualCount);
		verify(groupRepository, Mockito.times(1)).deleteGroupByName(groupName);
	}

	@ParameterizedTest
	@CsvSource({ "aa-34", "35-aa", "test", "123", "aa-aa", "00-00", "!@-@$" })
	void shouldShowAllGroups(String groupName) {
		Group group = new Group(groupName);
		List<Group> groups = Collections.singletonList(group);
		when(groupRepository.findAll()).thenReturn(groups);
		List<Group> actual = groupService.showAllGroups();

		Assertions.assertTrue(!groups.isEmpty() && !actual.isEmpty());
		Assertions.assertNotNull(group.getGroupName());
		Assertions.assertEquals(groups, actual);
		verify(groupRepository).findAll();
	}
}
