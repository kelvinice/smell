package refactoring.organizing_data.replace_data_value_with_object.after;

public class Order {
	private Customer customer;
	private String productName;
	private double price;
	
	public Order(Customer customer, String productName, double price) {
		this.customer = customer;
		this.productName = productName;
		this.price = price;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double getPrice() {
		return price;
	}
	
	// Customer data is now encapsulated in a Customer object
}

