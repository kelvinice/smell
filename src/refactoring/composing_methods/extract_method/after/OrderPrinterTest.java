package refactoring.composing_methods.extract_method.after;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class OrderPrinterTest {

	@Test
	void testPrintOrder() {
		OrderPrinter printer = new OrderPrinter();
		List<OrderItem> items = new ArrayList<>();
		items.add(new OrderItem("Apple", 1.50, 3));
		items.add(new OrderItem("Banana", 0.75, 5));
		
		// This test verifies the method can be called without errors
		// In a real scenario, you might use OutputStream to capture output
		printer.printOrder(items, "John Doe");
		assertTrue(true); // Method executed successfully
	}

}

