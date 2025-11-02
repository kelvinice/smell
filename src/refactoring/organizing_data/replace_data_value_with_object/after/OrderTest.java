package refactoring.organizing_data.replace_data_value_with_object.after;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OrderTest {

	@Test
	void testOrderWithCustomerObject() {
		Customer customer = new Customer("John Doe", "john@example.com");
		Order order = new Order(customer, "Laptop", 999.99);
		
		assertEquals("John Doe", order.getCustomer().getName());
		assertEquals("john@example.com", order.getCustomer().getEmail());
		assertEquals("Laptop", order.getProductName());
		assertEquals(999.99, order.getPrice());
	}

}

