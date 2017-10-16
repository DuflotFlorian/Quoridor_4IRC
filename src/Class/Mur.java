package Class;

public class Mur {
	private Coordonnees coordonnees;
	private int sens;
	
	public Mur(int x, int y, int sens) {
		this.coordonnees = new Coordonnees(x, y);
		this.sens = sens;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public int getSens() {
		return sens;
	}

	public void setSens(int sens) {
		this.sens = sens;
	}
	
	
}
