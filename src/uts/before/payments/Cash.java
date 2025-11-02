package uts.before.payments;

public class Cash extends Traditional {

	public Cash(double amount) {
		super(amount);
	}

	@Override
	public void processPayment() {
		System.out.println("Processing cash payment of Rp" + amount);
		
	}

}
