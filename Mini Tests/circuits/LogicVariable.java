package circuits;

public class LogicVariable {
	
	protected String name;
	protected boolean value;
	protected LogicGate obj;

	public LogicVariable(String string, boolean b) {
		
		this.name = string;
		this.value = b;
	}

	public LogicVariable(String string) {
		
		this.name = string;
	}

	public String getName() {
		
		return this.name;
	}

	public boolean getValue() {
		if(this.getCalculatedBy() == null)
			return value;
		
		return this.getCalculatedBy().getValue();
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (value ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogicVariable other = (LogicVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	public void setCalculatedBy(LogicGate obj) {
		
		this.obj = obj;
	}

	public LogicGate getCalculatedBy() {
		
		return this.obj;
	}

	public String getFormula() {
		
		if(this.getCalculatedBy() == null)
			return this.getName();
		
		return this.getCalculatedBy().getFormula();
	}
	
	

}
