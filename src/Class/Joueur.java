package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class Joueur {
	private Pion pion;
	private ArrayList<Mur> listMurs;
	private int nbMaxMurs;
	private Coordonnees winCoord;

	public Joueur(int nbMurs, Couleur c, Coordonnees coord) {
		this.pion = new Pion(coord, c);
		this.listMurs = new ArrayList<Mur>();
		this.nbMaxMurs = nbMurs;
	}

	public Couleur getCouleurs() {
		return pion.getCouleur();
	}
	
	public int nbMursPosee(){
		return this.listMurs.size();
	}

	public int getNbMaxMurs() {
		return nbMaxMurs;
	}

	public Coordonnees getCoordonnees() {
		return this.pion.getCoordonnees();
	}

	public Coordonnees getWinCoord() {
		return winCoord;
	}

	public void setWinCoord(Coordonnees coord){
		this.winCoord = coord;
	}

	public void setPionCoordonnees(Coordonnees coord){
		this.pion.setCoordonnees(coord);
	}

	public void move(Coordonnees coord){
		this.pion.setCoordonnees(coord);
	}

	public String toString() {
		String res = "";
		res += "\tCouleur : " + this.getCouleurs() + "\n";
		res += "\tNombre max de murs : " + this.nbMaxMurs + "\n";
		res += "\tPosition pion : " + this.pion.getCoordonnees().toString() + "\n";
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof Joueur)) {
			return false;
		}

		Joueur j = (Joueur) obj;

		return j.getCouleurs().equals(this.getCouleurs());
	}
}

