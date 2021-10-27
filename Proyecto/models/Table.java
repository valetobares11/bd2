package Proyecto.models;
import java.util.Set;

import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;

public class Table {

	private Set<Column> columns;
	private Set<Trigger> triggers;
	private Set<Key> keys;
	private Set<Index> indexs;
	private String name;


	//public Status status;

	public Table() {
		columns = new HashSet<Column>();
		triggers = new HashSet<Trigger>();
		keys = new HashSet<Key>();
		indexs = new HashSet<Index>();
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public Column getColumn(String name) {
		return columns.stream().filter((x) -> x.getName().equals(name)).findFirst().get();
	}
	public Index getIndex(String name) {
		return indexs.stream().filter((x) -> x.getName().equals(name)).findFirst().get();
	}
	
	public Set<Index> getIndexs() {
		return indexs;
	}

	public void setIndexs(Set<Index> indexs) {
		this.indexs = indexs;
	}

	public Trigger getTrigger(String name) {
		return triggers.stream().filter((x) -> x.getName().equals(name)).findFirst().get();
	}

	public Set<Trigger> getTriggers() {
		return triggers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Key> getKeys() {
		return keys;
	}

	public boolean addColumn(Column col) {
		return columns.add(col);
	}

	public boolean addKey(Key k) {
		return keys.add(k);
	}



	public Key getKey(Key key) {
		Optional<Key> o = keys.stream().filter(x -> x.equals(key)).findAny();
		return o.isPresent()? o.get(): null;
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
		if (!(obj instanceof Table))
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		}else {
			if(columns.size() != other.getColumns().size()) {
				return false;
			} else {
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Column column = (Column) iterator.next();
					if(!other.getColumn(column.getName()).equals(column)) {
						return false;
					}
				}	
			}
			if (triggers.size() != other.getTriggers().size()) {
				return false;
			} else {
				for (Iterator iterator = triggers.iterator(); iterator.hasNext();) {
					Trigger trigger = (Trigger) iterator.next();
					if(!other.getTrigger(trigger.getName()).equals(trigger)) {
						return false;
					}
				}
			}
			
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String r = "  Table: " + name;
		for (Column column : columns) {
			r = r + "\n    " + column.toString();
		}
		for (Key key : keys) {
			r = r + "\n     k:"+key.toString();
		}
		for (Trigger trigger : triggers) {
			r = r + "\n     T:"+trigger.toString();
		}
		for (Index index : indexs) {
			r = r + "\n     I:"+index.toString();
		}
		return r;
	}

	public void addTrigger(Trigger trigger) {
		triggers.add(trigger);
	}

	public void addIndex(Index index) {
		indexs.add(index);	
	}

	public String compare(Table other) {
		String result = "";
			if(columns.size() != other.getColumns().size()) {
				result += "Las dos tablas poseen distintas cantidad de columnas";
				if(columns.size() > other.getColumns().size()) {
					result += "la tabla " +this.name+ " de la BD1 tiene mas columnas que la tabla "+ other.getName() +" de la BD2"; 
				} else {
					result += "la tabla " +this.name+ " de la BD1 tiene menos columnas que la tabla "+ other.getName() +" de la BD2"; 
				}
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de columnas"; 
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Column column = (Column) iterator.next();
					Column columnOther = other.getColumn(column.getName());
					if(columnOther==null) {
						result += "La BD1 posee una tabla llamada "+ this.getName() +" Con una Columna llamada "+ column.getName() + ""
								+ "Pero la BD2 posee esta tabla pero esa tabla no posee esta columna";
					} else {
						if (columnOther.equals(column)) {
							result += "Ambas BDs poseen una tabla con el nombre : "+ this.getName() + " y una columna llamada "+ column.getName() ;
							result += "Veamos si ambas columnas son iguales estructuralmente..";
							if (!columnOther.equals(column)) {
								result += columnOther.compare(column);
							} else {
								result += "Ambas son iguales estructuralmente " + column.toString();
							}
						}
						
					}
				}	
			}
			/*if (triggers.size() != other.getTriggers().size()) {
				return false;
			} else {
				for (Iterator iterator = triggers.iterator(); iterator.hasNext();) {
					Trigger trigger = (Trigger) iterator.next();
					if(!other.getTrigger(trigger.getName()).equals(trigger)) {
						return false;
					}
				}
			}
			
			return true;
		}	*/
		return result;
	}
}
