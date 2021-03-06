package atm;

public class ATM {
	
	protected int ID;
	protected String city;
	protected String sig;
	protected int count = 0;

	public ATM(int i, String string, String string2) {
		
		this.ID = i;
		this.city = string;
		this.sig = string2;
	}

	public int getID() {
		
		return this.ID;
	}
	
	public String getCity( ) {
		
		return this.city;
	}
	
	public String getSignature() {
		
		return this.sig;
	}
	
	public int getCount() {
		
		return this.count;
	}

	public void setCount(int i) {
		
		this.count = i;	
		
	}

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
		ATM other = (ATM) obj;
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

}
