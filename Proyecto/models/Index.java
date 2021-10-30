package Proyecto.models;

public class Index {
	private String name;
	private String type;
	private String column;
	
	
	public Index() {}
	
	public Index(String nombre, String tipo, String col) {
		this.name = nombre;
		this.type = tipo;
		this.column = col;
	}

	
	@Override
	public String toString() {
		return "Index [name=" + name + ", type=" + type + ", column=" + column + "]\n";
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
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String compare(Index index) {
		String result = "";
		if (this == index)
			result += "Ambos indices son inguales ya que son el mismo indice.\n";
		if (index == null)
			result += "El indice con el que se desea comparar esta vacío.\n";
		Index other = (Index) index;
		if (column == null) {
			if (other.column != null)
				result += this.name + " no posee indices, mientras que "
				          + other.name + " si posee indices.\n";
		} else if (!column.equals(other.column))
			result += "Los valores de los indices no son iguales.\n";
		if (type == null) {
			if (other.type != null)
				result += this.name + " no tiene valores de tipo asignado a sus indies, "
				         +other.name + " si tiene tipos, siendo estos primarios o secundarios.\n";
		} else if (!type.equals(other.type))
			result += "Los indices no son del mismo tipo.\n";
		if (result == "")
			result += "Los indices " + this.name + " y " + other.name + " son iguales.\n";
		return result;
	}
}
