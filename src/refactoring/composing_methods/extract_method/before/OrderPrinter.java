package refactoring.composing_methods.extract_method.before;

import java.util.List;

public class OrderPrinter {
	
	public void printOrder(List<OrderItem> items, String customerName) {
		double total = 0;
		
		// Print header
		System.out.println("====================");
		System.out.println("Order Receipt");
		System.out.println("====================");
		
		// Print items
		for (OrderItem item : items) {
			double price = item.getPrice() * item.getQuantity();
			System.out.println(item.getName() + " x" + item.getQuantity() + " = $" + price);
			total += price;
		}
		
		// Print footer
		System.out.println("--------------------");
		System.out.println("Total: $" + total);
		System.out.println("Customer: " + customerName);
		System.out.println("====================");
	}
}

