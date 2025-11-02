package refactoring.simplifying_conditionals.replace_nested_conditional.after;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PriceCalculatorTest {

	@Test
	void testCalculatePrice() {
		PriceCalculator calculator = new PriceCalculator();
		
		assertEquals(0, calculator.calculatePrice(0, false, false));
		assertEquals(240.0, calculator.calculatePrice(3, true, true)); // 100 * 3 * 0.8
		assertEquals(270.0, calculator.calculatePrice(3, true, false)); // 100 * 3 * 0.9
		assertEquals(255.0, calculator.calculatePrice(3, false, true)); // 100 * 3 * 0.85
		assertEquals(300.0, calculator.calculatePrice(3, false, false)); // 100 * 3
	}

}

