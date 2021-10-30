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
					for (Iterator iterator = procedures.iterator(); iterator.hasNext();) {
						Procedure procedure = (Procedure) iterator.next();
						if(!other.getProcedure(procedure.getName()).equals(procedure)) {
							return false;
						}
					}
				}
			}		
			
		}
		return true;
	}
	
	public String compare(Database other) {
	String result = "Comenzamnos con la comparacion de las base de datos\n";
		if (!name.equals(other.name)) {
			result += "Primero el nombre de las base de datos son distintos una se llama "+ name +" y la otra "+ other.getName()+"\n";
			
		} else {
			 result += "El nombre de las base de datos son iguales, ambas se llaman "+ name +"\n"
			 		+ "Seguimos con las Tablas";
			if (tables.size() != other.getTables().size()) {
				result += "El tamaño de las tablas es distinto ... \n";
				if (tables.size() > other.getTables().size()) {
					result += "El tamaño de la BD1 de tablas es Mayor que la BD2 \n";
				} else {
					result += "El tamaño de la BD2 de tablas es Mayor que la BD1 \n";
				}
				
				Set<Table> tablasIguales = new HashSet<Table>();
				Set<Table> tablasbd1 = new HashSet<Table>();
				Set<Table> tablasbd2 = new HashSet<Table>();
				
				for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
					Table table = (Table) iterator.next();
					Table table2 = other.getTable(table.getName());
					if (table2!=null && !table.equals(table2)) {
						tablasbd1.add(table);
					} else {
						tablasIguales.add(table);
					}
				}
				for (Iterator iterator = other.getTables().iterator(); iterator.hasNext();) {
					Table table = (Table) iterator.next();
					Table table2 = this.getTable(table.getName());
					if (table2!=null && !table.equals(table2)) {
						tablasbd2.add(table);
					} 
				}
				
				result += "Por lo tanto las  dos Base de Datos son distintas a continuacion mostramos las tablas equivalentes y diferentes\n"
						+ "Las tablas iguales son :\n";
				if (tablasIguales.size() >0) {
					for (Iterator iterator = tablasIguales.iterator(); iterator.hasNext();) {
						Table table = (Table) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (tablasbd1.size() >0) {
					result+= "Las tablas de la BD1 diferentes de la BD2 son :";
					for (Iterator iterator = tablasbd1.iterator(); iterator.hasNext();) {
						Table table = (Table) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (tablasbd2.size() >0) {
					result+= "Las tablas de la BD2 diferentes de la BD1 son :";
					for (Iterator iterator = tablasbd2.iterator(); iterator.hasNext();) {
						Table table = (Table) iterator.next();
						result += table.toString()+"\n";
					}
				}
				
			} else {
				result += "Ahora vamos a comparar las Tablas de ambas \n";
				for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
					Table table = (Table) iterator.next();
					Table otherTable = other.getTable(table.getName());
					if(otherTable==null) {
						result += "La BD1 posee una tabla llamada "+ table.getName() +" Pero esa tabla en la BD2 no existe\n";
					} else {
						if (otherTable.getName().equals(table.getName())) {
							result += "Ambas BDs poseen una tabla con el nombre : "+ table.getName()+"\n";
							result += "Veamos si ambas son iguales estructuralmente..\n";
							if (!otherTable.equals(table)) {
								result += otherTable.compare(table);
							} else {
								result += "Ambas son iguales estructuralmente \n" + table.toString();
							}
						}	
					}
				}
				
			}
			
			result += "Seguimos comparando los procedimientos/funciones de ambas BD (aclaramos que cuando"
					+ " hacemos referecia a los procedimientos tambien incluimos a las funciones)\n";
			if (procedures.size() != other.getProcedures().size()) {
				result += "El tamaño de los procedimientos/funciones es distinto ... \n";
				if (procedures.size() > other.getProcedures().size()) {
					result += "El tamaño de la BD1 de procedimientos es Mayor que la BD2 \n";
				} else {
					result += "El tamaño de la BD2 de procedimientos es Mayor que la BD1 \n";
				}
				
				Set<Procedure> proceduresIguales = new HashSet<Procedure>();
				Set<Procedure> proceduresbd1 = new HashSet<Procedure>();
				Set<Procedure> proceduresbd2 = new HashSet<Procedure>();
				
				for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
					Procedure table = (Procedure) iterator.next();
					Procedure table2 = other.getProcedure(table.getName());
					if (table2!=null && !table.equals(table2)) {
						proceduresbd1.add(table);
					} else {
						proceduresIguales.add(table);
					}
				}
				for (Iterator iterator = other.getProcedures().iterator(); iterator.hasNext();) {
					Procedure table = (Procedure) iterator.next();
					Procedure table2 = this.getProcedure(table.getName());
					if (table2!=null && !table.equals(table2)) {
						proceduresbd2.add(table);
					} 
				}
				
				result += "Por lo tanto las  dos Base de Datos son distintas a continuacion mostramos los procedimientos equivalentes y diferentes\n"
						+ "Los procedimientos iguales son :\n";
				if (proceduresIguales.size() >0) {
					for (Iterator iterator = proceduresIguales.iterator(); iterator.hasNext();) {
						Procedure table = (Procedure) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (proceduresbd1.size() >0) {
					result+= "Los procedimientos de la BD1 diferentes de la BD2 son :";
					for (Iterator iterator = proceduresbd1.iterator(); iterator.hasNext();) {
						Procedure table = (Procedure) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (proceduresbd2.size() >0) {
					result+= "Los procedimientos de la BD2 diferentes de la BD1 son :";
					for (Iterator iterator = proceduresbd2.iterator(); iterator.hasNext();) {
						Procedure table = (Procedure) iterator.next();
						result += table.toString()+"\n";
					}
				}
			} else {
				result += "Ahora vamos a comparar las Procedimientos de ambas \n";
				for (Iterator iterator = procedures.iterator(); iterator.hasNext();) {
					Procedure procedure = (Procedure) iterator.next();
					Procedure otherProcedure = other.getProcedure(procedure.getName());
					if(otherProcedure==null) {
						result += "La BD1 posee procedimiento llamado "+ procedure.getName() +" Pero esa procedimiento no existe en la BD2 \n";
					} else {
						if (otherProcedure.getName().equals(procedure.getName())) {
							result += "Ambas BDs tienen un procedimiento con el nombre : \n"+ procedure.getName();
							result += "Veamos si ambos son iguales estructuralmente..\n";
							if (!otherProcedure.equals(procedure)) {
								result += otherProcedure.compare(procedure);
							} else {
								result += "Ambos son iguales estructuralmente con la siguiente: \n "
										+ "" + procedure.toString();
							}
						}	
					}
				}
				
			}
		}
		
		if (this.equals(other)) {
			result += "En conclucion podemos decir que las base de datos son iguales por todo lo mencionado anteriormente \n";
		} else {
			result += "En conclucion podemos decir que las base de datos son distintas por todo lo mencionado anteriormente \n";

		}
		
		System.out.println("Aquí terminamos con la comparacion de las BDs y devolvemos el informe");
		return result;
	}
	
	

	

	
}
