package atm;

public class Deposit {
	
	protected ATM atm;
	protected Session s;
	protected Card c;
	protected int i;

	public Deposit(ATM atm, Session s, Card c, int i) {
		
		this.atm = atm;
		this.s = s;
		this.c = c;
		this.i = i;
	}

}
