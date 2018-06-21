package geometria;

public class FiguraComposta extends Figura {

	protected Figura[] figuras;
	
	public FiguraComposta(Figura[] figuras) {
		this.figuras = figuras;
	}

	@Override
	public double getArea() {
		
		double total = 0;
		for(Figura s: figuras) {
			
			total+=s.getArea();
		}
		return total;
	}

	@Override
	public double getPerimetro() {
		
		double total = 0;
		for(Figura s: figuras) {
			
			total+=s.getPerimetro();
		}
		return total;
	}
	
	@Override
	public int count() {
		return figuras.length;
	}

}
