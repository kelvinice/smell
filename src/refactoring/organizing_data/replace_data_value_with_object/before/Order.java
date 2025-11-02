package refactoring.organizing_data.replace_data_value_with_object.before;

public class Order {
	private String customerName;
	private String customerEmail;
	private String productName;
	private double price;
	
	public Order(String customerName, String customerEmail, String productName, double price) {
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.productName = productName;
		this.price = price;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public String getCustomerEmail() {
		return customerEmail;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double getPrice() {
		return price;
	}
	
	// Customer data is scattered as primitive strings
}

