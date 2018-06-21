package railroad;

import java.util.ArrayList;

public class Comboio {
	
	protected String name;
	protected String tipo;
	protected int lugares;
	protected int passageiros;
	protected ArrayList<Carruagem> carruagens = new ArrayList<>();
	protected ServicoABordo servico;

	public Comboio(String string) {
		
		this.name = string;
		this.passageiros = 0;
		this.tipo = "Comboio";
		this.servico = new ServicoRegular();
	}

	public String getNome() {
		
		return this.name;
	}

	public int getNumLugares() {
		
		return this.lugares;
	}

	public int getNumCarruagens() {
		
		return carruagens.size();
	}

	public void addCarruagem(Carruagem a1) {
		
		carruagens.add(a1);
		this.lugares+=a1.lugares;
		
	}

	public Carruagem getCarruagemByOrder(int i) {
		
		return carruagens.get(i);
	}

	public void removeAllCarruagens(int i) {
		
		for(int j = 0; j < carruagens.size(); j++) {
			
			if(carruagens.get(j).getNumLugares() == i) {
				
				this.lugares-=i;
				carruagens.remove(j);
				j--;
			}
		}
		
	}

	public int getNumPassageiros() {
		
		return this.passageiros;
	}

	public int getLugaresLivres() {
		
		return lugares - passageiros;
	}

	public void addPassageiros(int i) throws PassengerCapacityExceeded {
		
		if(this.getLugaresLivres() < i) {
			
			throw new PassengerCapacityExceeded();
		}
		this.passageiros+=i;
		
	}

	public void removePassageiros(int i) throws PassengerCapacityExceeded {
		
		if(passageiros < i) {
			
			throw new PassengerCapacityExceeded();
		}
		this.passageiros-=i;
		
	}

	public void removePassageiros() {
		
		this.passageiros = 0;
		
	}
	
	public String toString() {
		
		String comboio;
		String carruagem;
		String lugar;
		String passageiro;
		
		comboio = this.tipo + " " + this.name;
		if(this.getNumCarruagens() > 1 || this.getNumCarruagens() == 0) {
			
			carruagem = +this.getNumCarruagens() + " carruagens";
		}else {
			
			carruagem = +this.getNumCarruagens() + " carruagem";
		}
		
		if(this.getNumLugares() > 1 || this.getNumLugares() == 0) {
			lugar = +this.getNumLugares() + " lugares";
		}else
			lugar = +this.getNumLugares() + " lugar";
		
		if(this.getNumPassageiros() > 1 || this.getNumPassageiros() == 0) {
			passageiro = +this.getNumPassageiros() + " passageiros";
		}else
			passageiro = +this.getNumPassageiros() + " passageiro";
		
		return comboio + ", " + carruagem + ", " + lugar + ", " + passageiro;
	}

	@Override
	public boolean equals(Object obj) {
		if(getNumCarruagens() != ((Comboio) obj).getNumCarruagens())
			return false;
		
		for(int i = 0 ; i < getNumCarruagens(); i++) {
			if(carruagens.get(i).getNumLugares() != ((Comboio) obj).getCarruagemByOrder(i).getNumLugares())
				return false;
		}
		
		return true;
	}

	public ServicoABordo getServicoABordo() {
		
		return this.servico;
	}
	
	public String getDescricaoServico() {
		return servico.getDescricaoServico();
	}
	
	public void setServicoABordo(ServicoABordo servico) {
		this.servico = servico;
	}

}
