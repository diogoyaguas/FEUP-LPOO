package railroad;

public class TGV extends Comboio {

	public TGV(String string) {
		
		super(string);
		this.tipo = "TGV";
		this.servico = new ServicoPrioritario();
	}

}
