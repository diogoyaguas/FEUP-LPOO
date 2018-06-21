package circuits;

public class GateAnd extends LogicGate {

	public GateAnd(LogicVariable output, LogicVariable input1, LogicVariable input2) throws ColisionException {
		
		this.inputs = new LogicVariable[2];
		if(output.getCalculatedBy() != null) {
			
			throw new ColisionException();
		}
		this.output = output;
		this.output.setCalculatedBy(this);
		this.input1 = input1;	
		this.input2 = input2;
		this.inputs[0] = input1;
		this.inputs[1] = input2;
	}
	
	@Override
	public String getSymbol() {
		
		return "AND";
	}
	
	@Override
	public String getFormula() {
		
		return "AND("+inputs[0].getFormula()+","+inputs[1].getFormula()+")";
	}
	
	@Override
	public boolean getValue() {
		this.value = (inputs[0].getValue() && inputs[1].getValue());
		return (inputs[0].getValue() && inputs[1].getValue()); 
	}

}
