package atm;

public class Account {
	
	protected String name;
	protected double balance;

	public Account(String string, double d) {
		
		this.name = string;
		this.balance = d;
	}

	public String getName() {
		
		return this.name;
	}

	public double getBalance() {
		
		return this.balance;
	}

}
