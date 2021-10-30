package Proyecto.models;


public class Key {

	private String name;
	private String column;// igual que con tabla ver kionda
	private int seqNumber;
	private boolean isPrimary;
	private boolean isForeink;
	private boolean isUnique;
	private String referenceTable;
	private String referenceKey;
	//private Status status;

	public Key() {}

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

	public void setColumnAssoc(String columnName) {
		column = columnName;
	}

	public void setSeqNumber(int numberOfKey) {
		seqNumber = numberOfKey;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Key))
			return false;
		Key other = (Key) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}/* else if (!name.equals(other.name))
			return false;*/
		return true;
	}

	/*public Status getStatus() {
		return status;
	}*/

	@Override
	public String toString() {
		return "Key [name=" + name + ", column=" + column + ", isPrimary=" + isPrimary
				+ ", isForeink=" + isForeink + ", isUnique=" + isUnique + ", referenceTable=" + referenceTable
				+ ", referenceKey=" + referenceKey +  "]";
	}

	public String compare(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

}
