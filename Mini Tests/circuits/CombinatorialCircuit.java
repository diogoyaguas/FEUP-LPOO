package circuits;

import java.util.ArrayList;

public class CombinatorialCircuit {
	
	protected ArrayList<LogicVariable> circuits = new ArrayList<>();

	public Object addVariable(LogicVariable a) {
		
		for(int i = 0; i < circuits.size(); i++) {
			
			if(circuits.get(i).getName() == a.getName()) {
				
				return false;
			}
		}
		
		circuits.add(a);
		return true;
	}

	public LogicVariable getVariableByName(String string) {
		
		for(int i = 0; i < circuits.size(); i++) {
			
			if(circuits.get(i).getName() == string) {
				
				return circuits.get(i);
			}
		}
		return null;
	}

}
