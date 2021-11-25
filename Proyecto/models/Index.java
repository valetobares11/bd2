package Proyecto.models;

public class Index {
	private String name;
	private String column;
	
	
	public Index() {}
	
	public Index(String nombre, String col) {
		this.name = nombre;
		this.column = col;
	}

	
	@Override
	public String toString() {
		return "Index [name=" + name + ", column=" + column + "]\n";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Index other = (Index) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column)) 
			return false;
		return true;
	}

	public String compare(Index index) {
		String result = "";
		if (this == index)
			result += "Ambos indices son inguales ya que son el mismo indice.\n";
		if (index == null)
			result += "El indice con el que se desea comparar esta vacío.\n";
		if (column.equals(index.column))
			result += "Los indices "+this.name+" y "+index.name+" referencian a la misma columna "+column;
		else
			result += "Los indices "+this.name+" y "+index.name+" referencian a distintas columnas, "+this.name+" referencia a "+column+" mientras que "+index.name+" referencia a "+index.column;
		return result;
	}
}
