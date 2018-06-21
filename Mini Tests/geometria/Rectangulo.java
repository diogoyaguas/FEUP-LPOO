package geometria;

public class Rectangulo extends Figura {
	
	protected int a1;
	protected int a2;
	protected int a3;
	protected int a4;

	public Rectangulo(int x1, int y1, int x2, int y2) {
		
		this.a1 = x1;
		this.a2 = y1;
		this.a3 = x2;
		this.a4 = y2;
	}

	@Override
	public double getArea() {
		return (a4-a2)*(a3-a1);
	}
	
	public double getPerimetro() {
		
		return 2*(a3 - a1) + 2*(a4-a2);
	}

	@Override
	int count() {
		
		return 1;
	}

}
