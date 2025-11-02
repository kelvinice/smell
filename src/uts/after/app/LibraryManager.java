package uts.after.app;

import java.util.Scanner;
import uts.after.services.LibraryService;

/**
 * LMSapp class provides a terminal-based menu system to manage library
 * operations such as adding members, inserting books, calculating fines, and
 * viewing registered members.
 * 
 * Refactored to fix:
 * - Long Method: Extracted methods into separate controllers
 * - Divergent Change: Separated responsibilities into UserController, BookController, PaymentController, MenuDisplay
 */

public class LibraryManager {

	private LibraryService service = new LibraryService();
	private Scanner scan = new Scanner(System.in);
	private UserController userController;
	private BookController bookController;
	private PaymentController paymentController;
	private MenuDisplay menuDisplay;

	public LibraryManager() {
		userController = new UserController(service, scan);
		bookController = new BookController(service, scan);
		paymentController = new PaymentController(scan);
		menuDisplay = new MenuDisplay();
	}

	/**
	 * Displays the main menu and handles user input. The loop continues until the
	 * user chooses to exit. 
	 * You can't run the application here. But via app.Main.java
	 * 
	 * Refactored: Much shorter and delegates to specialized controllers
	 */

	public void startApp() {
		while (true) {
			menuDisplay.showMenu();

			int choice = scan.nextInt();
			scan.nextLine();

			// Refactored: Delegates to specialized controllers (Long Method & Divergent Change fix)
			switch (choice) {
				case 1:
					userController.createUser();
					break;
				case 2:
					userController.viewMembers();
					break;
				case 3:
					bookController.addBook();
					break;
				case 4:
					paymentController.calculateFine();
					break;
				case 5:
					System.out.println("Exiting Library System...");
					return;
				default:
					System.out.println("Invalid option, please try again.");
			}
		}
	}

}


