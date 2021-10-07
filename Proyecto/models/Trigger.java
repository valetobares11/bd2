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
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "Trigger [name=" + name + ", shot=" + shot + "]";
	}
	
}
