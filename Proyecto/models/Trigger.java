package Proyecto.models;


public class Trigger {

	private String name;
	private String shot;
	private String action;

	public Trigger(String name, String shot, String action) {
		this.name = name;
		this.shot = shot;
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShot() {
		return shot;
	}

	public void setShot(String shot) {
		this.shot = shot;
	}

	
	public String getAction() {
		return action;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trigger other = (Trigger) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		} else {
			return (action.equals(other.getAction()) && shot.equals(other.getShot()));
		}
		return true;
	}

	
	@Override
	public String toString() {
		return "Trigger [name=" + name + ", shot=" + shot + ", action= "+action+"] \n";
	}

	public String compare(Trigger trigger) {
		String result = "";
		if (this == trigger)
			result += "Ambos trigger son inguales ya que son el mismo trigger.\n";
		if (trigger == null)
			result += "El trigger con el que se desea comparar esta vacío.\n";
		Trigger other = (Trigger) trigger;
		if (name == null) {
			if (other.name != null)
				result += "El trigger desde el que se trata de hacer la comparacion no posee nombre.\n";
		} else if (!name.equals(other.name)) {
			result += this.name + " y " + other.name + " como se ve no tienen el mismo nombre.\n";
		} else {
			if (!(action.equals(other.getAction())))
				result += this.name + " y " + other.name + " no generan la misma accion.\n";
			if (!(shot.equals(other.getShot())))
				result += this.name + " y " + other.name + " no generan la accion en el mismo momento.\n";
		}
		if (result == "")
			result += "Los trigger " + this.name + " y " + other.name + " son iguales.\n";;
		return result;
	}
	
}
