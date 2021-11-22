package Proyecto.models;
import java.util.Set;

//import sun.swing.MenuItemLayoutHelper.ColumnAlignment;

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
		for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
			Column columna = (Column) iterator.next();
			if (columna.getName().equals(name))
				return columna;
		}
		return null;
	}
	
	public Set<Index> getIndexs() {
		return indexs;
	}

	public void setIndexs(Set<Index> indexs) {
		this.indexs = indexs;
	}

	public Trigger getTrigger(String name) {
		for (Iterator<Trigger> iterator = triggers.iterator(); iterator.hasNext();) {
			Trigger columna = (Trigger) iterator.next();
			if (columna.getName().equals(name))
				return columna;
		}
		return null;
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
		for (Iterator<Key> iterator = keys.iterator(); iterator.hasNext();) {
			Key key2 = (Key) iterator.next();
			if (key2.equals(key))
				return key2;
		}
		return null;
	}
	public Index getIndex(Index index) {
		for (Iterator<Index> iterator = indexs.iterator(); iterator.hasNext();) {
			Index columna = (Index) iterator.next();
			if (columna.equals(index))
				return columna;
		}
		return null;
	}

	public Set<Key> getPrimaryKeys() {
		Set<Key> primaryKeys = new HashSet<>();
		for (Iterator<Key> iterator = keys.iterator(); iterator.hasNext();) {
			Key key = iterator.next();
			if (key.isPrimary())
				primaryKeys.add(key);
		}
		return primaryKeys;
		
	}	
	
	public Key getKeyPorNombre(String nombreKey) {
		for (Iterator<Key> iterator = keys.iterator(); iterator.hasNext();) {
			Key key = iterator.next();
			if (key.getName().equals(nombreKey))
				return key;
		}
		return null;
	}
	
	public Set<Key> getUniqueKeys() {
		Set<Key> uniqueKeys = new HashSet<>();
		for (Iterator<Key> iterator = keys.iterator(); iterator.hasNext();) {
			Key key = iterator.next();
			if (key.isUnique())
				uniqueKeys.add(key);
		}
		return uniqueKeys;
	}
	
	public Set<Key> getForeignKeys() {
		Set<Key> foreignKeys = new HashSet<>();
		for (Iterator<Key> iterator = keys.iterator(); iterator.hasNext();) {
			Key key = iterator.next();
			if (key.isForeink())
				foreignKeys.add(key);
		}
		return foreignKeys;
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
		} else {
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
					if(other.getIndex(index)== null ) {
						return false;
					}
				}
			}
			
			if (keys.size() != other.getKeys().size()) {
				return false;
			} else {
				
				for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
					Key key = (Key) iterator.next();
					if(other.getKey(key)== null) {
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
			} else {
				result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de columnas \n";
			}
				result +=  "Ahora veamos sus columnas \n"; 
				Column column = null;
				Column columnOther = null;
				for (Iterator iterator = columns.iterator(); iterator.hasNext();) {
					 column = (Column) iterator.next();
					 columnOther = other.getColumn(column.getName());
					if(columnOther==null) {
						result += "La BD1 con tabla "+ this.getName() +"Posee una Columna llamada "+ column.getName() + "\n"
								+ "Pero la BD2 con esta tabla no posee esta columna \n";
					} else {
						if (columnOther.getName().equals(column.getName())) {
							result += "Ambas BDs con la tabla : "+ this.getName() + " poseen una columna llamada "+ column.getName() +"\n";
							result += "Veamos si ambas columnas son iguales estructuralmente.. \n";
							if (!columnOther.equals(column)) {
								result += columnOther.compare(column);
							} else {
								result += "Ambas son iguales estructuralmente con la siguiente estructura" + column.toString()+ "\n";
							}
						}
						
					}
				}
				for (Iterator iterator = other.getColumns().iterator(); iterator.hasNext();) {
					column = (Column) iterator.next();
					columnOther = this.getColumn(column.getName());
					if(columnOther==null) {
						result += "La BD2 con tabla "+ this.getName() +"Posee una Columna llamada "+ column.getName() + "\n"
								+ "Pero la BD1 con esta tabla no posee esta columna \n";
					}		
				}
				
			result +="\n";
			result += "Ahora comparemos entre los triggers de ambas tablas \n";
			if (triggers.size() == 0 &&  other.getTriggers().size()!=0) {
				result += "La BD1 con tabla "+this.name+" no posee triggers y la BD2 con la misma tabla si posee y son :\n";
				Trigger trigger = null;
				for (Iterator iterator =  other.getTriggers().iterator(); iterator.hasNext();) {
					trigger = (Trigger) iterator.next();
					result+= trigger.toString()+"\n";
				}
			} else if (triggers.size() != 0 &&  other.getTriggers().size()==0) {
				result += "La BD2 con tabla "+this.name+" no posee triggers y la BD1 con la misma tabla si posee y son :\n";
				Trigger trigger = null;
				for (Iterator iterator =  triggers.iterator(); iterator.hasNext();) {
					trigger = (Trigger) iterator.next();
					result+= trigger.toString()+"\n";
				}
			} else if (triggers.size() == 0 &&  other.getTriggers().size()==0) {
				result += "ninguna de las BDs con tabla "+this.name+" poseen triggers \n";
			} else {
				if (triggers.size() != other.getTriggers().size()) {
					result += " Las tablas poseen distintas cantidad de triggers \n"; 
					if(triggers.size() > other.getTriggers().size()) {
						result += "La tabla " +this.name+ " de la BD1 tiene mas triggers  que la tabla "+ other.getName() +" de la BD2"
								+ "Posee "+ triggers.size()+ "y la otra "+ other.getTriggers().size() + "\n"; 
					} else {
						result += "La tabla " +other.getName()+ " de la BD2 tiene mas triggers  que la tabla "+ name +" de la BD1"
								+ "Posee "+ other.getTriggers().size()+ "y la otra "+ triggers.size() + "\n"; 
					}
				} else {
					result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de triggers \n";
				}
				result+= "Ahora veamos sus triggers \n"; 
				Trigger trigger = null;
				Trigger otherTrigger = null;
				for (Iterator iterator = triggers.iterator(); iterator.hasNext();) {
				    trigger = (Trigger) iterator.next();
				    otherTrigger = other.getTrigger(trigger.getName());
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
					for (Iterator iterator = other.getTriggers().iterator(); iterator.hasNext();) {
					    trigger = (Trigger) iterator.next();
					    otherTrigger = this.getTrigger(trigger.getName());
						if(otherTrigger==null) {
							result += "La BD2 con tabla: "+ this.getName() +" Poseen un Trigger llamado "+ trigger.getName() + ""
									+ "Pero la BD1 con esta tabla no posee este trigger \n";
						}
					}	
			}
			
			
			result +="\n";
			result += "Ahora comparemos entre los indices de ambas tablas \n";
			if (indexs.size() == 0 &&  other.getIndexs().size()!=0) {
				result += "La BD1 con tabla "+this.name+" no poseen indices y la BD2 con la misma tabla si posee y son :\n";
				Index index = null;
				for (Iterator iterator =  other.getIndexs().iterator(); iterator.hasNext();) {
					index = (Index) iterator.next();
					result+= index.toString()+"\n";
				}
			} else if (indexs.size() != 0 &&  other.getIndexs().size()==0) {
				result += "La BD2 con tabla "+this.name+" no poseen indices y la BD1 con la misma tabla si posee y son :\n";
				Index index = null;
				for (Iterator iterator =  indexs.iterator(); iterator.hasNext();) {
					index = (Index) iterator.next();
					result+= index.toString()+"\n";
				}
			} else if (indexs.size() == 0 &&  other.getIndexs().size()==0) {
				result += "ninguna de las BDs con tabla "+this.name+" posee Indices\n";
			} else {
				if (indexs.size() != other.getIndexs().size()) {
					result += " Las tablas poseen distintas cantidad de indices \n"; 
					if(indexs.size() > other.getIndexs().size()) {
						result += "la tabla " +this.name+ " de la BD1 tiene mas indices  que la tabla "+ other.getName() +" de la BD2\n"
								+ "Posee "+ indexs.size()+ "y la otra "+ other.getIndexs().size() + "\n"; 
					} else {
						result += "la tabla " +other.getName()+ " de la BD2 tiene mas indices  que la tabla "+ name +" de la BD1\n"
								+ "Posee "+ other.getIndexs().size()+ "y la otra "+ indexs.size() + "\n"; 
					}
					
				} else {
					result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de indices \n";
				}
					result+= "Ahora veamos sus indices \n"; 
					Index index = null;
					Index otherIndex = null;
					for (Iterator iterator = indexs.iterator(); iterator.hasNext();) {
						index = (Index) iterator.next();
						 otherIndex = other.getIndex(index);
						if(otherIndex==null) {
							result += "La BD1 con tabla "+ this.getName() +" Posee un Index llamado "+ index.getName() + "\n"
									+ " que esta asociado a la columna "+index.getColumn()+" "
									+ "Pero la BD2 con esta tabla no posee este index \n";
						} else {
							if (otherIndex.getName().equals(index.getName())) {
								result += "Ambas BDs con tabla : "+ this.getName() + " Poseen un index llamado "+ index.getName()+"\n" ;
								result += "Veamos si ambos indices son iguales estructuralmente.. \n";
								if (!otherIndex.equals(index)) {
									result += otherIndex.compare(index);
								} else {
									result += "Ambos Indices son iguales estructuralmente con la siguiente estructura :\n" + index.toString() + "\n";
								}
							}
							
						}
					}	
						for (Iterator iterator2 = other.getIndexs().iterator(); iterator2.hasNext();) {
							index = (Index) iterator2.next();
							 otherIndex = getIndex(index);
							if(otherIndex==null) {
								result += "En cambio la BD2 con tabla "+ this.getName() +" Posee un Index llamado "+ index.getName() + ""
										+ " que esta asociado a la columna "+index.getColumn()+"\n "
										+ "Que la BD1 con esta tabla no posee. \n";
							}
						}
			}
			
			result +="\n";
			result += "Ahora vamos a comparar entre las Claves de ambas tablas \n";
			result += "Arrancamos con las claves primarias\n";
			Set<Key> primaryKeys = this.getPrimaryKeys();
			Set<Key> otherPrimaryKeys = other.getPrimaryKeys();
			if (primaryKeys.size() == 0 &&  otherPrimaryKeys.size()!=0) {
				result += "La BD1 con tabla "+this.name+" no poseen claves primarias y la BD2 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  otherPrimaryKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (primaryKeys.size() != 0 &&  otherPrimaryKeys.size()==0) {
				result += "La BD2 con tabla "+this.name+" no poseen claves primarias y la BD2 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  primaryKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (primaryKeys.size() == 0 &&  otherPrimaryKeys.size()==0) {
				result += "ninguna de las BDs con tabla "+this.name+" posee Claves Primarias\n";
			} else {
				if (primaryKeys.size() != otherPrimaryKeys.size()) {
					result += " Las tablas poseen distintas cantidad de claves primarias \n"; 
					if(primaryKeys.size() > otherPrimaryKeys.size()) {
						result += "la tabla " +this.name+ " de la BD1 tiene mas claves primarias que la tabla "+ other.getName() +" de la BD2\n"
								+ "Posee "+ primaryKeys.size()+ "y la otra "+ otherPrimaryKeys.size()+ "\n"; 
					} else {
						result += "la tabla " +other.getName()+ " de la BD2 tiene mas claves primarias que la tabla "+ name +" de la BD1\n"
								+ "Posee "+ otherPrimaryKeys.size()+ "y la otra "+ primaryKeys.size() + "\n"; 
					}
				} else {
					result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de Claves primarias \n";
				}	
				
						result+= "Ahora veamos sus Claves primarias \n"; 
				Key key =null;
				Key otherKey = null;
				
				for (Iterator<Key> iterator = primaryKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
						otherKey = other.getKey(key);
						if(otherKey == null) {
							result += "La BD1 con tabla "+ this.getName() +" Posee una clave primaria llamada "+ key.getKeyName() + "que esta asociada\n";
							if (key.getColumns().size()==1) {
								result += "a la columna:"+ key.getColumns().toString()+ "\n";
							} else {
								result += "a las columnas "+ key.getColumns().toString() +"\n";
							}
							result+= "Pero la BD2 con esta tabla No posee una key con la misma estructura \n";
							for (Iterator<Key> iterator2 = otherPrimaryKeys.iterator(); iterator.hasNext();) {
								otherKey = (Key) iterator2.next();
								result += key.compare(otherKey);
							}
						} else {
							result += "Ambas Claves son iguales estructuralmente con la siguiente estructura por parte de la key "+ key.getKeyName()+ ""
									+ " de la BD1 con esta tabla \n"+ key.toString() +" \n"
									+ " Y la clave "+ otherKey.getKeyName()+ " de la BD2 con la misma tabla \n"+otherKey.toString() + "\n";
						}
				}
					for (Iterator<Key> iterator2 = otherPrimaryKeys.iterator(); iterator2.hasNext();) {
						key = (Key) iterator2.next();
						if (key != null) {	
							otherKey = this.getKey(key);
							if(otherKey == null) {
								result += "La BD2 con tabla "+ other.getName() +" Posee una clave primaria llamada "+ key.getKeyName() + "que esta asociada";
								if (key.getColumns().size()==1) {
									result += "a la columna:"+ key.getColumns().toString();
								} else {
									result += "a las columnas "+ key.getColumns().toString();
								}
								result+= "Pero la BD1 con esta tabla No posee una key similar con la misma estructura \n";
							}
						}
					
					}
			}
		
				
			result +="\n\n";
			Set<Key> uniqueKeys = this.getUniqueKeys();
			Set<Key> otherUniqueKeys = other.getUniqueKeys();
			result += "Continuamos con las claves secundarias\n";
			if (uniqueKeys.size() == 0 &&  otherUniqueKeys.size()!=0) {
				result += "La BD1 con tabla "+this.name+" no poseen claves secundarias y la BD2 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  otherUniqueKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (uniqueKeys.size() != 0 &&  otherUniqueKeys.size()==0) {
				result += "La BD2 con tabla "+this.name+" no poseen claves secundarias y la BD1 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  uniqueKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (uniqueKeys.size() == 0 &&  otherUniqueKeys.size()==0) {
				result += "ninguna de las BDs con tabla "+this.name+" posee Claves Secundarias \n";
			} else {
				if (uniqueKeys.size() != otherUniqueKeys.size()) {
					result += " Las tablas poseen distinta cantidad de claves secundarias \n"; 
					if(uniqueKeys.size() > otherUniqueKeys.size()) {
						result += "la tabla " +this.name+ " de la BD1 tiene mas claves primarias que la tabla "+ other.getName() +" de la BD2\n"
								+ "Posee "+ uniqueKeys.size()+ "y la otra "+ otherUniqueKeys.size()+ "\n"; 
					} else {
						result += "la tabla " +other.getName()+ " de la BD2 tiene mas claves primarias que la tabla "+ name +" de la BD1\n"
								+ "Posee "+ otherUniqueKeys.size()+ "y la otra "+ uniqueKeys.size() + "\n"; 
					}
				} else {
					result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de Claves secundarias \n";
				}	
				
					result+= "Ahora veamos sus Claves secundarias \n"; 
					Key key = null;
					Key otherKey = null;
					for (Iterator<Key> iterator = uniqueKeys.iterator(); iterator.hasNext();) {
					    key = (Key) iterator.next();
					    otherKey = other.getKey(key);
						if(otherKey == null) {
							result += "La BD1 con tabla "+ this.getName() +" Posee una clave secundaria llamada "+ key.getKeyName() + ""
									+ "Pero la BD2 con esta tabla No posee una key exactamente igual \n";
							result += "procedemos a comparar esta clave con las otras \n";
							for (Iterator<Key> iterator2 = otherUniqueKeys.iterator(); iterator.hasNext();) {
								otherKey = (Key) iterator2.next();
								result += key.compare(otherKey);
							}
						} else {
							result += "Ambas Claves son iguales estructuralmente con la siguiente estructura por parte de la key "+ key.getKeyName()+ ""
									+ " de la BD1 con esta tabla \n"+ key.toString() +" \n"
									+ " Y la clave "+ otherKey.getKeyName()+ " de la BD2 con la misma tabla \n"+otherKey.toString() + "\n";
						}	
					}
						for (Iterator<Key> iterator = otherUniqueKeys.iterator(); iterator.hasNext();) {
						  	key = (Key) iterator.next();
						    otherKey = getKey(key);
							if(otherKey == null) {
								result += "La BD2 con tabla "+ this.getName() +" Posee una clave secundaria llamada "+ key.getKeyName() + ""
										+ "Pero la BD1 con esta tabla No posee una key similar con la misma estructura \n";
							}
						}
					
			}
			
				
			
			Set<Key> foreignKeys = this.getForeignKeys();
			Set<Key> otherForeignKeys = other.getForeignKeys();
			result +="\n\n";
			result += "Continuamos con las claves foraneas\n";
			if (foreignKeys.size() == 0 &&  otherForeignKeys.size()!=0) {
				result += "La BD1 con tabla "+this.name+" no poseen claves Foraneas y la BD2 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  otherForeignKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (foreignKeys.size() != 0 &&  otherForeignKeys.size()==0) {
				result += "La BD2 con tabla "+this.name+" no poseen claves Foraneas y la BD1 con la misma tabla si posee y es :\n";
				Key key = null;
				for (Iterator iterator =  foreignKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					result+= key.toString()+"\n";
				}
			} else if (foreignKeys.size() == 0 &&  otherForeignKeys.size()==0) {
				result += "ninguna de las BDs con tabla "+this.name+" posee Claves Foraneas \n";
			} else {
				if (foreignKeys.size() != otherForeignKeys.size()) {
					result += " Las tablas poseen distinta cantidad de claves foraneas \n"; 
					if(foreignKeys.size() > otherForeignKeys.size()) {
						result += "la tabla " +this.name+ " de la BD1 tiene mas claves foraneas que la tabla "+ other.getName() +" de la BD2\n"
								+ "Posee "+ foreignKeys.size()+ "y la otra "+ otherForeignKeys.size()+ "\n"; 
					} else {
						result += "la tabla " +other.getName()+ " de la BD2 tiene mas claves foraneas que la tabla "+ name +" de la BD1\n"
								+ "Posee "+ otherForeignKeys.size()+ "y la otra "+ foreignKeys.size() + "\n"; 
					}
				} else {
					result += "Ambas tablas con nombre " + this.name +" poseen la misma cantidad de Claves foraneas \n";	
				}
				result+= "Ahora veamos sus claves foraneas \n"; 
				Key key  = null;
				Key otherKey = null;
				for (Iterator<Key> iterator = foreignKeys.iterator(); iterator.hasNext();) {
					key = (Key) iterator.next();
					otherKey = other.getKey(key);
					if(otherKey == null) {
						result += "La BD1 con tabla "+ this.getName() +" Posee una clave foranea llamada "+ key.getKeyName() + "\n"
								+ "Pero la BD2 con esta tabla No posee una key similar con la misma estructura \n";
						for (Iterator<Key> iterator2 = otherForeignKeys.iterator(); iterator.hasNext();) {
							otherKey =  (Key) iterator2.next();
							key.compare(otherKey);
						}
					} else {
						result += "Ambas Claves son iguales estructuralmente con la siguiente estructura por parte de la key "+ key.getKeyName()+ "\n"
								+ " de la BD1 con esta tabla \n"+ key.toString() +" \n"
								+ " Y la clave "+ otherKey.getKeyName()+ " de la BD2 con la misma tabla \n"+otherKey.toString() + "\n";
						}	
					}
					for (Iterator<Key> iterator2 = otherForeignKeys.iterator(); iterator2.hasNext();) {
						key = (Key) iterator2.next();
						otherKey = this.getKey(key);
						if(otherKey == null) {
							result += "La BD2 con tabla "+ this.getName() +" Posee una clave foranea llamada "+ key.getKeyName() + "\n"
										+ "Pero la BD1 con esta tabla No posee una key similar con la misma estructura \n";
						}
					}	
			}
		
				
		return result;
	}
	
	
	
}
