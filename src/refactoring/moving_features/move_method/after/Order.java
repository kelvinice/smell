package refactoring.moving_features.move_method.after;

import java.util.List;

public class Order {
	private String productName;
	private double price;
	private int quantity;
	
	public Order(String productName, double price, int quantity) {
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	// Method moved here - it's more appropriate since it operates on Orders
	public static double calculateTotal(List<Order> orders) {
		double total = 0;
		if (orders != null) {
			for (Order order : orders) {
				total += order.getPrice() * order.getQuantity();
			}
		}
		return total;
	}
}

