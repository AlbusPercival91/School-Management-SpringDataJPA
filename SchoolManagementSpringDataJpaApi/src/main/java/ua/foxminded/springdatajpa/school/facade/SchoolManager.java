package ua.foxminded.springdatajpa.school.facade;

import java.util.Scanner;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SchoolManager {
	private MainMenuComponents mainMenu;

	public SchoolManager(MainMenuComponents mainMenu) {
		this.mainMenu = mainMenu;
	}

	public void manage() {
		Scanner scan = new Scanner(System.in);
		String command = "";
		log.info("Welcome to School console application. Please choose options below:");
		log.info(MenuConstants.MAIN_MENU);

		while (!command.equalsIgnoreCase("q")) {
			command = scan.nextLine();

			if (command.equalsIgnoreCase("a")) {
				command = mainMenu.studentMenu(scan);
			} else if (command.equalsIgnoreCase("b")) {
				command = mainMenu.groupMenu(scan);
			} else if (command.equalsIgnoreCase("c")) {
				command = mainMenu.courseMenu(scan);
			}
		}
		scan.close();
	}

}
