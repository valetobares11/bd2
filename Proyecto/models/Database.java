package Proyecto.models;
import java.util.Set;



import java.util.HashSet;
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

	

	

	
}
