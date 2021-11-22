package Proyecto.models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ItalicAction;



public class Key {

	private String name;
	private Set<Column> columns;
	private int seqNumber;
	private boolean isPrimary;
	private boolean isForeink;
	private boolean isUnique;
	private String referenceTable;
	private String referenceKey;
	//private Status status;

	public Key() {
		columns = new HashSet<Column>();
	}

	/*
	 * 1= is primary 2= is foreink 3= is unique
	 */
	public void setKeyType(int type) {
		switch (type) {
		case 1:
			isPrimary = true;
			break;
		case 2:
			isForeink = true;
			break;
		case 3:
			isUnique = true;
			break;
		}
	}

	public void setKeyName(String keyName) {
		name = keyName;
	}

	public String getKeyName() {
		return name;
	}

	public void setSeqNumber(int numberOfKey) {
		seqNumber = numberOfKey;
	}
	
	public void setColumn(Column column) {
		columns.add(column);
	}
	// Cuando una clave es foranea, hay que decir a que clave hace referencia
	public void referenceTo(String refKey, String refTable) {
		this.referenceKey = refKey;
		this.referenceTable = refTable;
	}

	public int getType() {
		if (this.isPrimary)
			return 1;
		if (this.isForeink)
			return 2;
		if (this.isUnique)
			return 3;
		return 0;
	}

	/*public Status getStatus() {
		return status;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Column> getColumns() {
		return columns;
	}

	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public boolean isForeink() {
		return isForeink;
	}

	public void setForeink(boolean isForeink) {
		this.isForeink = isForeink;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public String getReferenceTable() {
		return referenceTable;
	}

	public void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}

	public String getReferenceKey() {
		return referenceKey;
	}

	public void setReferenceKey(String referenceKey) {
		this.referenceKey = referenceKey;
	}

	public int getSeqNumber() {
		return seqNumber;
	}
	
	public Column getColumn(String name) {
		for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
			Column columna = iterator.next();
			if (columna.getName().equals(name))
				return columna;
		}
		return null;
	}
	
	

	@Override
	public String toString() {
		return "Key [name=" + name + ", columns=" + columns + ", seqNumber=" + seqNumber + ", isPrimary=" + isPrimary
				+ ", isForeink=" + isForeink + ", isUnique=" + isUnique + ", referenceTable=" + referenceTable
				+ ", referenceKey=" + referenceKey + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + (isForeink ? 1231 : 1237);
		result = prime * result + (isPrimary ? 1231 : 1237);
		result = prime * result + (isUnique ? 1231 : 1237);
		result = prime * result + ((referenceKey == null) ? 0 : referenceKey.hashCode());
		result = prime * result + ((referenceTable == null) ? 0 : referenceTable.hashCode());
		result = prime * result + seqNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		//if (getClass() != obj.getClass())
			//return false;
		Key other = (Key) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columnasIguales(columns,other.columns)) {			
			return false;
		}
		if (isForeink != other.isForeink)
			return false;
		if (isPrimary != other.isPrimary)
			return false;
		if (isUnique != other.isUnique)
			return false;
		if (referenceKey == null) {
			if (other.referenceKey != null)
				return false;
		} else if (!referenceKey.equals(other.referenceKey)) {	
			return false;
		}	
		if (referenceTable == null) {
			if (other.referenceTable != null)
				return false;
		} else if (!referenceTable.equals(other.referenceTable))
			return false;
		if (seqNumber != other.seqNumber)
			return false;
		return true;
	}
	public boolean columnasIguales(Set<Column> columns1, Set<Column> columns2) {
		Column column1 = null;
		Column column2 = null;
		for (Iterator iterator = columns1.iterator(); iterator.hasNext();) {
		 column1 = (Column) iterator.next();
		 for (Iterator iterator2 = columns2.iterator(); iterator2.hasNext();) {
			 column2 = (Column) iterator2.next();
			if (column2.getName().equals(column1.getName())) {
				return true;
			}
		 }	
		}
		return false;
	}
	
	public String compare(Key other) {
		String result = "";
		if (columns.size() != other.getColumns().size()) {
			result += "Las dos claves poseen distinta cantidad de columnas\n";
			if(columns.size() > other.getColumns().size()) {
				result += "La clave " +this.name+ " tiene mas columnas que la clave "+ other.getName()+",Posee "+ columns.size()+ "y la otra "+ other.getColumns().size()+ "\n"; 
			}
			else {
				result += "La clave " +other.getName()+ " tiene mas columnas que la clave "+this.name+", Posee "+ other.getColumns().size()+ "y la otra "+ columns.size()+ "\n";
			}
			Set<Column> columnasIguales = new HashSet<Column>();
			Set<Column> columnasbd1 = new HashSet<Column>();
			Set<Column> columnasbd2 = new HashSet<Column>();
			
			for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
				Column column = (Column) iterator.next();
				Column column2 = other.getColumn(column.getName());
				if (column2 == null || !column.equals(column2)) {
					columnasbd1.add(column);
				} else {
					columnasIguales.add(column);
				}
			}
			for (Iterator<Column> iterator = other.getColumns().iterator(); iterator.hasNext();) {
				Column column = (Column) iterator.next();
				Column column2 = this.getColumn(column.getName());
				if (column2 == null || !column.equals(column2)) {
					columnasbd2.add(column);
				} 
			}
			result += "A continuacion mostramos las columnas equivalentes y diferentes\n";
			if (columnasIguales.size() >0) {
				result +=  "Las columnas iguales son :\n";
				for (Iterator<Column> iterator = columnasIguales.iterator(); iterator.hasNext();) {
					Column table = (Column) iterator.next();
					result +=table.toString()+"\n";
				}
			}
			if (columnasbd1.size() >0) {
				result+= "Las columnas propias de la clave "+this.getName()+" son :";
				for (Iterator<Column> iterator = columnasbd1.iterator(); iterator.hasNext();) {
					Column table = (Column) iterator.next();
					result += table.toString()+"\n";
				}
			}
			if (columnasbd2.size() >0) {
				result+= "Las columnas propias de la clave "+other.getName()+" son :";
				for (Iterator<Column> iterator = columnasbd2.iterator(); iterator.hasNext();) {
					Column table = (Column) iterator.next();
					result += table.toString()+"\n";
				}
			}
		}
		else {
			result += "Ambas claves poseen la misma cantidad de columnas \n"
					+ "Ahora veamos sus columnas \n"; 
			for (Iterator<Column> iterator = columns.iterator(); iterator.hasNext();) {
				Column column = (Column) iterator.next();
				Column columnOther = other.getColumn(column.getName());
				if(columnOther==null) {
					result += "La clave "+ this.getName() +"posee una Columna llamada "+ column.getName() + ""
							+ "pero la clave "+other.getName()+" no posee esta columna \n";
				} else {
					if (columnOther.getName().equals(column.getName())) {
						result += "Ambas claves poseen una columna llamada "+ column.getName() ;
						result += "Veamos si ambas columnas son iguales estructuralmente.. \n";
						if (!columnOther.equals(column)) {
							result += columnOther.compare(column);
						} else {
							result += "Ambas son iguales estructuralmente con la siguiente estructura" + column.toString()+ "\n";
						}
					}
					
				}
			}
			for (Iterator<Column> iterator = other.getColumns().iterator(); iterator.hasNext();) {
				Column column = (Column) iterator.next();
				Column columnOther = this.getColumn(column.getName());
				if(columnOther==null) {
					result += "La clave "+ other.getName() +"posee una Columna llamada "+ column.getName() + ""
							+ "pero la clave "+this.getName()+" no posee esta columna \n";
				}
			}
			
		}
		if (isForeink) {
			if (!referenceTable.equals(other.getReferenceTable())) {
				result += "La clave "+other.getName()+" refiere a "+other.getReferenceTable()+", mientras que "+this.getName()+" refiere a "+this.referenceTable;
			}
			else
				result += "Ambas claves "+this.getName()+" y "+other.getName()+" refieren a la tabla "+this.referenceTable;
			if (!referenceKey.equals(other.getReferenceKey())) {
				result += "La clave "+other.getName()+" refiere a la clave "+other.getReferenceKey()+", mientras que "+this.getName()+" refiere a "+this.referenceKey;
			}
			else
				result += "Ambas claves "+this.getName()+" y "+other.getName()+" refieren a la clave "+this.referenceKey;
		}
		return result;
	}

}
