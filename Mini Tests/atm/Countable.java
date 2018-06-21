package atm;

public class Countable {
	
	protected ATM atm;
	
	public Countable(ATM atm) {
		
		this.atm = atm;
	}

	public int count() {
		
		return this.atm.getCount();
	}

}
