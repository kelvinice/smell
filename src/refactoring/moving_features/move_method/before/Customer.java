package refactoring.moving_features.move_method.before;

import java.util.List;

public class Customer {
	private String name;
	private List<Order> orders;
	
	public Customer(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	// Method that should be moved to Order class
	public double getTotalPrice() {
		double total = 0;
		if (orders != null) {
			for (Order order : orders) {
				total += order.getPrice() * order.getQuantity();
			}
		}
		return total;
	}
}

