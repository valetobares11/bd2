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

	

	
}
