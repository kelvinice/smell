package refactoring.simplifying_conditionals.replace_nested_conditional.after;

public class PriceCalculator {
	
	public double calculatePrice(int quantity, boolean isPremium, boolean hasDiscount) {
		// Guard clause for invalid quantity
		if (quantity <= 0) {
			return 0;
		}
		
		// Guard clause for premium with discount
		if (isPremium && hasDiscount) {
			return 100.0 * quantity * 0.8;
		}
		
		// Guard clause for premium without discount
		if (isPremium) {
			return 100.0 * quantity * 0.9;
		}
		
		// Guard clause for regular with discount
		if (hasDiscount) {
			return 100.0 * quantity * 0.85;
		}
		
		// Default case
		return 100.0 * quantity;
	}
}

