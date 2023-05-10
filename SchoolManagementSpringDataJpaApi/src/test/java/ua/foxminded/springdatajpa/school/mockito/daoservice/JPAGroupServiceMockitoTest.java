package ua.foxminded.springdatajpa.school.mockito.daoservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.hibernate.school.dao.JPAGroupDao;
import ua.foxminded.hibernate.school.dao.JPAGroupService;
import ua.foxminded.hibernate.school.entity.Group;
import ua.foxminded.hibernate.school.facade.SchoolManager;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class JPAGroupServiceMockitoTest {

  @Autowired
  private JPAGroupService groupService;

  @MockBean
  private JPAGroupDao groupDAO;

  @MockBean
  private SchoolManager schoolManager;
  
  private AutoCloseable closeable;

  @BeforeEach
  public void open() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void release() throws Exception {
    closeable.close();
  }

  @ParameterizedTest
  @CsvSource({ "30, aa-34", "100, ab-45", "23, er-12" })
  void shouldFindGroupsWithLessOrEqualsStudents(int numberOfStudents, String groupName) {
    Group group = new Group(groupName);
    List<Group> groups = Collections.singletonList(group);
    when(groupDAO.findGroupsWithLessOrEqualsStudents(numberOfStudents)).thenReturn(groups);
    List<Group> actual = groupService.findGroupsWithLessOrEqualsStudents(numberOfStudents);

    Assertions.assertEquals(groups.size(), actual.size(), "Expected number of groups to match actual");
    Group actualGroup = actual.get(0);
    Assertions.assertEquals(groupName, actualGroup.getGroupName(), "Expected group name to match actual");
    verify(groupDAO, Mockito.times(1)).findGroupsWithLessOrEqualsStudents(numberOfStudents);
  }

  @ParameterizedTest
  @CsvSource({ "aa-34", "ab-45", "er-12" })
  void shouldFindGroupsWithLessOrEqualsStudents_WhenStudentsZero() {
    when(groupDAO.findGroupsWithLessOrEqualsStudents(0)).thenReturn(Collections.emptyList());
    List<Group> actual = groupService.findGroupsWithLessOrEqualsStudents(0);

    Assertions.assertTrue(actual.isEmpty());
    verify(groupDAO, Mockito.times(1)).findGroupsWithLessOrEqualsStudents(0);
  }

  @ParameterizedTest
  @CsvSource({ "aa-23", "!@-@$", "cc-45", "45-df", "@#-45" })
  void shouldCreateGroup(String groupName) {
    Integer expectedCount = 1;
    Group group = new Group(groupName);
    when(groupDAO.createGroup(any(Group.class))).thenReturn(expectedCount);
    Integer actualCount = groupService.createGroup(group);

    Assertions.assertNotNull(group.getGroupName());
    Assertions.assertEquals(expectedCount, actualCount);
    verify(groupDAO, Mockito.times(1)).createGroup(any(Group.class));
  }

  @ParameterizedTest
  @CsvSource({ "aa-34, aa-35", "aa-35, 35-aa", "test, test-test", "123, 321", "aa-aa, bb-bb", "00-00, 11-11",
      "!@-@$, )&-%^" })
  void shouldEditGroupName(String groupName, String newGroupName) {
    Integer expectedCount = 1;
    when(groupDAO.editGroupName(groupName, newGroupName)).thenReturn(expectedCount);
    Integer actualCount = groupService.editGroupName(groupName, newGroupName);

    Assertions.assertEquals(expectedCount, actualCount);
    verify(groupDAO, Mockito.times(1)).editGroupName(groupName, newGroupName);
  }

  @ParameterizedTest
  @CsvSource({ "aa-34", "35-aa", "test", "123", "aa-aa", "00-00", "!@-@$" })
  void shouldDeleteGroupByName(String groupName) {
    Integer expectedCount = 1;
    when(groupDAO.deleteGroupByName(groupName)).thenReturn(expectedCount);
    Integer actualCount = groupService.deleteGroupByName(groupName);

    Assertions.assertEquals(expectedCount, actualCount);
    verify(groupDAO, Mockito.times(1)).deleteGroupByName(groupName);
  }

  @ParameterizedTest
  @CsvSource({ "aa-34", "35-aa", "test", "123", "aa-aa", "00-00", "!@-@$" })
  void shouldShowAllGroups(String groupName) {
    Group group = new Group(groupName);
    List<Group> groups = Collections.singletonList(group);
    when(groupDAO.showAllGroups()).thenReturn(groups);
    List<Group> actual = groupService.showAllGroups();

    Assertions.assertTrue(!groups.isEmpty() && !actual.isEmpty());
    Assertions.assertNotNull(group.getGroupName());
    Assertions.assertEquals(groups, actual);
    verify(groupDAO).showAllGroups();
  }
}
