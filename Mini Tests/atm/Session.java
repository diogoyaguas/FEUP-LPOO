package atm;

public class Session extends Countable{
	
	protected int ID;
	protected String city;
	protected String sig;
	protected int count = 0;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + count;
		result = prime * result + ((sig == null) ? 0 : sig.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (ID != other.ID)
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (count != other.count)
			return false;
		if (sig == null) {
			if (other.sig != null)
				return false;
		} else if (!sig.equals(other.sig))
			return false;
		return true;
	}

	public Session(ATM atm) {
		super(atm);
	}

	public ATM getATM() {
		
		return this.atm;
	}

	public void addTransaction(Withdrawal w) {
		
		int i = this.atm.getCount();
		i++;
		this.atm.setCount(i);
	}

	public void addTransaction(Deposit d) {
		
		int i = this.atm.getCount();
		i++;
		this.atm.setCount(i);
	}

}
