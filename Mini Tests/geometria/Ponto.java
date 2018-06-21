package geometria;

public class Ponto implements Comparable<Ponto> {
	
	protected int x;
	protected int y;

	public Ponto(int i, int j) {
		
		this.x = i;
		this.y = j;
	}

	public int getX() {
		
		return this.x;
	}

	public int getY() {
		
		return this.y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public int compareTo(Ponto o) {
		
		int cmp = Integer.compare(this.x, ((Ponto) o).getX());
		if (cmp == 0) {
			cmp = Integer.compare(this.y, ((Ponto) o).getY());
		}
		return cmp;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ponto other = (Ponto) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
