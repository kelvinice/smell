package refactoring.moving_features.move_method.after;

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
	
	// Now delegates to Order class
	public double getTotalPrice() {
		return Order.calculateTotal(orders);
	}
}

