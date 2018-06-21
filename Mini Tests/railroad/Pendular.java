package railroad;

public class Pendular extends Comboio {

	public Pendular(String string) {
		super(string);
		this.tipo = "Pendular";
		this.servico = new ServicoSemEnjoos();
	}

}
