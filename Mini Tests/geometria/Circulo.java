package geometria;

public class Circulo extends Figura {
	
	protected Ponto centro;
	protected int raio;

	public Circulo(Ponto p, int i) throws IllegalArgumentException {
		if( i <0 ) {
			
			throw new IllegalArgumentException();
		}
		this.centro = p;
		this.raio = i;
	}

	public int getRaio() {
		
		return this.raio;
	}

	public Ponto getCentro() {
		
		return this.centro;
	}
	
	public double getArea() {
		return Math.PI * raio * raio;
	}
	
	public double getPerimetro() {
		
		return 2*Math.PI * raio;
	}

	@Override
	int count() {
		
		return 1;
	}

}
