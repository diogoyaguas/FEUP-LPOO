package railroad;

public class Carruagem {
	
	protected int lugares;

	public Carruagem(int i) {

		this.lugares = i;
	}

	public int getNumLugares() {
		
		return this.lugares;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lugares;
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
		Carruagem other = (Carruagem) obj;
		if (lugares != other.lugares)
			return false;
		return true;
	}
	
	

}
