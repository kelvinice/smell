package uts.after.app;

import java.util.Scanner;
import uts.after.payments.Cash;

/**
 * Refactored: Extracted class to handle payment-related operations (Divergent Change fix)
 */
public class PaymentController {
	private Scanner scan;
	
	public PaymentController(Scanner scan) {
		this.scan = scan;
	}
	
	// Refactored: Extracted from long method (Long Method fix)
	public void calculateFine() {
		System.out.print("Enter Fine Amount: ");
        double fineAmount = scan.nextDouble();
        scan.nextLine();
        
        System.out.println("Processing fine payment using Cash...");
        Cash cashPayment = new Cash(fineAmount);
        cashPayment.processPayment();
        System.out.println("Fine paid successfully!");
	}
}


