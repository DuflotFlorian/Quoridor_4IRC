package Class;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class Joueur {
	private Pion pion;
	private ArrayList<Mur> listMurs;
	private int nbMaxMurs;
	
	public Joueur(int nbMurs, Couleur c) {
		this.pion = new Pion(c);
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

	public void setPionCoordonnees(Coordonnees coord){
		this.pion.setCoordonnees(coord);
	}

	public void deplacer(Coordonnees coord){
		this.pion.setCoordonnees(coord);
	}

	public boolean isMoveOk(Coordonnees coord){
		boolean validX,validY;
		validX = (abs(pion.getCoordonnees().getX() - coord.getX()) == 1);
		validY = (abs(pion.getCoordonnees().getY() - coord.getY()) == 1);
		return (validX || validY);
	}

	public String toString() {
		String res = "";
		res += "\tCouleur : " + this.getCouleurs() + "\n";
		res += "\tNombre max de murs : " + this.nbMaxMurs + "\n";
		res += "\tPosition pion : " + this.pion.getCoordonnees().toString() + "\n";
		return res;
	}
}

