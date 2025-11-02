package uts.after.app;

/**
 * Refactored: Extracted class to handle menu display (Divergent Change fix)
 */
public class MenuDisplay {
	
	public void showMenu() {
		System.out.println("");
		System.out.println("=== Welcome to Elsunib Lib! ==");
		System.out.println("1. Create New User");
		System.out.println("2. View Registered Member");
		System.out.println("3. Insert New Book");
		System.out.println("4. Calculate Member Fine");
		System.out.println("5. Exit");
		System.out.println("==============================");
		System.out.print("Choose an option: ");
	}
}


