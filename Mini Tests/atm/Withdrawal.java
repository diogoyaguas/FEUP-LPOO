package atm;

public class Withdrawal {
	
	protected ATM atm;
	protected Session s;
	protected Card c;
	protected double amount;

	public Withdrawal(ATM atm, Session s, Card c, float i) {
		
		this.atm = atm;
		this.s = s;
		this.c = c;
		this.amount = i;
	}

	public void setAmount(double d) {
	
		this.amount = d;
		
	}

	public double getAmount() {
		
		return this.amount;
	}

	@Override
	public String toString() {
		return "Withdrawal at ATM " +this.atm.getID() + " (" + this.atm.getCity() + ", " + this.atm.getSignature() + ") of " + +getAmount() + "0";
	}
	
	

}
