package Proyecto.models;
import java.util.ArrayList;
import java.util.List;


public class Procedure {

	//public Status status;
	private String name;
	private List<String[]> parameters;
	private String procedure_return;

	public Procedure(String name, String procedure_return) {
		this.name = name;
	
		this.procedure_return = procedure_return;
		parameters = new ArrayList<String[]>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String[]> getParameters() {
		return parameters;
	}

	public void setParameters(List<String[]> parameters) {
		this.parameters = parameters;
	}

	public String getProcedure_return() {
		return procedure_return;
	}

	public void setProcedure_return(String procedure_return) {
		this.procedure_return = procedure_return;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Procedure other = (Procedure) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Procedure [name=" + name + ", parameters=" + parameters + ", procedure_return=" + procedure_return
				+ "]";
	}

}
