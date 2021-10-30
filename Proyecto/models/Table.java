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
	public Index getIndex(Index index) {
		Optional<Index> o = indexs.stream().filter(x -> x.equals(index)).findAny();
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
					if(other.getColumn(column.getName())!=null && !other.getColumn(column.getName()).equals(column)) {
						return false;
					}
				}	
			}
			if (triggers.size() != other.getTriggers().size()) {
				return false;
			} else {
				for (Iterator iterator = triggers.iterator(); iterator.hasNext();) {
					Trigger trigger = (Trigger) iterator.next();
					if(other.getTrigger(trigger.getName())!= null && !other.getTrigger(trigger.getName()).equals(trigger)) {
						return false;
					}
				}
			}
			if (indexs.size() != other.getIndexs().size()) {
				return false;
			} else {
				for (Iterator iterator = indexs.iterator(); iterator.hasNext();) {
					Index index = (Index) iterator.next();
					if(other.getIndex(index)!= null && !other.getIndex(index).equals(index)) {
						return false;
					}
				}
			}
			if (keys.size() != other.getKeys().size()) {
				return false;
			} else {
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					Key key = (Key) iterator.next();
					if(other.getKey(key) != null && !other.getKey(key).equals(key)) {
						return false;
					}
				}
			}
		}
		return true;
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
				result += "Las dos tablas poseen distintas cantidad de columnas\n";
				if(columns.size() > other.getColumns().size()) {
					result += "La tabla " +this.name+ " de la BD1 tiene mas columnas que la tabla "+ other.getName() +" de la BD2 "
							 + "Posee "+ columns.size()+ "y la otra "+ other.getIndexs().size()+ "\n"; 
				} else {
					result += "La tabla " +this.name+ " de la BD1 tiene menos columnas que la tabla "+ other.getName() +" de la BD2 "
							+ "Posee "+ other.getColumns().size()+ "y la otra "+ columns.size()+ "\n" ; 
				}
				Set<Column> columnasIguales = new HashSet<Column>();
				Set<Column> columnasbd1 = new HashSet<Column>();
				Set<Column> columnasbd2 = new HashSet<Column>();
				
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Column column = (Column) iterator.next();
					Column column2 = other.getColumn(column.getName());
					if (column2!=null && !column.equals(column2)) {
						columnasbd1.add(column);
					} else {
						columnasIguales.add(column);
					}
				}
				for (Iterator iterator = other.getColumns().iterator(); iterator.hasNext();) {
					Column column = (Column) iterator.next();
					Column column2 = this.getColumn(column.getName());
					if (column2!=null && !column.equals(column2)) {
						columnasbd2.add(column);
					} 
				}
				result += "A continuacion mostramos las columnas equivalentes y diferentes\n";
				if (columnasIguales.size() >0) {
					result +=  "Las columnas iguales son :\n";
					for (Iterator iterator = columnasIguales.iterator(); iterator.hasNext();) {
						Column table = (Column) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (columnasbd1.size() >0) {
					result+= "Las columnas de la tabla "+this.getName()+" de la BD1 diferentes de la tabla "+other.getName()+" de la BD2 son :";
					for (Iterator iterator = columnasbd1.iterator(); iterator.hasNext();) {
						Column table = (Column) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (columnasbd2.size() >0) {
					result+= "Las columnas de la tabla "+this.getName()+" de la BD2 diferentes de la tabla "+other.getName()+" de la BD1 son :";
					for (Iterator iterator = columnasbd2.iterator(); iterator.hasNext();) {
						Column table = (Column) iterator.next();
						result += table.toString()+"\n";
					}
				}
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de columnas \n"
						+ "Ahora veamos sus columnas \n"; 
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Column column = (Column) iterator.next();
					Column columnOther = other.getColumn(column.getName());
					if(columnOther==null) {
						result += "La BD1 con tabla "+ this.getName() +"Posee una Columna llamada "+ column.getName() + ""
								+ "Pero la BD2 con esta tabla no posee esta columna \n";
					} else {
						if (columnOther.getName().equals(column.getName())) {
							result += "Ambas BDs con la tabla : "+ this.getName() + " poseen una columna llamada "+ column.getName() ;
							result += "Veamos si ambas columnas son iguales estructuralmente.. \n";
							if (!columnOther.equals(column)) {
								
								result += columnOther.compare(column);
							} else {
								result += "Ambas son iguales estructuralmente con la siguiente estructura" + column.toString()+ "\n";
							}
						}
						
					}
				}	
			}
			result += "Ahora comparemos entre los triggers de ambas tablas \n";
			if (triggers.size() != other.getTriggers().size()) {
				result += " Las tablas poseen distintas cantidad de triggers \n"; 
				if(triggers.size() > other.getTriggers().size()) {
					result += "La tabla " +this.name+ " de la BD1 tiene mas triggers  que la tabla "+ other.getName() +" de la BD2"
							+ "Posee "+ triggers.size()+ "y la otra "+ other.getTriggers().size() + "\n"; 
				} else {
					result += "La tabla " +other.getName()+ " de la BD2 tiene mas triggers  que la tabla "+ name +" de la BD1"
							+ "Posee "+ other.getTriggers().size()+ "y la otra "+ triggers.size() + "\n"; 
				}
				Set<Trigger> columnasIguales = new HashSet<Trigger>();
				Set<Trigger> columnasbd1 = new HashSet<Trigger>();
				Set<Trigger> columnasbd2 = new HashSet<Trigger>();
				
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Trigger column = (Trigger) iterator.next();
					Trigger column2 = other.getTrigger(column.getName());
					if (column2!=null && !column.equals(column2)) {
						columnasbd1.add(column);
					} else {
						columnasIguales.add(column);
					}
				}
				for (Iterator iterator = other.getTriggers().iterator(); iterator.hasNext();) {
					Trigger column = (Trigger) iterator.next();
					Trigger column2 = this.getTrigger(column.getName());
					if (column2!=null && !column.equals(column2)) {
						columnasbd2.add(column);
					} 
				}
				result += "A continuacion mostramos los triggers equivalentes y diferentes\n";
				if (columnasIguales.size() >0) {
					result +=  "Las trigger iguales son :\n";
					for (Iterator iterator = columnasIguales.iterator(); iterator.hasNext();) {
						Trigger table = (Trigger) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (columnasbd1.size() >0) {
					result+= "Los trigger de la tabla "+this.getName()+" de la BD1 diferentes de la tabla "+other.getName()+" de la BD2 son :";
					for (Iterator iterator = columnasbd1.iterator(); iterator.hasNext();) {
						Trigger table = (Trigger) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (columnasbd2.size() >0) {
					result+= "Los trigger de la tabla "+this.getName()+" de la BD2 diferentes de la tabla "+other.getName()+" de la BD1 son :";
					for (Iterator iterator = columnasbd2.iterator(); iterator.hasNext();) {
						Trigger table = (Trigger) iterator.next();
						result += table.toString()+"\n";
					}
				}
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de triggers \n"
						+ "Ahora veamos sus triggers \n"; 
				for (Iterator iterator = triggers.iterator(); iterator.hasNext();) {
					Trigger trigger = (Trigger) iterator.next();
					Trigger otherTrigger = other.getTrigger(trigger.getName());
					if(otherTrigger==null) {
						result += "La BD1 con tabla: "+ this.getName() +" Poseen un Trigger llamado "+ trigger.getName() + ""
								+ "Pero la BD2 con esta tabla no posee este trigger \n";
					} else {
						if (otherTrigger.getName().equals(trigger.getName())) {
							result += "Ambas BDs con tabla : "+ this.getName() + " Poseen un trigger llamado "+ trigger.getName() ;
							result += "Veamos si ambos triggers son iguales estructuralmente.. \n";
							if (!otherTrigger.equals(trigger)) {
								result += otherTrigger.compare(trigger);
							} else {
								result += "Ambos Triggers son iguales estructuralmente con la siguiente estructura :\n" + trigger.toString() + "\n";
							}
						}
						
					}
				}
			}
			result += "Ahora comparemnos entre los indices de ambas tablas \n";
			if (indexs.size() != other.getIndexs().size()) {
				result += " Las tablas poseen distintas cantidad de indices \n"; 
				if(indexs.size() > other.getIndexs().size()) {
					result += "la tabla " +this.name+ " de la BD1 tiene mas indices  que la tabla "+ other.getName() +" de la BD2"
							+ "Posee "+ indexs.size()+ "y la otra "+ other.getIndexs().size() + "\n"; 
				} else {
					result += "la tabla " +other.getName()+ " de la BD2 tiene mas indices  que la tabla "+ name +" de la BD1"
							+ "Posee "+ other.getIndexs().size()+ "y la otra "+ indexs.size() + "\n"; 
				}
				Set<Index> columnasIguales = new HashSet<Index>();
				Set<Index> columnasbd1 = new HashSet<Index>();
				Set<Index> columnasbd2 = new HashSet<Index>();
				
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Index column = (Index) iterator.next();
					Index column2 = other.getIndex(column);
					if (column2!=null && !column.equals(column2)) {
						columnasbd1.add(column);
					} else {
						columnasIguales.add(column);
					}
				}
				for (Iterator iterator = other.getIndexs().iterator(); iterator.hasNext();) {
					Index column = (Index) iterator.next();
					Index column2 = this.getIndex(column);
					if (column2!=null && !column.equals(column2)) {
						columnasbd2.add(column);
					} 
				}
				result += "A continuacion mostramos los indices equivalentes y diferentes\n";
				if (columnasIguales.size() >0) {
					result +=  "Las indices iguales son :\n";
					for (Iterator iterator = columnasIguales.iterator(); iterator.hasNext();) {
						Index table = (Index) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (columnasbd1.size() >0) {
					result+= "Los indices de la tabla "+this.getName()+" de la BD1 diferentes de la tabla "+other.getName()+" de la BD2 son :";
					for (Iterator iterator = columnasbd1.iterator(); iterator.hasNext();) {
						Index table = (Index) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (columnasbd2.size() >0) {
					result+= "Los indices de la tabla "+this.getName()+" de la BD2 diferentes de la tabla "+other.getName()+" de la BD1 son :";
					for (Iterator iterator = columnasbd2.iterator(); iterator.hasNext();) {
						Index table = (Index) iterator.next();
						result += table.toString()+"\n";
					}
				}
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de indices \n"
						+ "Ahora veamos sus indices \n"; 
				for (Iterator iterator = indexs.iterator(); iterator.hasNext();) {
					Index index = (Index) iterator.next();
					Index otherIndex = other.getIndex(index);
					if(otherIndex==null) {
						result += "La BD1 con tabla "+ this.getName() +" Posee un Index llamado "+ index.getName() + ""
								+ "Pero la BD2 con esta tabla no posee este index \n";
					} else {
						if (otherIndex.getName().equals(index.getName())) {
							result += "Ambas BDs con tabla : "+ this.getName() + " Poseen un index llamado "+ index.getName() ;
							result += "Veamos si ambos indices son iguales estructuralmente.. \n";
							if (!otherIndex.equals(index)) {
								result += otherIndex.compare(index);
							} else {
								result += "Ambos Indices son iguales estructuralmente con la siguiente estructura :\n" + index.toString() + "\n";
							}
						}
						
					}
				}
			}
			result += "Ahora vamos a  comparar entre las Claves de ambas tablas \n";
			if (keys.size() != other.getKeys().size()) {
				result += " Las tablas poseen distintas cantidad de Claves \n"; 
				if(keys.size() > other.getKeys().size()) {
					result += "la tabla " +this.name+ " de la BD1 tiene mas claves que la tabla "+ other.getName() +" de la BD2"
							+ "Posee "+ keys.size()+ "y la otra "+ other.getKeys().size()+ "\n"; 
				} else {
					result += "la tabla " +other.getName()+ " de la BD2 tiene mas claves que la tabla "+ name +" de la BD1"
							+ "Posee "+ other.getKeys().size()+ "y la otra "+ keys.size() + "\n"; 
				}
				Set<Key> columnasIguales = new HashSet<Key>();
				Set<Key> columnasbd1 = new HashSet<Key>();
				Set<Key> columnasbd2 = new HashSet<Key>();
				
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					Key column = (Key) iterator.next();
					Key column2 = other.getKey(column);
					if (column2!=null && !column.equals(column2)) {
						columnasbd1.add(column);
					} else {
						columnasIguales.add(column);
					}
				}
				for (Iterator iterator = other.getKeys().iterator(); iterator.hasNext();) {
					Key column = (Key) iterator.next();
					Key column2 = this.getKey(column);
					if (column2!=null && !column.equals(column2)) {
						columnasbd2.add(column);
					} 
				}
				result += "A continuacion mostramos las claves equivalentes y diferentes\n";
				if (columnasIguales.size() >0) {
					result +=  "Las Claves iguales son :\n";
					for (Iterator iterator = columnasIguales.iterator(); iterator.hasNext();) {
						Key table = (Key) iterator.next();
						result +=table.toString()+"\n";
					}
				}
				if (columnasbd1.size() >0) {
					result+= "Las claves de la tabla "+this.getName()+" de la BD1 diferentes de la tabla "+other.getName()+" de la BD2 son :";
					for (Iterator iterator = columnasbd1.iterator(); iterator.hasNext();) {
						Key table = (Key) iterator.next();
						result += table.toString()+"\n";
					}
				}
				if (columnasbd2.size() >0) {
					result+= "Las Claves de la tabla "+this.getName()+" de la BD2 diferentes de la tabla "+other.getName()+" de la BD1 son :";
					for (Iterator iterator = columnasbd2.iterator(); iterator.hasNext();) {
						Key table = (Key) iterator.next();
						result += table.toString()+"\n";
					}
				}
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de Claves \n"
						+ "Ahora veamos sus Claves \n"; 
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					Key key = (Key) iterator.next();
					Key otherKey = other.getKey(key);
					if(otherKey==null) {
						result += "La BD1 con tabla "+ this.getName() +" Posee una clave llamada "+ key.getKeyName() + ""
								+ "Pero la BD2 con esta tabla No posee una key similar con la misma estructura \n";
						result += otherKey.compare(key)+ "\n";
					} else {
						result += "Ambas Claves son iguales estructuralmente con la siguiente estructura por parte de la key "+ key.getKeyName()+ ""
								+ " de la BD1 con esta tabla \n"+ key.toString() +" \n"
								+ " Y la clave "+ otherKey.getKeyName()+ " de la BD2 con la misma tabla \n"+otherKey.toString() + "\n";
						}
						
					}
			}
			
		return result;
	}
}
