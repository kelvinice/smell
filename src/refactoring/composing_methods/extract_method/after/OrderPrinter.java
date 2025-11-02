package refactoring.composing_methods.extract_method.after;

import java.util.List;

public class OrderPrinter {
	
	public void printOrder(List<OrderItem> items, String customerName) {
		printHeader();
		double total = printItems(items);
		printFooter(total, customerName);
	}
	
	private void printHeader() {
		System.out.println("====================");
		System.out.println("Order Receipt");
		System.out.println("====================");
	}
	
	private double printItems(List<OrderItem> items) {
		double total = 0;
		for (OrderItem item : items) {
			double price = item.getPrice() * item.getQuantity();
			System.out.println(item.getName() + " x" + item.getQuantity() + " = $" + price);
			total += price;
		}
		return total;
	}
	
	private void printFooter(double total, String customerName) {
		System.out.println("--------------------");
		System.out.println("Total: $" + total);
		System.out.println("Customer: " + customerName);
		System.out.println("====================");
	}
}

