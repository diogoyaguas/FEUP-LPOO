package circuits;

public class GateNot extends LogicGate {

	public GateNot(LogicVariable output, LogicVariable input1) throws ColisionException, CycleException {

		this.inputs = new LogicVariable[1];
		if(output.getCalculatedBy() != null) {
			throw new ColisionException();
		}
		
		if(input1.getCalculatedBy().getInputs()[0] == output || input1.getCalculatedBy().getInputs()[1] == output) {
			throw new CycleException();
		}
		
		this.output = output;
		this.output.setCalculatedBy(this);
		this.input1 = input1;
		this.inputs[0] = input1;
	}
	
	@Override
	public String getSymbol() {
		
		return "NOT";
	}
	
	@Override
	public String getFormula() {
		
		return "NOT("+inputs[0].getFormula()+")";
	}
	
	@Override
	public boolean getValue() {
		return (!inputs[0].getValue()); 
	}

}
