package Proyecto.models;
import java.util.Set;



import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public class Database {

	private Set<Table> tables;
	private Set<Procedure> procedures;
	private String name;
	//private Status status;

	public Database(String name) {
		this.name = name;
		tables = new HashSet<Table>();
		procedures = new HashSet<Procedure>();
	}

	public Set<Table> getTables() {
		return tables;
	}

	public String getName() {
		return name;
	}

	public Table getTable(String name) {
		return tables.stream().filter((x) -> x.getName().equals(name)).findFirst().get();
	}

	public Procedure getProcedure(String name) {
		Optional<Procedure> op = procedures.stream().filter((x) -> x.getName().equals(name)).findFirst();
		return op.isPresent() ? op.get() : null;
	}

	public boolean addTable(Table table) {
		return tables.add(table);
	}

	public Set<Procedure> getProcedures() {
		return procedures;
	}

	public boolean addProcedure(Procedure proc) {
		return procedures.add(proc);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Database))
			return false;
		Database other = (Database) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		} else {
			if (tables.size() != other.getTables().size()) {
				return false;
			} else {
				for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
					Table table = (Table) iterator.next();
					if(!other.getTable(table.getName()).equals(table)) {
						return false;
					}
				}
				if (procedures.size() != other.getProcedures().size()) {
					return false;
				} else {
					
				}
				
			}
			
		}
		return false;
	}
	
	public String compare(Database other) {
	String result = "Comenzamnos con la comparacion de las base de datos\n";
		if (!name.equals(other.name)) {
			result += "Primero el nombre de las base de datos son distintos una se llama "+ name +" y la otra "+ other.getName()+"\n";
			
		} else {
			 result += "El nombre de las base de datos son iguales ambas se llaman "+ name +"\n";
			if (tables.size() != other.getTables().size()) {
				result += "El tamaño de las tablas es distinto ... \n";
				if (tables.size() > other.getTables().size()) {
					result += "El tamaño de la BD1 de tablas es Mayor que la BD2 \n";
				} else {
					result += "El tamaño de la BD2 de tablas es Mayor que la BD1 \n";
				}
			} else {
				result += "Ahora vamos a comparar las Tablas de ambas \n";
				for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
					Table table = (Table) iterator.next();
					Table otherTable = other.getTable(table.getName());
					if(otherTable==null) {
						result += "La BD1 posee una tabla llamada "+ table.getName() +" Pero esa tabla en la BD2 no existe";
					} else {
						if (otherTable.equals(table)) {
							result += "Ambas BDs poseen una tabla con el nombre : "+ table.getName();
							result += "Veamos si ambas son iguales estructuralmente..";
							if (!otherTable.equals(table)) {
								result += otherTable.compare(table);
							} else {
								result += "Ambas son iguales estructuralmente " + table.toString();
							}
						}	
					}
				}
				/*if (procedures.size() != other.getProcedures().size()) {
					return false;
				} else {
					
				}*/
				
			}
			
		}
		return result;
	}
	
	

	

	
}
