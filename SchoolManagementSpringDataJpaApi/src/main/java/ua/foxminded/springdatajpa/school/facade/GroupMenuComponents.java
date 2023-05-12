package ua.foxminded.springdatajpa.school.facade;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.springdatajpa.school.dao.service.GroupService;
import ua.foxminded.springdatajpa.school.entity.Group;

@Slf4j
@Component
public class GroupMenuComponents {
	private final GroupService groupService;

	public GroupMenuComponents(GroupService groupService) {
		this.groupService = groupService;
	}

	public void findGroupsWithLessOrEqualsStudentsFacade(Scanner scan) {
		log.info(MenuConstants.NUMBER_OF_STUDENTS);

		if (scan.hasNextInt()) {
			int quant = scan.nextInt();
			groupService.findGroupsWithLessOrEqualsStudents(quant).stream().map(Group::toString).forEach(log::info);
		} else {
			log.warn(MenuConstants.DIGITS_REQUIRED);
		}
	}

	public void createGroupFacade(Scanner scan) {
		log.info(MenuConstants.GROUP_NAME);
		String groupName = scan.nextLine();

		if (!groupName.isEmpty()
				&& groupService.showAllGroups().stream().noneMatch(group -> group.getGroupName().equals(groupName))) {
			Group group = new Group(groupName);
			log.info("Course with ID:" + groupService.createGroup(group) + " added" + "\n" + MenuConstants.GROUP_MENU);
		} else {
			log.warn(MenuConstants.EMPTY_NOTE);
			log.info(MenuConstants.GROUP_MENU);
		}
	}

	public void editGroupNameFacade(Scanner scan) {
		log.info(MenuConstants.CHOOSE_GROUP_NAME);
		groupService.showAllGroups().forEach(group -> log.info(group.toString()));
		String groupName = scan.nextLine();

		if (groupService.showAllGroups().stream().anyMatch(group -> group.getGroupName().equals(groupName))) {
			log.info(MenuConstants.NEW_GROUP_NAME);
			String newGroupName = scan.nextLine();
			log.info("Group: " + groupName + " updated, new Group name: "
					+ groupService.editGroupName(groupName, newGroupName).getGroupName() + "\n"
					+ MenuConstants.GROUP_MENU);
		} else {
			log.warn(MenuConstants.NO_SUCH_GROUP);
			log.info(MenuConstants.GROUP_MENU);
		}
	}

	public void deleteGroupByNameFacade(Scanner scan) {
		log.info(MenuConstants.CHOOSE_GROUP_NAME);
		groupService.showAllGroups().forEach(group -> log.info(group.toString()));
		String groupName = scan.nextLine();

		if (groupService.showAllGroups().stream().anyMatch(group -> group.getGroupName().equals(groupName))) {
			log.info(groupService.deleteGroupByName(groupName) + " group deleted" + "\n" + MenuConstants.GROUP_MENU);
		} else {
			log.warn(MenuConstants.NO_SUCH_GROUP);
			log.info(MenuConstants.GROUP_MENU);
		}
	}

	public void showAllGroupsFacade() {
		groupService.showAllGroups().forEach(group -> log.info(group.toString()));
		log.info(MenuConstants.GROUP_MENU);
	}

}
