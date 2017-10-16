package Class;

public class Pion {
	private Coordonnees coordonnees;
	private Couleur couleur;
	
	public Pion(Couleur c) {
		this.couleur = c;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}
	
}
