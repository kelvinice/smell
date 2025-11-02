package uts.before.payments;

public abstract class Electronic extends PaymentMethod {
    protected String transactionId;

	public Electronic(double amount, String transactionId) {
		super(amount);
		this.transactionId = transactionId;
	}

}
