package circuits;

public class GateOr extends LogicGate {

	public GateOr(LogicVariable output, LogicVariable input1, LogicVariable input2) throws ColisionException {
		
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
		
		return "OR";
	}
	
	@Override
	public String getFormula() {
		
		return "OR("+inputs[0].getFormula()+","+inputs[1].getFormula()+")";
	}
	
	@Override
	public boolean getValue() {
		if(inputs[0].getValue()) {
			return true;
		}
		else if(inputs[1].getValue()) {
			return true;
		}
		return false;
	}

}
