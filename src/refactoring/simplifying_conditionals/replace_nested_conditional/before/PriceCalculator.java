package refactoring.simplifying_conditionals.replace_nested_conditional.before;

public class PriceCalculator {
	
	public double calculatePrice(int quantity, boolean isPremium, boolean hasDiscount) {
		double price = 100.0;
		
		if (quantity > 0) {
			if (isPremium) {
				if (hasDiscount) {
					price = price * quantity * 0.8;
				} else {
					price = price * quantity * 0.9;
				}
			} else {
				if (hasDiscount) {
					price = price * quantity * 0.85;
				} else {
					price = price * quantity;
				}
			}
		} else {
			price = 0;
		}
		
		return price;
	}
}

