package uts.after.app;

import java.util.Scanner;
import uts.after.models.Address;
import uts.after.models.Librarian;
import uts.after.models.Member;
import uts.after.models.User;
import uts.after.services.LibraryService;

/**
 * Refactored: Extracted class to handle user-related operations (Divergent Change fix)
 */
public class UserController {
	private LibraryService service;
	private Scanner scan;
	
	public UserController(LibraryService service, Scanner scan) {
		this.service = service;
		this.scan = scan;
	}
	
	// Refactored: Extracted from long method (Long Method fix)
	public void createUser() {
		System.out.print("Enter first name: ");
		String firstName = scan.nextLine();

		System.out.print("Enter last name: ");
		String lastName = scan.nextLine();

		System.out.print("Enter street: ");
		String street = scan.nextLine();
		System.out.print("Enter village: ");
		String village = scan.nextLine();
		System.out.print("Enter district: ");
		String district = scan.nextLine();
		System.out.print("Enter municipality: ");
		String municipality = scan.nextLine();
		
		Address address = new Address(street, village, district, municipality);

		int userCategory;
		do {
			System.out.print("Enter member type (0 for Member, 1 for Librarian): ");
			userCategory = Integer.parseInt(scan.nextLine());

			if (userCategory != 0 && userCategory != 1) {
				System.out.println("Invalid type! Please enter 0 for Member or 1 for Librarian.");
			}
		} while (userCategory != 0 && userCategory != 1);

		User newUser;
		if (userCategory == 0) {
			newUser = new Member(firstName, lastName, address);
		} else {
			newUser = new Librarian(firstName, lastName, address);
		}

		service.registerUser(newUser);
		System.out.println("Member registered successfully.");
	}
	
	// Refactored: Extracted from long method (Long Method fix)
	// Refactored: Uses User.getFormattedInfo() instead of accessing Address directly (Feature Envy fix)
	public void viewMembers() {
		System.out.println("\nRegistered Members:");
		if (service.getUsers().isEmpty()) {
			System.out.println("No members found.");
			return;
		}

		for (User user : service.getUsers()) {
			System.out.println(user.getFormattedInfo());
		}
	}
}

