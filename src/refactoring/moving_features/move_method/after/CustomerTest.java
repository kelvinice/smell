package refactoring.moving_features.move_method.after;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CustomerTest {

	@Test
	void testGetTotalPrice() {
		Customer customer = new Customer("John Doe");
		List<Order> orders = new ArrayList<>();
		orders.add(new Order("Apple", 1.50, 3));
		orders.add(new Order("Banana", 0.75, 5));
		customer.setOrders(orders);
		
		double expected = (1.50 * 3) + (0.75 * 5);
		assertEquals(expected, customer.getTotalPrice());
	}

}

