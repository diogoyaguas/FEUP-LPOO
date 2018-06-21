package circuits;

public abstract class LogicGate {
	
	protected LogicVariable inputs[];
	protected LogicVariable output;
	protected LogicVariable input1;
	protected LogicVariable input2;
	protected boolean value;

	public LogicVariable getOutput() {
		
		return output;
	}

	public LogicVariable[] getInputs() {
		
		return this.inputs.clone();
	}
	
	public String getSymbol() {
		
		return "";
	}

	public String getFormula() {
		
		return "";
	}
	
	public boolean getValue() {
		return this.value;
	}

}
