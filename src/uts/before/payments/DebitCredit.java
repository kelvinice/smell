package uts.before.payments;

public class DebitCredit extends Electronic {
	private String cardNumber;
	
	public DebitCredit(double amount, String transactionId, String cardNumber) {
		super(amount, transactionId);
		this.cardNumber = cardNumber;
	}

	@Override
	public void processPayment() {
		System.out.println("Processing debit/credit card payment of Rp" + amount + 
                " using card: " + cardNumber + " (Transaction ID: " + transactionId + ")");
	}

}
