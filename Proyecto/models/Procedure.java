package Proyecto.models;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Procedure {

	private String name;
	private List<String[]> parameters; //[(nombre del Parametro), (tipo de parametro IN,OUT...), (tipo de parametro VARCHAR,INT..) ]
	private String procedure_return;

	public Procedure(String name, String procedure_return) {
		this.name = name;
	
		this.procedure_return = procedure_return;
		parameters = new ArrayList<String[]>();
	}
	
	public Procedure(String name) {
		this.name = name;
		parameters = new ArrayList<String[]>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean constainsParameter(String[] param) {
		for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
			String[] parameter = (String[]) iterator.next();
			if (parameter[2].equals(param[2]) && parameter[1].equals(param[1])) {
				return true;
			}
		}
		return false;
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
		} else if (!name.equals(other.name)) {
			return false;
		} else {
			if (parameters.size() != other.getParameters().size()) {
				return false;
			}
			for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
				String[] parameter = (String[]) iterator.next();
				if (!other.constainsParameter(parameter)) {
					return false;
				}
			}
			
		}
		return true;
				//procedure_return.equals(other.getProcedure_return());
	}

	@Override
	public String toString() {
		return "Procedure [name=" + name + ", parameters=" + parameters + ", procedure_return=" + procedure_return+ "]\n";
	}

	public String compare(Procedure procedure) {
		String result = "";
		Procedure other = (Procedure) procedure;
		if (this.name.equals(other.name)) {
				//result += "Ambos poseen el procedimiento con el nombre "+this.name+".\n";
				if (parameters.size() != other.getParameters().size()) {
					if (parameters.size() < other.getParameters().size()) {
						result += "La cantidad de paramatros es distinta en cada procemiento, "
								  +"el procedimiento de la bd1 tiene menos parametros que la bd2.\n";
					}else {
						result += "La cantidad de paramatros es distinta en cada procemiento, "
								  +"el procedimiento de la bd2 tiene menos parametros que la bd1.\n";
					}
					
				}else {
					
					result += "Los procedimientos tienen la misma cantidad de parametros.\n"; 
				}
				result += "Vamos a comparar los procedimientos de cada Parametro.\n";
					for (Iterator iterator = parameters.iterator(); iterator.hasNext();) {
						String[] parameter = (String[]) iterator.next();
						if (!other.constainsParameter(parameter)) {
							result += "El parametro "+ parameter[2]+" "+parameter[0]+" de tipo "+parameter[1]+" no pertenese al procedimiento de la bd2, "
									+ "es decir que no se encontró otro parametro que tenga el mismo perfil \n";
						}
					}
					
					for (Iterator iterator = other.parameters.iterator(); iterator.hasNext();) {
						String[] parameter = (String[]) iterator.next();
						if (!this.constainsParameter(parameter)) {
							result += "El parametro "+ parameter[2]+" "+parameter[0]+" de tipo "+parameter[1]+"  no pertenese al procedimiento de la bd1"
									+ "es decir que no se encontró otro parametro que tenga el mismo perfil \n";
						}
					}
		}
		return result;
	}

}
