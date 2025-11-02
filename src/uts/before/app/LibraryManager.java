package uts.before.app;

import java.util.Scanner;

import uts.before.models.Address;
import uts.before.models.Book;
import uts.before.models.User;
import uts.before.payments.Cash;
import uts.before.services.LibraryService;

/**
 * LMSapp class provides a terminal-based menu system to manage library
 * operations such as adding members, inserting books, calculating fines, and
 * viewing registered members.
 */

public class LibraryManager {

	private LibraryService service = new LibraryService();
	private Scanner scan = new Scanner(System.in);

	/**
	 * Displays the main menu and handles user input. The loop continues until the
	 * user chooses to exit. 
	 * You can't run the application here. But via app.Main.java
	 */

	public void startApp() {
		while (true) {
			System.out.println("");
			System.out.println("=== Welcome to Elsunib Lib! ==");
			System.out.println("1. Create New User");
			System.out.println("2. View Registered Member");
			System.out.println("3. Insert New Book");
			System.out.println("4. Calculate Member Fine");
			System.out.println("5. Exit");
			System.out.println("==============================");
			System.out.print("Choose an option: ");

			int choice = scan.nextInt();
			scan.nextLine();

			// For adding new user, it can be a Librarian or Member
			if (choice == 1) {
				System.out.print("Enter first name: ");
				String firstName = scan.nextLine();

				System.out.print("Enter last name: ");
				String lastName = scan.nextLine();

				Address address = new Address();
				System.out.print("Enter street: ");
				address.setStreet(scan.nextLine());
				System.out.print("Enter village: ");
				address.setVillage(scan.nextLine());
				System.out.print("Enter district: ");
				address.setDistrict(scan.nextLine());
				System.out.print("Enter municipality: ");
				address.setMunicipality(scan.nextLine());

				int userCategory;
				do {
					System.out.print("Enter member type (0 for Member, 1 for Librarian): ");
					userCategory = Integer.parseInt(scan.nextLine());

					if (userCategory != User.MEMBER && userCategory != User.LIBRARIAN) {
						System.out.println("Invalid type! Please enter 0 for Member or 1 for Librarian.");
					}
				} while (userCategory != User.MEMBER && userCategory != User.LIBRARIAN);

				User newUser = new User();
				newUser.setFirstName(firstName);
				newUser.setLastName(lastName);
				newUser.setAddress(address);
				newUser.setUserCategory(userCategory);

				service.registerUser(newUser);
				System.out.println("Member registered successfully.");

			}
			
			// For Viewing Registered Members
			else if (choice == 2) {
				System.out.println("\nRegistered Members:");
				if (service.getUsers().isEmpty()) {
					System.out.println("No members found.");
					return;
				}

				for (User user : service.getUsers()) {
					System.out.println("Name: " + user.getFirstName() + " " + user.getLastName() 
				    + ", Type: " + user.getUserCategory() + ", Address: " + user.getAddress().getStreet() + ", " + 
			                   user.getAddress().getVillage() + ", " +
			                   user.getAddress().getDistrict() + ", " +
			                   user.getAddress().getMunicipality());
				}
			}

			// For adding a new book
			else if (choice == 3) {
				System.out.print("Enter book title: ");
		        String title = scan.nextLine();

		        System.out.print("Enter book author: ");
		        String author = scan.nextLine();

		        System.out.print("Enter publication year: ");
		        int year = Integer.parseInt(scan.nextLine());

		        System.out.print("Enter ISBN: ");
		        String isbn = scan.nextLine();

		        Book newBook = new Book(title, author, year, isbn);
		        service.addBook(newBook);

		        System.out.println("Book inserted successfully.");
			}

			// For Logging Member's Fines
			else if (choice == 4) {
				System.out.print("Enter Fine Amount: ");
		        double fineAmount = scan.nextDouble();
		        scan.nextLine();
		        
		        System.out.println("Processing fine payment using Cash...");
		        Cash cashPayment = new Cash(fineAmount);
		        cashPayment.processPayment();
		        System.out.println("Fine paid successfully!");
			}

			// Nothing, just let them exit this program.
			else if (choice == 5) {
				System.out.println("Exiting Library System...");
				break;
			} else {
				System.out.println("Invalid option, please try again.");
			}
		}

	}

}
