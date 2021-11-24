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
		for (Iterator<Table> iterator = tables.iterator(); iterator.hasNext();) {
			Table tabla = (Table) iterator.next();
			if (tabla.getName().equals(name))
				return tabla;
		}
		return null;
	}

	public Procedure getProcedure(String name) {
		for (Iterator<Procedure> iterator = procedures.iterator(); iterator.hasNext();) {
			Procedure columna = (Procedure) iterator.next();
			if (columna.getName().equals(name))
				return columna;
		}
		return null;
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
		//EL NOMBRE NO ES CONDICION PARA EVALUAR
		/*if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		} else {*/
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
			
		//}
		return true;
	}
	
	public String compare(Database other) {
	String result = "Comenzamnos con la comparacion de las base de datos\n"
			+ "\n";
	
		if (!name.equals(other.name)) {
			result += "Primero el nombre de las base de datos son distintos una se llama "+ name +" y la otra "+ other.getName()+"\n";
			
		} else {
			 result += "El nombre de las base de datos son iguales, ambas se llaman "+ name +"\n\n";
		}	 
		
		if (tables.size() == 0 &&  other.getTables().size()!=0) {
			result += "La BD1 no poseen tablas y la BD2 si posee y son :\n";
			Table table = null;
			for (Iterator iterator =  other.getTables().iterator(); iterator.hasNext();) {
				table = (Table) iterator.next();
				result+= table.toString()+"\n";
			}
		} else if (tables.size() != 0 &&  other.getTables().size()==0) {
			result += "La BD2 no poseen tablas y la BD1 si posee y son :\n";
			Table table = null;
			for (Iterator iterator =  tables.iterator(); iterator.hasNext();) {
				table = (Table) iterator.next();
				result+= table.toString()+"\n";
			}
		} else if (tables.size() == 0 &&  other.getTables().size()==0) {
			result += "ninguna de las BDs posee tablas\n";
		} else {
			if (tables.size() != other.getTables().size()) {
				result += "El tamaño de las tablas es distinto ... \n";
				if (tables.size() > other.getTables().size()) {
					result += "La cantidad de tablas de la BD1 es Mayor que la BD2 ya que posee "+tables.size()+" Y la de la BD2 "+other.getTables().size()+"\n";
				} else {
					result += "La cantidad de tablas de la BD2 es Mayor que la BD1 "+other.getTables().size()+" Y la de la BD2 "+tables.size()+ "\n";
				}
			} else {
				result += "La cantidad de tablas de las BDs es Igual\n";
			}
			result += "Ahora vamos a comparar las Tablas de ambas \n";
			Table table = null;
			Table otherTable = null;
			for (Iterator iterator = tables.iterator(); iterator.hasNext();) {
				result +="\n\n";
				table = (Table) iterator.next();
			    otherTable = other.getTable(table.getName());
				if(otherTable==null) {
					result += "La BD1 posee una tabla llamada "+ table.getName() +" Pero esa tabla en la BD2 no existe\n";
				} else {
					if (otherTable.getName().equals(table.getName())) {
						result += "Las dos BDs tienen una tabla con el nombre : "+ table.getName()+"\n";
						//result += "Veamos si ambas son iguales estructuralmente..\n";
						if (!otherTable.equals(table)) {
							result += otherTable.compare(table);
						} else {
							result += "Y Ambas son iguales estructuralmente: \n" + table.toString();
						}
					}	
				}
			}
			
			for (Iterator iterator = other.getTables().iterator(); iterator.hasNext();) {
				table = (Table) iterator.next();
			    otherTable = this.getTable(table.getName());
				if(otherTable==null) {
					result += "La BD2 posee una tabla llamada "+ table.getName() +" Pero esa tabla en la BD1 no existe\n";
				} 
			}
		}	
		result +="\n\n";	
		result += "\n\nSeguimos comparando los procedimientos/funciones de ambas BD (aclaramos que cuando"
				+ " hacemos referecia a los procedimientos tambien incluimos a las funciones)\n";
		
		if (procedures.size() == 0 &&  other.getProcedures().size()!=0) {
			result += "La BD1 no poseen procedeimientos y la BD2 si posee y son :\n";
			Procedure procedure = null;
			for (Iterator iterator = other.getProcedures().iterator(); iterator.hasNext();) {
				procedure = (Procedure) iterator.next();
				result+= procedure.toString()+"\n";
			}
		} else if (procedures.size() != 0 &&  other.getProcedures().size()==0) {
			result += "La BD2 no poseen procedeimientos y la BD1 si posee y son :\n";
			Procedure procedure = null;
			for (Iterator iterator = procedures.iterator(); iterator.hasNext();) {
				procedure = (Procedure) iterator.next();
				result+= procedure.toString()+"\n";
			}
		} else if (procedures.size() == 0 &&  other.getProcedures().size()==0) {
			result += "ninguna de las BDs posee procedimientos almacenados\n";
		}else {
			if (procedures.size() != other.getProcedures().size()) {
				result += "El tamaño de los procedimientos/funciones es distinto ... \n";
				if (procedures.size() > other.getProcedures().size()) {
					result += "El tamaño de la BD1 de procedimientos es Mayor que la BD2 ya que posee "+procedures.size()+
							" mientras que la BD2 tiene "+other.getProcedures().size()+" \n";
				} else {
					result += "El tamaño de la BD2 de procedimientos es Mayor que la BD1 ya que posee "+other.getProcedures().size()+
							" mientras que la BD1 tiene "+procedures.size()+" \n";
				}
				result += "ahora vamos a comparar los precedimientos de ambas\n";
			} else {
				result += "Las dos BDs tienen la misma cantidad de procedimientos \n Ahora vamos a comparar las Procedimientos de ambas \n";
			}	
				Procedure procedure  =null;
				Procedure otherProcedure = null;
				for (Iterator iterator = procedures.iterator(); iterator.hasNext();) {
					 procedure = (Procedure) iterator.next();
					 otherProcedure = other.getProcedure(procedure.getName());
					if(otherProcedure==null) {
						result += "La BD1 posee procedimiento llamado "+ procedure.getName() +" Pero esa procedimiento no existe en la BD2 \n";
					} else {
						if (otherProcedure.getName().equals(procedure.getName())) {
							result += "Ambas BDs tienen un procedimiento con el nombre : \n"+ procedure.getName() + "\n";
							if (!otherProcedure.equals(procedure)) {
								result += otherProcedure.compare(procedure);
							} else {
								result += "Y Los procedimientos son iguales con la siguiente estructura \n "
										+ "" + procedure.toString();
							}
						}	
					}
				}
				for (Iterator iterator2 = other.getProcedures().iterator(); iterator2.hasNext();) {
					 procedure = (Procedure) iterator2.next();
					 otherProcedure = this.getProcedure(procedure.getName());
					if(otherProcedure==null) {
						result += "La BD2 posee procedimiento llamado "+ procedure.getName() +" Pero esa procedimiento no existe en la BD1 \n";
					}
				}
		}
		result +="\n\n";
		if (this.equals(other)) {
			result += "\nEn conclucion podemos decir que las base de datos son IGUALES por todo lo mencionado anteriormente \n";
		} else {
			result += "\nEn conclucion podemos decir que las base de datos son DISTINTAS por todo lo mencionado anteriormente \n";

		}
		
		System.out.println("Aquí terminamos con la comparacion de las BDs y devolvemos el informe en la carpeta /Resultados/informe");
		return result;
	}
	
	

	

	
}
