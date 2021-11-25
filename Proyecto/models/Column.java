package Proyecto.models;

import javax.print.attribute.standard.MediaSize.Other;

public class Column {
	
	private String name;
	private String type;
	private int size;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		if (!(obj instanceof Column))
			return false;
		Column other = (Column) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type.equals(other.getType()) && type.equals("VARCHAR"))
			return size == other.size;
		else 
			return type.equals(other.getType());
	}


	@Override
	public String toString() {
		String r = 	(type.equals("VARCHAR"))?	" Columna: " + name+ "   Type: "+  type+ "   Size: "+ size :"  Columna: " + name+ "   Type: "+  type ;
		
		return r;
	}

	public String compare(Column column) {
		String result = "";
			if(type.equals(column.getType())) {
				if (type.equals("VARCHAR")) {
					if ( size == column.getSize()) {
						result += "el tipo de la columnas son iguales de tipo " + type + " y su longitud tambien ambas de :"+ size;
					} else {
						result += "el tipo de la columnas son iguales de tipo " + type + 
								" pero la longitud no es igual una es de "+ size +" y la otra de "+ column.getSize();
					}
				} else 
					result += "el tipo de la columnas son iguales de tipo " + type;
			} else {
				result += "el tipo de las columnas son distintos el de la columna " + name + " de la BD1 es" +type + ""
						+ " y el de la columna " + column.getName() + " de la BD2  es " + column.getType();
			} 
		return result;
		
	}
	
}
