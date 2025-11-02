package uts.after.app;

import java.util.Scanner;
import uts.after.models.Book;
import uts.after.services.LibraryService;

/**
 * Refactored: Extracted class to handle book-related operations (Divergent Change fix)
 */
public class BookController {
	private LibraryService service;
	private Scanner scan;
	
	public BookController(LibraryService service, Scanner scan) {
		this.service = service;
		this.scan = scan;
	}
	
	// Refactored: Extracted from long method (Long Method fix)
	public void addBook() {
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
}


